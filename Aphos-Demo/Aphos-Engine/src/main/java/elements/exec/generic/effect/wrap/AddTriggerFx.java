package elements.exec.generic.effect.wrap;

import system.utils.EnumFinder;
import elements.exec.EntityRef;
import elements.exec.Executable;
import elements.exec.build.ExecBuilder;
import elements.exec.generic.condition.Condition;
import elements.exec.generic.effect.Effect;
import elements.exec.battle.trigger.ExecTrigger;
import framework.data.TypeData;
import module.battle.foundation.execution.event.combat.CombatEventType;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/24/2023
 *
 * is this a wrapper or superclass??
 */
public class AddTriggerFx extends Effect {
    private CombatEventType eventType;
    private Condition condition;
    private Executable executable;
    private ExecTrigger trigger;

    @Override
    public void setData(TypeData data) {
        super.setData(data);
        eventType = EnumFinder.get(CombatEventType.class, data.get("event_type"));
        //TODO
        // trigger = new Trigger(ExecBuilder.initExecutable());
    }

    public AddTriggerFx(CombatEventType eventType, Condition condition, Effect effect) {
        this(eventType, condition, ExecBuilder.fromEffect(effect));
    }
    public AddTriggerFx(CombatEventType eventType, Condition condition, String execData) {
        this(eventType, condition, ExecBuilder.getExecutable(execData));
    }

    public AddTriggerFx(CombatEventType eventType, Condition condition, Executable executable) {
        this.eventType = eventType;
        this.condition = condition;
        this.executable = executable;
    }

    @Override
    protected void applyThis(EntityRef ref) {
        //clone trigger?!
        //continuous addTriggerEffect does not make much sense now, does it?
        // trigger.setAfter(getData().getB("after"));
        getTrigger().setTargetRef(ref.copy());
        combat().getEventHandler().addTrigger(trigger, eventType);
    }
    public ExecTrigger getTrigger(){
        if (trigger==null){
            this.trigger = new ExecTrigger(condition, executable);
        }
        return trigger;
    }
}
