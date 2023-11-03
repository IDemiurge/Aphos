package framework.entity.sub;

import elements.content.enums.types.EntityTypes;
import elements.exec.EntityRef;
import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import framework.entity.field.Unit;

import java.util.Map;

import static combat.sub.BattleManager.combat;

/**
 * Created by Alexander on 8/21/2023
 *
 *
 *
 */
public class UnitPassive extends ExecEntity {
    protected EntityTypes.PassiveType type; //NOT NEEDED? Only Trigger VS Stats , where stats might be diff CLASS

    public UnitPassive(Map<String, Object> valueMap, Map<String, Object> varMap, Unit unit) {
        super(valueMap, varMap, unit);
    }

    public void apply() {
        //on each reset!
        //qualities are in fact just ... props! maybe effects can grant them too... conditional qualities?
        //can be of TWO types - TRIGGER or MODIFIER ; both can have condition

        EntityRef ref = new EntityRef(unit);
        ref.setMatch(unit);
        if (activationCondition.check(ref)) {
            //IDEA - boosted passives on CONDITION?
            Executable exec = ExecBuilder.initExecutable(this, false);
            combat().getExecutor().passiveApplies(this, exec);

        }

        //we don't need to use addTriggerFx, or? yes, apply directly a PassiveTrigger
    }
    // TriggerEvent event; //from templates (w/ param?) to real Events + base conditions
    // ConditionalEffects effects; //smart branching by condition

    public EntityTypes.PassiveType getType() {
        if (type == null) {
            type = getEnum("Passive_type", EntityTypes.PassiveType.class);
        }
        return type;
    }

}
