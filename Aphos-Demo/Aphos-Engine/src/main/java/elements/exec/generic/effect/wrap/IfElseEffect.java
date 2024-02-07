package elements.exec.generic.effect.wrap;

import elements.exec.EntityRef;
import elements.exec.generic.condition.Condition;
import elements.exec.generic.effect.Effect;

/**
 * Created by Alexander on 10/27/2023
 */
public class IfElseEffect extends Effect {
    Condition condition;
    Effect ifEffect;
    Effect elseEffect;

    public IfElseEffect(Condition condition, Effect ifEffect, Effect elseEffect) {
        this.condition = condition;
        this.ifEffect = ifEffect;
        this.elseEffect = elseEffect;
    }

    @Override
    protected void applyThis(EntityRef ref) {
        if (condition.check(ref)) {
            ifEffect.apply(ref);
        } else
            elseEffect.apply(ref);
    }
}
