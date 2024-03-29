package framework.datatypes;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedStringMap<T> extends LinkedHashMap<String, T> {

    @Override
    public T get(Object key) {
        return super.get(key.toString().replace(" ", "_").toUpperCase());
    }

    @Override
    public T put(String key, T value) {
        return super.put(format(key), value);
    }

    public static final String format(String key) {
        return key.replace(" ", "_").toUpperCase();
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(format(key.toString()));
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> m) {
        for (String s : m.keySet()) {
            put(s, m.get(s));
        }
    }
}
