package logic.rules.combat.wounds;

import elements.exec.effect.Effect;
import elements.content.enums.stats.unit.UnitProp;
import logic.rules.combat.wounds.content.Wound;
import framework.math.DieType;

/**
 * Created by Alexander on 8/22/2023
 */
public class FaithWounds extends WoundsRule{
    @Override
    protected Effect getEffect(Wound wound) {
        return null;
    }

    @Override
    protected UnitProp getWoundValue(Wound wound) {
        return null;
    }

    @Override
    protected DieType getDie() {
        return null;
    }

    @Override
    protected Wound getWound(int rolled) {
        return null;
    }
}
