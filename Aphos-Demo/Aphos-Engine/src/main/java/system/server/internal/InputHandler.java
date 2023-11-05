package system.server.internal;

import system.log.SysLog;

/**
 * Created by Alexander on 11/3/2023
 */
public class InputHandler {

    public void handle(InputKey key, Object data) {
        //must result in sending something back
    }

    public void handle(Object input) {
        SysLog.printLine(input);
    }
}
