package framework.data.datatypes;


import framework.datatypes.LinkedStringMap;
import system.log.SysLog;

public class LogStatMap<T> extends LinkedStringMap<T> {

    private String prefix;
    private boolean muted = true;

    @Override
    public T put(String key, T value) {
        T prev = super.put(key, value);
        if (prev == value)
            return prev;
        if (prev != null) {
            if (prev.equals(value))
                return prev;
            if (!muted)
                SysLog.printLine(SysLog.LogChannel.Values, prefix, key, ": ", prev, " ---> ", value);
        }
        return prev;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
