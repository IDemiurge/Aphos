package elements.exec.generic.effect.wrap;

import elements.exec.generic.condition.Condition;
import elements.exec.generic.effect.Effect;
import module.battle.foundation.execution.event.combat.CombatEventType;

/**
 * Created by Alexander on 10/29/2023
 */
public class PeriodicEffect extends AddTriggerFx {
//trigger retain for passives - automatically?

    public PeriodicEffect(CombatEventType eventType, Condition condition, Effect effect) {
        super(eventType, condition, effect);
    }
}
