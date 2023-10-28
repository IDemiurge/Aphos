package system.math;

import elements.exec.EntityRef;

/**
 * Created by Alexander on 8/25/2023
 * How to make it concise? Will I use that same old math package from Core?
 * Or is there something more modern?
 */
public class Formula {

    public static int eval(String formula, EntityRef ref) {
        //TODO let's see how we can make it performant

        if (formula.length()==1)
            return Integer.valueOf(formula);
        return 0;
    }
    public static int getInt(Object formula) {
        if (formula instanceof Integer)
            return (int) formula;
        //TODO
        return 0;
    }
}
