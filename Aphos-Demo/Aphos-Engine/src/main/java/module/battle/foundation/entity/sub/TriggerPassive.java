package module.battle.foundation.entity.sub;

import elements.content.battle.types.EntityTypes;
import elements.exec.EntityRef;
import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import module.battle.foundation.entity.field.Unit;
import module.battle.foundation.entity.sub.generic.ExecEntity;

import java.util.Map;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/21/2023
 *
 *
 *
 */
public class TriggerPassive extends ExecEntity {
    protected EntityTypes.PassiveType type; //NOT NEEDED? Only Trigger VS Stats , where stats might be diff CLASS
    // stat-based passives - auras or just boost e.g. at DD or other states/conditions


    public TriggerPassive(Map<String, Object> valueMap, Map<String, Object> varMap, Unit unit) {
        super(valueMap, varMap, unit);
    }

    public void apply() {
        //can be of TWO types - TRIGGER or MODIFIER ; both can have condition

        EntityRef ref = new EntityRef(unit);
        ref.setMatch(unit);
        if (activationCondition.check(ref)) { //is this not enough for RETAIN?

            //IDEA - boosted passives on CONDITION?
            //re-build exec in case something has changed? I'd rather substitute vars!
            Executable exec = ExecBuilder.initExecutable(this, false);
            // wrap this exec? not in yaml!

            // List<AddTriggerFx> list = EffectUtils.getByClass(exec.getEffect(), AddTriggerFx.class);
            // list.forEach(e-> e.getTrigger().setRetainCondition(condition));
            //add trigger - retain on build() level
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
