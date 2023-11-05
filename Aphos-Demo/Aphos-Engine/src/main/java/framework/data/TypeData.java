package framework.data;

import system.utils.EnumFinder;
import elements.content.enums.stats.generic.Stat;
import org.apache.commons.lang3.NotImplementedException;
import system.consts.StringConsts;
import datatypes.LinkedStringMap;
import utils.collection.ListUtils;
import utils.collection.MapUtils;
import utils.old.NumberUtils;
import utils.old.StringMaster;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static framework.data.DataManager.getRawValue;

/**
 * Created by Alexander on 8/23/2023
 */
public class TypeData {
    //TODO float?
    protected final Map<String, Integer> intMap = createMap(Integer.class);
    protected final Map<String, String> stringMap = createMap(String.class);
    protected final Map<String, Boolean> boolMap = createMap(Boolean.class);
    protected final Map<Class, Map> maps = new HashMap<>();
    private final Map<String, Function<String, Object>> getterCache = new LinkedStringMap<>();

    public TypeData() {
        this(new LinkedStringMap<>());
    }

    public TypeData(Map<String, Object> valueMap) {
        maps.put(Boolean.class, boolMap);
        maps.put(String.class, stringMap);
        maps.put(Integer.class, intMap);

        for (String key : valueMap.keySet()) {
            initValue(key, valueMap.get(key));
        }
    }

    protected <T> Map<String, T> createMap(Class<T> clazz) {
        return new LinkedStringMap<>();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Data: ");
        return builder.append("\n---String: \n").append(MapUtils.represent(stringMap))
                .append("\n---Integers: \n").append(MapUtils.represent(intMap))
                .append("\n---Booleans: \n").append(MapUtils.represent(boolMap)).toString();
    }

    protected Object initValue(String key, Object o) {
        if (o == null)
            return null;
        Object val = getRawValue(o);

        String string = o.toString();
        if (isTripleValue(string) && !string.contains("=")) {
            //TODO refactor for DRY
            Iterator<String> iterator = Arrays.asList(new String[]{"min", "base", "max"}).iterator();
            for (String str : string.split(StringConsts.MIN_BASE_MAX_SEPARATOR)) {
                key = StringConsts.checkValueNameReplacement(key);
                initValue(key + "_" + iterator.next(), NumberUtils.getInt(str));
            }
        } else {
            set(key, val);
        }
        return val;
    }

    private boolean isTripleValue(String string) {
        return string.contains(StringConsts.MIN_BASE_MAX_SEPARATOR);
    }

    public void addIntValue(String valueName, Integer value) {
        String[] values = null;
        if (isTripleValueName(valueName)) {
            values = getTriplet(valueName);
        } else
            values = new String[]{valueName};


        for (String name : values) {
            int prev = getIntOrZero(name);
            int newVal = prev + value;
            setInt(name, newVal);
        }
    }

    protected void setInt(String name, int newVal) {
        intMap.put(name, newVal);
    }

    private String[] getTriplet(String valueName) {
        valueName = valueName.toLowerCase();
        String root =valueName;
        if (valueName.endsWith("_all")) {
            root = valueName.replace("_all", "");
        }
            return new String[]{root + "_min", root + "_base", root + "_max"};
    }

    private boolean isTripleValueName(String valueName) {
        if (valueName.endsWith("_all"))
            return true;
        return false;
    }

    private int getIntOrZero(String valueName) {
        Integer integer = intMap.get(valueName);
        if (integer != null)
            return integer;
        return 0;
    }

    //endregion
    //////////////// region SETTERS ///////////////////
    public void set(Stat key, Object val) {
        set(key.getName(), val);
    }

    //TODO
    public void multiply(String key, Object val) {

    }

    public void set(String key, Object val) {
        String strValue = val.toString();
        if (isTripleValue(strValue)) {
            String[] valueNames = getTriplet(key);
            List<Integer> values = getTripletValues(strValue);
            int i = 0;
            for (String name : valueNames) {
                setInt(name, values.get(i++));
            }
        } else {
            Map<String, Object> map = maps.get(val.getClass());
            map.put(key.toString(), val);
        }
    }

    private List<Integer> getTripletValues(String strValue) {
        return Arrays.stream(strValue.split(StringConsts.MIN_BASE_MAX_SEPARATOR)).map(s-> NumberUtils.getInt(s)).collect(Collectors.toList());
    }

    public void setPersistent(String key, Object val) {
        throw new NotImplementedException("Must be EntityData");
    }

    //endregion
    //////////////// region GETTERS ///////////////////
    public <T> T getEnum(String name, Class<T> className) {
        String value = getS(name);
        if (StringMaster.isEmpty(value))
            return null;
        return EnumFinder.get(className, value);
    }

    public Object get(String key) {
        if (getterCache.containsKey(key)) {
            return getterCache.get(key).apply(key);
        }
        String str = getS(key);
        if (str != null) {
            getterCache.put(key, s -> getS(s));
            return str;
        }
        Boolean b = getB(key);
        if (b != null) {
            getterCache.put(key, s -> getB(s));
            return b;
        }
        Integer i = getInt(key);
        if (i != null) {
            // if (i != MathConsts.minValue) {
            getterCache.put(key, s -> getInt(s));
            return i;
        }
        //TODO NOTE ERROR
        return "";
    }

    public Integer getInt(String key) {
        Integer integer = intMap.get(key.toString());
        if (integer == null) {
            return 0;
            // return MathConsts.minValue;
        }
        return integer;
    }

    private Boolean getB(String name) {
        return boolMap.get(name);
    }

    public String getS(Stat stat) {
        return getS(stat.getName());
    }

    public String getS(String name) {
        return stringMap.get(name);
    }

    public String getStr(String name) {
        String s = stringMap.get(name);
        if (s == null) {
            return "";
        }
        return s;
    }

    public Object get(Stat stat) {
        return get(stat.getName());
    }

    public int getInt(Stat stat) {
        return getInt(stat.getName());
    }

    public boolean isTrue(Stat stat) {
        return isTrue(stat.getName());
    }

    public boolean isTrue(String s) {
        return Boolean.TRUE.equals(getB(s));
    }

    public boolean has(String key) {
        return getterCache.containsKey(key);
    }

    public Set<String> keySet() {
        return ListUtils.mergeToSet(intMap.keySet(),
                boolMap.keySet(),
                stringMap.keySet());
    }

    public String toSimpleString() {
        return keySet().stream().map(key -> key + "=" + get(key)).collect(Collectors.joining(", "));
    }

    public boolean valueContains(String name, String value) {
        value = value.toLowerCase();
        String s = getStr(name).toLowerCase();
        if (s.endsWith(value))
            return true;
        return s.contains(value + StringConsts.CONTAINER_SEPARATOR)
                || s.contains(value + StringConsts.CONTAINER_PROP_SEPARATOR);
    }
}
