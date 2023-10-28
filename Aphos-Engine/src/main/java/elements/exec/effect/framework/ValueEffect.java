package elements.exec.effect.framework;

import elements.exec.EntityRef;
import elements.exec.effect.Effect;

/**
 * Created by Alexander on 10/28/2023
 */
public abstract class ValueEffect extends Effect {

    protected String valueFormula;

    @Override
    protected void applyThis(EntityRef ref) {
        valueFormula = getData().getS("value");
    }

    @Override
    public String getArgs() {
        return "value|";
    }
}
