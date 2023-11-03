package logic.rules.combat.wounds;

import elements.stats.UnitParam;
import elements.stats.UnitProp;
import framework.entity.field.FieldEntity;

/**
 * Created by Alexander on 11/1/2023
 */
public class DeathRule {

    /**
     * @param entity
     * @return true for Soul-death, false for Body-death, null otherwise
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
