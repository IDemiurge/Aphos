package framework.entity;

import elements.stats.UnitParam;
import elements.stats.UnitProp;
import elements.stats.generic.Stat;
import elements.stats.generic.StatConsts;
import framework.data.TypeData;
import system.datatypes.LinkedStringMap;
import system.datatypes.LogStatMap;
import system.utils.MapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 8/22/2023 maybe separate basic version from one with _Base and _Cur?
 */
public class EntityData extends TypeData {

    private final Map<String, Integer> intMapCur = createMap(Integer.class);

    private Map<String, Integer> intMapBase;
    private Map<String, String> stringMapBase;
    private Map<String, Boolean> boolMapBase;
    private List<String> retainedProps;
    private boolean muted;

    public EntityData(Map<String, Object> valueMap) {
        super(valueMap);
        initDefaultValues();
        intMap.keySet().removeAll(intMapCur.keySet());

    }

    public void setName(String name) {
        maps.values().forEach(m -> {
            setPrefix(m, name + "'s ");
        });
        setPrefix(intMapCur, name + "'s cur ");
        setPrefix(stringMapBase, name + "'s base ");
        setPrefix(intMapBase, name + "'s base ");
        setPrefix(boolMapBase, name + "'s base ");
    }

    public void setMuted(boolean muted) {
        this.muted=muted;
        maps.values().forEach(m -> {
            setMuted(m, muted);
        });
        setMuted(intMapCur, muted);
        setMuted(stringMapBase, muted);
        setMuted(intMapBase, muted);
        setMuted(boolMapBase, muted);
    }

    private void setMuted(Map m, boolean muted) {
        if (m instanceof LogStatMap<?> map)
            map.setMuted(muted);
    }

    private void setPrefix(Map m, String name) {
        if (m instanceof LogStatMap<?> map)
            map.setPrefix(name);
    }

    @Override
    protected <T> Map<String, T> createMap(Class<T> clazz) {
        return new LogStatMap<>();
    }

    private void initDefaultValues() {
        //if missing, set default value
        for (UnitParam val : StatConsts.unitDefaultVals) {
            if (!intMap.containsKey(val.getName())) {
                // if (getInt(val) == MathConsts.minValue){
                initValue(val.getName(), StatConsts.getDefault(val));
            }
        }
    }

    protected Object initValue(String key, Object o) {
        Object val = super.initValue(key, o);
        setBase(key, val);
        return val;
    }

    public void setCur(String key, int val) {
        intMapCur.put(key, val);
    }

    public Integer getInt(String key) {
        Integer integer = intMapCur.get(key);
        if (integer != null)
            return integer;
        return super.getInt(key);
    }

    private void setBase(String s, Object o) {
        if (intMapBase == null) {
            stringMapBase = createMap(String.class);
            intMapBase = createMap(Integer.class);
            boolMapBase = createMap(Boolean.class);
        }
        if (o instanceof Integer) {
            intMapBase.put(s, (Integer) o);
        }
        if (o instanceof Boolean) {
            boolMapBase.put(s, (Boolean) o);
        } else {
            stringMapBase.put(s, o.toString());
        }
    }

    public void toBase() {
        if (!muted)
            setMuted(true);
        //THIS WILL NOT WORK WITH LOG?
        intMap.clear();
        intMap.putAll(intMapBase);

        boolMap.keySet().retainAll(getRetainedProps()); //same with params?
        boolMap.putAll(boolMapBase);
        stringMap.keySet().retainAll(getRetainedProps()); //same with params?
        stringMap.putAll(stringMapBase);
        setMuted(muted);
    }

    private List<String> getRetainedProps() {
        if (retainedProps == null) {
            retainedProps = new ArrayList<>();
            Arrays.stream(UnitProp.values()).forEach(prop -> {
                if (prop.isPersistent())
                    retainedProps.add(LinkedStringMap.format(prop.getName()));
            });
        }
        return retainedProps;
    }

    public void setPersistent(String key, Object val) {
        getRetainedProps().add(LinkedStringMap.format(key));
        set(key, val);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Entity Data: ");
        return builder
                .append("\n---Integers: \n").append(MapUtils.represent(intMap))
                .append("\n---String: \n").append(MapUtils.represent(stringMap))
                .append("\n---Booleans: \n").append(MapUtils.represent(boolMap))
                .append("\n\n---Current Values: \n").append(MapUtils.represent(intMapCur))
                .append("\n\n---Base Integers: \n").append(MapUtils.represent(intMapBase))
                .append("\n---Base String: \n").append(MapUtils.represent(stringMapBase))
                .append("\n---Base Booleans: \n").append(MapUtils.represent(boolMapBase))
                .toString();
    }

    //////////////// region MODIFY ///////////////////
    public void addCurValue(Stat key, int i) {
        intMapCur.put(key.getName(), intMapCur.get(key) + i);
    }


    protected void setInt(String name, int newVal) {
        if (intMapCur.containsKey(name))
            intMapCur.put(name, newVal);
        else intMap.put(name, newVal);
    }
    //endregion
}
