package module.battle.features.rules.damage;

import elements.content.battle.types.CombatTypes;
import elements.content.battle.stats.unit.UnitProp;
import module.battle.foundation.entity.field.FieldEntity;
import module.battle.foundation.entity.field.Unit;

/**
 * Created by Alexander on 10/24/2023
 */
public class ImmuneRule {
    public static boolean checkImmune(FieldEntity target, CombatTypes.DamageType type, Unit source) {
        //penetration
        return target.checkContainerProp(UnitProp.Immune, type.toString());
        //check bonus prop - if some FX gives Immune on top of base ! Can it?..
    }

}
