package system.math.wrap.kt;

import system.math.wrap.IExpression;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 11/21/2023
 */
public class KtExpression implements IExpression {
    ScriptEngine engine;
    private Map<String, Double> argMap= new HashMap<>();
    private String formula;

    public KtExpression(ScriptEngine engine, String formula) {
        this.engine = engine;
        this.formula = formula;
    }

    @Override
    public int calculate() {
        for (String s : argMap.keySet()) {
            engine.put(s, argMap.get(s));
        }
        try {
            return (int) engine.eval(formula);
        } catch (ScriptException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setArgumentValue(String name, double value) {
        argMap.put(name, value);
    }
}
