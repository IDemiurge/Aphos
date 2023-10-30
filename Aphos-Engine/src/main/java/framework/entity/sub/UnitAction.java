package framework.entity.sub;

import elements.content.enums.types.EntityTypes;
import elements.exec.EntityRef;
import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import framework.entity.field.Unit;
import logic.execution.cost.Cost;
import logic.execution.cost.CostFactory;

import java.util.Map;

/**
 * Created by Alexander on 8/20/2023
 */
public class UnitAction extends UnitSubEntity {
    private final Map<String, Object> varMap;
    private EntityTypes.ActionType type;
    private Cost boostCost;
    private Cost cost;

    public UnitAction(Map<String, Object> valueMap, Map<String, Object> varMap, Unit unit) {
        super(valueMap, unit);
        cost = CostFactory.get(valueMap);
        this.varMap = varMap;

        if (isBoostable()) {
            //TODO
            // boosted = AbilExtensions.boosted(this, executable);
            // boostCost = CostFactory.getBoost(valueMap);
        }
    }

    public boolean isBoostable() {
        //can be other
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

    public Executable getExecutable() {
        return getExecutable(false);
    }
    public Executable getExecutable(boolean boost) {
        Executable executable = ExecBuilder.initExecutable(this, boost);
        //are they stateful? Or are we just creating anew to support Var Map changes?
        return executable;
    }

    public Map<String, Object> getVarMap() {
        return varMap;
    }

    public void executed() {
        unit.getActionSet().setLastAction(this);
    }

    @Override
    public EntityRef ref() {
        return new EntityRef(unit).setAction(this);
    }
}
