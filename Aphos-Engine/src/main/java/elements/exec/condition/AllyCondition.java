package elements.exec.condition;

import elements.exec.EntityRef;
import framework.entity.field.Unit;

/**
 * Created by Alexander on 10/27/2023
 */
public class AllyCondition extends ConditionImpl{
    @Override
    protected boolean checkThis(EntityRef ref) {
        if (ref.getMatch() instanceof Unit unit) {
            return unit.isAlly() == ref.getSource().isAlly();
        }
        return false;
    }
}
