package utils.old.swing;


import utils.old.ArrayMaster;
import utils.old.ListMaster;

import javax.swing.*;
import java.util.List;

public class SwingMaster {

    private static final String INPUT = "Input text...";
    private static final String ERROR = "Error!";

    public static String inputText() {
        return inputText(INPUT);
    }

    public static String inputTextNotNull(String tip) {
        String input = inputText(tip);
        if (input == null) {
            return "";
        }
        return input;
    }

    public static String inputText(String tip) {
        return JOptionPane.showInputDialog(tip);
    }

    public static Integer inputInt(int initial) {
        return inputInt("Enter an integer", initial);
    }

    public static Integer inputInt(String msg, int initial) {
        return inputInt("Enter an integer", initial, false);
    }

    public static Integer inputInt(String msg, int initial, boolean nullIfCancelled) {
        String input = JOptionPane.showInputDialog(msg, initial);
        if (input == null) {
            if (nullIfCancelled) {
                return null;
            }
            return -1;
        }
        try {
            return Integer.valueOf(input);
        } catch (Exception e) {
            e.printStackTrace();
            if (nullIfCancelled) {
                return null;
            }
            return -1;
        }
    }

    public static int inputInt() {
        String input = JOptionPane.showInputDialog("Enter an integer");
        try {
            return Integer.valueOf(input);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String inputText(String tip, String initial) {
        return JOptionPane.showInputDialog(tip, initial);
    }

    public static void error(String string) {
        JOptionPane.showMessageDialog(null, string, ERROR, JOptionPane.ERROR_MESSAGE);
    }

    public static int optionChoice(String message, Object... array) {
        return optionChoice(array, message);
    }

    public static Object getChosenOption(String message, Object... array) {
        array = ArrayMaster.checkNestedArray(array);
        int optionChoice = optionChoice(array, message);
        if (optionChoice == -1) {
            return null;
        }
        return array[optionChoice];
    }

    public static int optionChoice(Object[] array, String message) {
        array = ArrayMaster.checkNestedArray(array);
        array = ListMaster.toStringList(array).toArray();
        return JOptionPane.showOptionDialog(null, message, "Choose...",
         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, array, array[0]);
    }

    public static void inform(String string) {
        JOptionPane.showMessageDialog(null, string);
    }

    public static boolean confirm(String string) {
        int showConfirmDialog = JOptionPane.showConfirmDialog(null, string, "Confirm",
         JOptionPane.YES_NO_OPTION);
        return showConfirmDialog == JOptionPane.YES_OPTION;
    }

    public static int pagedOptions(List<String> optionsList, int batchSize, boolean alt) {
        int n = 0;
        int from = 0;
        int to = Math.min(optionsList.size(), batchSize);
        int pages = Math.max(1, optionsList.size() / batchSize);
        while (n < pages) {
            List<String> sub = optionsList.subList(from, to);
            sub.add(0, alt ? "Next..." : "Next!");
            Object[] options = sub.toArray();
            int result = JOptionPane.showOptionDialog(null, "Pick one", "Draft",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
            if (result == -1)
                return -1;
            if (result >= 1) {
                return n * batchSize + result - 1;
            }
            n++;
            from += batchSize;
            to += batchSize;
        }
        return -1;
    }

    public enum CONTROLS_SCHEME {

    }

}
