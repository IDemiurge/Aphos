package system.math;

import elements.exec.EntityRef;
import framework.math.RefCalc;
import org.mariuszgromada.math.mxparser.*;
import system.math.MathCore;
import system.math.wrap.IArgument;
import system.math.wrap.IExpression;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander on 8/25/2023
 * How to make it concise? Will I use that same old math package from Core?
 * Or is there something more modern?
 */
public class Formula {

    private static final String VAR = "%";

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
        //must init args in advance?! wtf
        IExpression e =  MathCore.expression(format(formula));
        for (String s : formula.split(VAR)) {
            if (s.isEmpty() || !s.contains("_"))
                continue;
            double value = RefCalc.eval(s, ref);
            e.setArgumentValue(s, value);
        }
        return e;
    }

    private static String format(String formula) {
        return formula.replace(VAR, "");
    }

    private static String wrap(String s) {
        return VAR+s+VAR;
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
