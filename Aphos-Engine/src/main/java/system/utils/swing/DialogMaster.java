package system.utils.swing;


import javax.swing.*;

import static system.threading.WaitMaster.WAIT_OPERATIONS;
import static system.threading.WaitMaster.waitForInput;

public class DialogMaster {

    public static final WAIT_OPERATIONS ASK_WAIT = WAIT_OPERATIONS.OPTION_DIALOG;
    private static final String INPUT = "Input text...";
    private static final String ERROR = "Error!";

    public static String inputText() {
        return SwingMaster.inputText();
    }

    public static String inputTextNotNull(String tip) {
        return SwingMaster.inputTextNotNull(tip);
    }

    public static String inputText(String tip) {
        return SwingMaster.inputText(tip);
    }

    public static Integer inputInt(int initial) {
        return SwingMaster.inputInt(initial);
    }

    public static Integer inputInt(String msg, int initial) {
        return SwingMaster.inputInt(msg, initial);
    }

    public static Integer inputInt(String msg, int initial, boolean nullIfCancelled) {
        return SwingMaster.inputInt(msg, initial, nullIfCancelled);
    }

    public static int inputInt() {
        return SwingMaster.inputInt();
    }

    public static String inputText(String tip, String initial) {
        return SwingMaster.inputText(tip, initial);
    }

    public static void error(String string) {
        SwingMaster.error(string);
    }

    public static int optionChoice(String message, Object... array) {
        return SwingMaster.optionChoice(message, array);
    }

    public static Object getChosenOption(String message, Object... array) {
        return SwingMaster.getChosenOption(message, array);
    }

    public static int optionChoice(Object[] array, String message) {
        return SwingMaster.optionChoice(array, message);
    }
 

    public static void inform(String string) {
        SwingMaster.inform(string);
    }

    public static boolean confirm(String string) {
        return SwingMaster.confirm(string);
    }

    /**
     * use WaitMaster.waitForInput(WAIT_OPERATIONS.CONFIRM_DIALOG)
     *
     * @param string
     * @param wide
     * @param TRUE
     * @param FALSE
     * @param NULL
     */
    public static void ask(String string, boolean wide, String TRUE, String FALSE, String NULL) {
        JOptionPane.showConfirmDialog(null, string+" !!!??? - IMPLEMENT LIBGDX!");
    }


    public static Boolean askAndWait(String string, String TRUE, String FALSE, String NULL) {
        return askAndWait(string, true, TRUE, FALSE, NULL);
    }

    /**
     * Never use on EDT!
     */
    public static Boolean askAndWait(String string, boolean wide, String TRUE, String FALSE,
                                     String NULL) {
        if (SwingUtilities.isEventDispatchThread()) {
            throw new RuntimeException("askAndWait used on EDT!");
        }

        ask(string, wide, TRUE, FALSE, NULL);
        return (Boolean) waitForInput(ASK_WAIT);

    }

    public enum CONTROLS_SCHEME {

    }

}
