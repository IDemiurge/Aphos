package logic.rules.combat.wounds;

import elements.exec.EntityRef;
import elements.exec.condition.Condition;
import elements.exec.condition.value.PropCondition;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.ContinuousEffect;
import elements.content.enums.stats.unit.UnitProp;
import framework.combat.entity.Entity;
import logic.rules.combat.wounds.content.Wound;
import system.log.result.WoundResult;
import framework.math.DieType;
import framework.math.roll.Rolls;

/**
 * Created by Alexander on 8/22/2023
 */
public abstract class WoundsRule {

    public WoundResult apply(int excessDamage, EntityRef ref) {
        Entity target = ref.get("target");
        Wound wound = getWound(target);
        addWound(target, wound);
        Effect effect = getEffect(wound);
        effect = new ContinuousEffect(effect, getRetainCondition());
        effect.apply(ref.copy().setValueInt(excessDamage));

        // effect = getDD_ConditionalEffect(wound);
        // effect = new ContinuousEffect(effect, new Conditions(new DeathDoorCondition(), getRetainCondition()));
        effect.apply(ref.copy());

        WoundResult result= new WoundResult();
        return result;
    }

    private void addWound(Entity target, Wound wound) {
        target.setValue(getWoundValue(wound), true);
    }

    private Wound getWound(Entity target) {
        int rolled = Rolls.roll(getDie());
        //some target-checks?
        return  getWound(rolled);
    }
    private Condition getRetainCondition() {
        return new PropCondition(UnitProp.Wound_Body);
    }

    protected abstract Effect getEffect(Wound wound);

    protected abstract UnitProp getWoundValue(Wound wound);

    protected abstract DieType getDie();

    protected abstract Wound getWound(int rolled);
}
