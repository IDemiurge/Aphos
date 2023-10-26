package system.log;

import system.utils.StringUtils;

/**
 * Created by Alexander on 8/25/2023
 */
public class SysLog {

    public static final void printOut(int logLevel, Object... toLog){
        // new LogPriority(logLevel)
    }
    public static final void printOut( Object... toLog){
        printOut(LogChannel.Main, toLog);
    }
    public static final void printOut(LogMeta meta, Object... toLog){
        if (!checkLogged(meta))
            return;
       System.out.println(StringUtils.build("System: ", " ", toLog));
    }

    private static boolean checkLogged(LogMeta meta) {
        if (meta instanceof LogChannel channel){
            //
        }
        return true;
    }

    public enum LogChannel implements LogMeta{
        Main,
    }
    ////////////////////region STD LOG SHORTCUTS

    //endregion
}
