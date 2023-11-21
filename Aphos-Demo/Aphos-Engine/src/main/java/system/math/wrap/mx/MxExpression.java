package system.math.wrap.mx;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import system.math.MathCore;
import system.math.wrap.IExpression;

/**
 * Created by Alexander on 11/20/2023
 */
public class MxExpression implements IExpression {

    private final Expression expression;

    public MxExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int calculate() {
        int result = (int) Math.round(expression.calculate());
        return result;
    }


    @Override
    public void setArgumentValue(String name, double value) {
        expression.addArguments((Argument) MathCore.arg(name, value).unwrap());
    }
}
