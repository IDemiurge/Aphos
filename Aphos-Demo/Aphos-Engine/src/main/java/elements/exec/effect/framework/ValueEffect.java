package elements.exec.effect.framework;

import elements.exec.EntityRef;
import elements.exec.effect.Effect;
import system.log.result.EffectResult;

/**
 * Created by Alexander on 10/28/2023
 */
public abstract class ValueEffect extends Effect {

    protected Object valueFormula;

    @Override
    public EffectResult apply(EntityRef ref) {
        init();
        return super.apply(ref);
    }

    protected void init() {
        //allows for dynamic mods?
        valueFormula = data.get("value");
        //TODO process GRADE 1/2/3
    }
    @Override
    public String getArgs() {
        return "value|";
    }
}
