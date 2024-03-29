package logic.rules.combat.wounds;

import elements.exec.EntityRef;
import elements.content.enums.stats.unit.UnitParam;
import elements.content.enums.stats.unit.UnitProp;
import framework.combat.entity.field.FieldEntity;

/**
 * Created by Alexander on 8/22/2023
 * 4th wound => death
 * -[HP] => death
 * while at DD, additional penalty from wounds
 *
 */
public class DeathDoorRule {
    public static void apply(EntityRef ref) {
        FieldEntity target = ref.getTarget();
        target.setValue(UnitProp.Death_Door, true);

    }
    // consider DD-RECOVERY - what is needed? Any HP above 0?
    public static Boolean checkDD(FieldEntity entity){
        if (DeathRule.checkDead(entity)){
            return null;
        }
        if (entity.getInt(UnitParam.Health) <=0 ){
            return true;
        }
        return false;
    }
}
