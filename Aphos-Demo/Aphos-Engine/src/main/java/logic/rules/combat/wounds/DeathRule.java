package logic.rules.combat.wounds;

import elements.content.enums.stats.unit.UnitParam;
import elements.content.enums.stats.unit.UnitProp;
import framework.combat.entity.field.FieldEntity;

/**
 * Created by Alexander on 11/1/2023
 */
public class DeathRule {

    /**
     * @param entity
     * @return true for Soul-death, false for Body-death, null otherwise
     * To be checked after modifications? Any effect that does anything should run this check after apply() and if for
     * any reason a unit is dead - we will attribute death to this effect and its triggerer.
     *
     */
    public static Boolean checkDead(FieldEntity entity){
        if (entity.getInt(UnitParam.Soul) <= 0) {
            return true;
        }
        if (entity.getInt(UnitParam.Health) <=  getDeathBarrier(entity)) {
            return true;
        }
        if (entity.isTrue(UnitProp.Wound_Body) &&
                entity.isTrue(UnitProp.Wound_Head) &&
                    entity.isTrue(UnitProp.Wound_Limbs))
            return false;
        return null;
    }

    private static int getDeathBarrier(FieldEntity entity) {
        return entity.getInt(UnitParam.Health_Total) * getDeathBarrierMod(entity);
    }

    private static int getDeathBarrierMod(FieldEntity entity) {
        return -1 ; //TODO + mod
    }
}
