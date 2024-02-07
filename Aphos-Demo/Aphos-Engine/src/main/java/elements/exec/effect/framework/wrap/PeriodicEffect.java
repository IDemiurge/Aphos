package elements.exec.effect.framework.wrap;

import elements.exec.condition.Condition;
import elements.exec.effect.Effect;
import logic.execution.event.combat.CombatEventType;

/**
 * Created by Alexander on 10/29/2023
 */
public class PeriodicEffect extends AddTriggerFx {
//trigger retain for passives - automatically?

    public PeriodicEffect(CombatEventType eventType, Condition condition, Effect effect) {
        super(eventType, condition, effect);
    }
}
