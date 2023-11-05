package elements.exec.effect.framework.wrap;

import elements.exec.EntityRef;
import elements.exec.condition.Condition;
import elements.exec.effect.Effect;

import static combat.sub.BattleManager.combat;

/**
 * Created by Alexander on 8/23/2023
 */
public class ContinuousEffect extends WrapEffect {

    private Condition retainCondition;

    public ContinuousEffect(Effect effect) {
        this(effect, null);
    }

    public ContinuousEffect(Effect effect, Condition retainCondition) {
        super(effect);
        this.retainCondition = retainCondition;
    }

    @Override
    protected void applyThis(EntityRef ref) {
        effect.apply(ref);
        combat().getBattleState().addEffect(ref, effect, retainCondition);
    }

    @Override
    public String toString() {
        return "Continuous " + effect + "\n retainCondition: " + retainCondition;
    }
}
