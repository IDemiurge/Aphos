package framework.entity.sub;

import elements.content.enums.types.EntityTypes;
import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import framework.entity.field.Unit;
import logic.execution.cost.Cost;

import java.util.Map;

/**
 * Created by Alexander on 8/20/2023
 */
public class UnitAction extends UnitSubEntity {
    private EntityTypes.ActionType type;
    private Executable executable;
    private Cost cost;
    // ActiveAbility active;  encapsulate targeting, wrap in props.get(targeting)
    // Effects boostEffects; //modify, add trigger fx, etc
    // boostMode //how to apply fx

    public UnitAction(Map<String, Object> valueMap, Unit unit) {
        super(valueMap, unit);
        executable = ExecBuilder.initExecutable(this);
        cost = logic.execution.cost.CostFactory.get(valueMap);
    }

    public Executable getExecutable() {
        return executable;
    }

    public boolean isSpell() {
        return false;
    }

    public boolean isAttack() {
        return getType() != EntityTypes.ActionType.Defense;
    }

    public EntityTypes.ActionType getType() {
        if (type==null) {
            type = getEnum("action_type", EntityTypes.ActionType.class);
        }
        return type;
    }

    public Cost getCost() {
        return cost;
    }

    public void executed() {
        unit.getActionSet().setLastAction(this);
    }
}
