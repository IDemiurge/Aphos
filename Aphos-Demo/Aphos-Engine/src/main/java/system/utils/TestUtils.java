package system.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 10/31/2023
 */
public class TestUtils {
    static Map<String, Object> fixMap= new HashMap<>();
    public static void fix(String key, Object value) {
        fixMap.put(key, value);
    }

    public static <T> T getFix(String key, Class<T> c) {
        return (T) fixMap.remove(key);
    }
}
