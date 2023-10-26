package system.log.result;

import system.datatypes.LinkedStringMap;
import system.utils.MapUtils;

import java.util.Map;

/**
 * Created by Alexander on 8/25/2023
 *
 * Is it what we also can send to client?
 */
public abstract class LoggableResult {
    protected final Map<String, Object> data;

    public LoggableResult() {
        data = new LinkedStringMap<>();
    }

    public void addAll(LoggableResult datum){
        addAll(datum.data);
    }

    public void addAll(Map map){
        for (Object key : map.keySet()) {
            data.put(key.toString(), map.get(key));
        }
    }

    public Object get(Object key) {
        return data.get(key);
    }

    public Object __log__(String key, Object value) {
        return data.put(key, value);
    }

    public Object remove(Object key) {
        return data.remove(key);
    }

    @Override
    public String toString() {
        return MapUtils.represent(data);
    }

    // public TypeData getData() {
    //     return data;
    // }
    //wrapper methods?
}
