package api.dto;

import framework.datatypes.LinkedStringMap;

import java.util.Map;

/**
 * Created by Alexander on 11/3/2023
 *
 * Consider using records? The beauty of this is also that while we're on java, we can cheat and pass
 * some java objects directly in that dataMap!
 */
public abstract class DTO<T> {
    protected final T type;
    protected final Map<String, Object> dataMap = new LinkedStringMap<>(); //can be nested

    public DTO(T type) {
        this.type = type;
    }

    public Object put(String s, Object o) {
        return dataMap.put(s, o);
    }

    public Object get(String o) {
        return dataMap.get(o);
    }
}
