package elements.exec.generic.condition.common;

import elements.exec.EntityRef;
import elements.exec.generic.condition.ConditionImpl;
import module.battle.foundation.entity.field.Unit;

/**
 * Created by Alexander on 10/27/2023
 */
public class AllyCondition extends ConditionImpl {
    @Override
    protected boolean checkThis(EntityRef ref) {
        if (ref.getMatch() instanceof Unit unit) {
            return unit.isAlly() == ref.getSource().isAlly();
        }
        return false;
    }
}
