package elements.exec.battle.effect.modifier;

import elements.content.battle.types.CombatTypes;
import elements.exec.generic.condition.Condition;
import module.battle.foundation.execution.event.combat.CombatEventType;

import java.util.Map;

/**
 * Created by Alexander on 8/25/2023
 */
public class AddDamageEffect extends ModifierEffect {

    private CombatTypes.DamageType damageType;

    public AddDamageEffect(CombatEventType eventType, Condition condition) {
        super(eventType, condition);
    }

    public void modify(Map map) {
        int amount= 1;
        //if null - increase ALL?
        Object o = map.get(damageType);
        if (o!=null){

        } else {
            map.put(damageType, amount);
        }
    }
}
