package module.battle.features.rules.death.wounds;

import elements.exec.generic.effect.Effect;
import elements.content.battle.stats.unit.UnitProp;
import module.battle.features.rules.death.wounds.content.Wound;
import module.battle.features.grade_rolls.DieType;

/**
 * Created by Alexander on 8/21/2023
 */
public class SanityWounds extends WoundsRule {
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
