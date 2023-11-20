package system.math.wrap;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.FunctionExtension;

/**
 * Created by Alexander on 11/20/2023
 */
public class MathExpression implements IExpression {

    private final Expression expression;

    public MathExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int calculate() {
        int result = (int) Math.round(expression.calculate());

        return result;
    }

    public void addFunction(/*IFunction f */){
        // FunctionExtension e= new FunctionExtension() {
        //     @Override
        //     public int getParametersNumber() {
        //         return 0;
        //     }
        //
        //     @Override
        //     public void setParameterValue(int parameterIndex, double parameterValue) {
        //
        //     }
        //
        //     @Override
        //     public String getParameterName(int parameterIndex) {
        //         return null;
        //     }
        //
        //     @Override
        //     public double calculate() {
        //         return 0;
        //     }
        //
        //     @Override
        //     public FunctionExtension clone() {
        //         return null;
        //     }
        // };
        // Function f= new Function("", e);
        // expression.addFunctions(f);
    }

    @Override
    public void setArgumentValue(String name, double value) {
        expression.setArgumentValue(name, value);
    }
}
