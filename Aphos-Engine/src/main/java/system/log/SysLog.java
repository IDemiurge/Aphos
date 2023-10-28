package system.log;

import system.utils.StringUtils;
import system.utils.old.TimeMaster;

/**
 * Created by Alexander on 8/25/2023
 */
public class SysLog {

    public static final void printLine(int logLevel, Object... toLog){
        // new LogPriority(logLevel)
    }
    public static final void printLine(Object... toLog){
        printLine(LogChannel.Main, toLog);
    }
    public static final void printLine(LogMeta meta, Object... toLog){
        if (!checkLogged(meta))
            return;
       String time = TimeMaster.getFormattedTime()+" --- ";
       System.out.println(StringUtils.build(time, " " ,  toLog));
    }

    public static final void print(LogMeta meta, Object... toLog){
        if (!checkLogged(meta))
            return;
        System.out.print(StringUtils.build("", " ", toLog));
    }

    private static boolean checkLogged(LogMeta meta) {
        if (meta instanceof LogChannel channel){
            //
        }
        return true;
    }

    public enum LogChannel implements LogMeta{
        Main, Values, Gameflow, Error,
    }
    ////////////////////region STD LOG SHORTCUTS

    //endregion
}
