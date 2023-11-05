package elements.exec.build;

import system.log.SysLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 10/29/2023
 */
public class VarTracker {
    private static final String SEPARATOR = "$";
    private Map<String, VarHolder> varNameToHolder = new HashMap<>();
    private Map<String, Integer> increments = new HashMap<>();
    private String prefix;

    public VarTracker(String prefix) {
        this.prefix = prefix;
    }

    public void varHolderAdded(VarHolder holder) {
        for (String argName : holder.getArgNames()) {
            String key = getUniqueKey(argName);
            varNameToHolder.put(key, holder);
        }
    }

    private String getUniqueKey(String argName) {
        Integer n = increments.get(argName);
        if (n != null) {
            increments.put(argName, n + 1);
            return argName + SEPARATOR + n;
        }
        if (varNameToHolder.containsKey(argName)) {
            increments.put(argName, 2);
            return argName + SEPARATOR + 2;
        }
        return argName;
    }

    public void apply(Map<String, Object> vars) {
        for (String key : varNameToHolder.keySet()) {
            Object value = vars.get(key);
            if (value != null) {
                String originalKey = getOriginalKey(key);
                varNameToHolder.get(key).set(originalKey, value);
                SysLog.printLine(SysLog.LogChannel.Build, prefix, ": ", originalKey, " VAR set to ", value, " for ", varNameToHolder.get(key));
            }
        }
    }

    private String getOriginalKey(String key) {
        if (!key.contains(SEPARATOR)) {
            return key;
        }
        return key.substring(0, key.indexOf(SEPARATOR));
    }
}
