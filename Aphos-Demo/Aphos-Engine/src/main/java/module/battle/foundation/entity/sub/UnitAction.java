package module.battle.foundation.entity.sub;

import elements.content.battle.types.EntityTypes;
import elements.exec.EntityRef;
import module.battle.foundation.entity.field.Unit;
import module.battle.foundation.entity.sub.generic.ExecEntity;
import module.battle.foundation.execution.cost.Cost;
import module.battle.foundation.execution.cost.CostFactory;

import java.util.Map;

/**
 * Created by Alexander on 8/20/2023
 */
public class UnitAction extends ExecEntity {
    private EntityTypes.ActionType type;
    private Cost boostCost;
    private Cost cost;

    public UnitAction(Map<String, Object> valueMap, Map<String, Object> varMap, Unit unit) {
        super(valueMap, varMap, unit);
        cost = CostFactory.get(valueMap);

        if (isBoostable()) {
            //TODO
            // boosted = AbilExtensions.boosted(this, executable);
            // boostCost = CostFactory.getBoost(valueMap);
        }
    }

    public boolean isBoostable() {
        //TODO can be other
        return getType() == EntityTypes.ActionType.Power_Attack;
    }

    public boolean isSpell() {
        return false;
    }

    public boolean isAttack() {
        return getType() != EntityTypes.ActionType.Defense;
    }

    public EntityTypes.ActionType getType() {
        if (type == null) {
            type = getEnum("action_type", EntityTypes.ActionType.class);
        }
        return type;
    }

    public Cost getCost() {
        return getCost(false);
    }
    public Cost getCost(boolean boost) {
        return boost ? boostCost : cost;
    }

    public void executed() {
        unit.getActionSet().setLastAction(this);
    }

    @Override
    public EntityRef ref() {
        return new EntityRef(unit).setAction(this);
    }
}
