package system.math;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import system.math.wrap.IExpression;
import system.math.wrap.MathExpression;

/**
 * Created by Alexander on 11/20/2023
 *
 * WRAP 3rd party stuff!
 */
public class MathCore {
    public static void main(String[] args) {

        Function f = new Function("param(n) = ");
        Function f1 = new Function("counter(n) = ");
        Expression e = new Expression("", f);
        Argument arg = new Argument("", 0); //only numeric args, eh?

        e.calculate();
    }

    public static IExpression expression() {
        return new MathExpression(new Expression());
    }
}
