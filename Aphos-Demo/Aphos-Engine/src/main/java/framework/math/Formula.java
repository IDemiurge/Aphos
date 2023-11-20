package framework.math;

import elements.exec.EntityRef;
import org.mariuszgromada.math.mxparser.*;
import system.math.wrap.IExpression;

/**
 * Created by Alexander on 8/25/2023
 * How to make it concise? Will I use that same old math package from Core?
 * Or is there something more modern?
 */
public class Formula {

    public static int eval(String formula, EntityRef ref) {
        //TODO let's see how we can make it performant

        IExpression plainExpression = evalArgs(formula, ref);
        // e.setArgumentValue();
        //here it must have resolved all internal expressions etc
        return plainExpression.calculate();
    }

    private static IExpression evalArgs(String formula, EntityRef ref) {
        /*
        split into substrings based on special character?
        10*$src_str+
         */
        IExpression e = system.math.MathCore.expression();
        for (String s : formula.split("/$")) {
            double value = RefCalc.eval(s, ref);
            e.setArgumentValue(wrap(s), value);
        }
        return e;
    }

    private static String wrap(String s) {
        return null;
    }

    public static int eval(Object formula, EntityRef ref) {
        if (formula instanceof Integer)
            return (int) formula;
        String s = formula.toString();
        if (s.length()==1)
            return Integer.valueOf(s);
        return eval(s, ref);
    }
    public static int getInt(Object formula) {
        if (formula instanceof Integer)
            return (int) formula;
        //TODO
        return 0;
    }
}
