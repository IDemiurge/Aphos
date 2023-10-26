package framework.entity;

import elements.stats.UnitParam;
import elements.stats.UnitProp;
import elements.stats.generic.Stat;
import elements.stats.generic.StatConsts;
import framework.data.TypeData;
import system.datatypes.LinkedStringMap;
import system.utils.MapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 8/22/2023
 * maybe separate basic version from one with _Base and _Cur?
 */
public class EntityData extends TypeData {

    private final Map<String, Integer> intMapCur = new LinkedStringMap<>();

    private  Map<String, Integer> intMapBase ;
    private  Map<String, String> stringMapBase ;
    private  Map<String, Boolean> boolMapBase ;
    private List<String> retainedProps;


    public EntityData(Map<String, Object> valueMap) {
        super(valueMap);
        initDefaultValues();
    }

    private void initDefaultValues() {
        //if missing, set default value
        for (UnitParam val : StatConsts.unitDefaultVals) {
            if (!intMap.containsKey(val.getName()) ){
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
        if (stringMapBase==null)
            stringMapBase = new LinkedStringMap<>();
        if (intMapBase==null)
            intMapBase = new LinkedStringMap<>();
        if (boolMapBase==null)
            boolMapBase = new LinkedStringMap<>();

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
        intMap.clear();
        intMap.putAll(intMapBase);

        boolMap.keySet().retainAll(getRetainedProps()); //same with params?
        boolMap.putAll(boolMapBase);
        stringMap.keySet().retainAll(getRetainedProps()); //same with params?
        stringMap.putAll(stringMapBase);
    }

    private List<String> getRetainedProps() {
        if (retainedProps == null)
        {
            retainedProps = new ArrayList<>() ;
            Arrays.stream(UnitProp.values()).forEach(prop -> {
                if (prop.isPersistent())
                    retainedProps.add(LinkedStringMap.format(prop.getName()) );
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


    //endregion
}