package framework.data.datatypes;

import framework.data.DataManager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Alexander on 10/20/2023
 */
public class ArgMap extends LinkedHashMap<String, Object> {

    public ArgMap(Map<String, Object> map) {
        putAll(map);
    }

    public ArgMap() {

    }

    public static Map get(String s) {
        return new ArgMap(DataManager.stringToMap(s));
    }
}
