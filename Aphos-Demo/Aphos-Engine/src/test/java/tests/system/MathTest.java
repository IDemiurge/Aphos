package tests.system;

import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

/**
 * Created by Alexander on 11/6/2023
 *
 * Test that var-stat perk gives what it's supposed
 *
 * IDEA: maybe upon reset(), each unit can have its abils propagate all stats used in vars and then
 * we can add their actual values to global context ?
 */

//Wrap 3d Party API!
public class MathTest {

    @Test
    public void test(){
        Function f = new Function("param(n) = ");
        Function f1 = new Function("counter(n) = ");
        Expression e = new Expression("", f);
        Argument arg = new Argument("", 0); //only numeric args, eh?

        e.calculate();


    }
}
