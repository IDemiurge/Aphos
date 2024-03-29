package system.math;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import system.math.wrap.IArgument;
import system.math.wrap.IExpression;
import system.math.wrap.mx.MxArg;
import system.math.wrap.mx.MxExpression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by Alexander on 11/20/2023
 *
 * WRAP 3rd party stuff!
 */
public class MathCore {
    static ScriptEngine engine = new ScriptEngineManager().getEngineByName("Python");
    public static void main(String[] args) {
        // engine = new org.mozilla.javascript.ScriptEngineManager().getEngineByName("JavaScript");
    }

    public static IExpression expression(String formula) {
        // return mathImpl.expression(formula);
        // return new KtExpression(engine, (formula));
        return new MxExpression(new Expression(formula));
    }

    public static IArgument arg(String wrap, double value) {
        return new MxArg(new Argument(wrap, value));
    }
}
