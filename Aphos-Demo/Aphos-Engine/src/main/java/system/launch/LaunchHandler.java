package system.launch;

import system.log.SysLog;

/**
 * Created by Alexander on 11/20/2023
 *
 * also a family of handlers governed by a manager?
 */
public class LaunchHandler {

    private final Launch launch;

    public LaunchHandler(Launch launch) {
        this.launch = launch;
    }

    private boolean isCrashPreferred() {
        return false;
    }

    //into yaml handler - who will work with ExceptionHandler
    public void failYamlType(String name, String typeKey, Object typeNode, Exception e) {
        String message = typeKey + " " + name +
                " failed to build from " + typeNode;
        if (isCrashPreferred())
            throw new LaunchException(message, e);
        else {
            SysLog.printLine(SysLog.LogChannel.Launch_Errors, message);
            e.printStackTrace();
        }
    }

    public void exception(Exception e, String message) {
        if (isCrashPreferred())
            throw new LaunchException(message, e);
        else {
            SysLog.printLine(SysLog.LogChannel.Launch_Errors, message);
            e.printStackTrace();
        }
    }
}
