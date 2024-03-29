package tests.basic_init.action;

import elements.exec.EntityRef;
import elements.exec.build.ConditionBuilder;
import elements.exec.condition.Condition;
import elements.exec.effect.framework.wrap.AddTriggerFx;
import logic.execution.event.combat.CombatEventType;
import org.junit.jupiter.api.Disabled;
import tests.basic_init.basic.BattleInitTest;

import java.util.HashMap;
import java.util.Map;

import static module.battle.handler.BattleManager.combat;
import static elements.exec.build.template.ConditionTemplate.IDENTITY_CHECK;
import static elements.exec.build.template.ConditionTemplate.TARGET;

/**
 * Created by Alexander on 8/24/2023
 */
@Disabled
public class TriggerTest extends BattleInitTest {

    @org.junit.jupiter.api.Test
    public void test() {
        String toTrigger="kill self";
        Map map=new HashMap(); //should have a separate class for arg map?
        map.put("key", "event_target");
        Condition sourceAttack = ConditionBuilder.build(map,
                IDENTITY_CHECK, TARGET);

        EntityRef ref=new EntityRef(ally);
        //so this would be applied by passive - with default retain condition of ... source is alive
        ref.setTarget(enemy);
        new AddTriggerFx(CombatEventType.Unit_Attack_Misses, sourceAttack, toTrigger).apply(ref);

        ref = ref.reversed();
        combat().event(CombatEventType.Unit_Attack_Misses, ref);

        check(enemy.isDead());
    }


    //how do we specify trigger's target?
    //Suppose we make a passive that makes a unit damage adjacent enemies when they move
    //source of event will be target
    //triggered effect must have targeting then
    //so that 'data' has to build into whole EXEC with custom targeting!..
    //it would be nice to have a way to construct these fx+targeting in similar way ... to Builder
    //


    // new AddTriggerFx(CombatEventType.Unit_Being_Moved, builtCondition, data).apply()

    //regen? some passive that works reliably
    //what level of rarity warrants Trigger over code-rule?
    //gain rage when being Hit
    //Living Armor - remove broken armor or restore to full armor
    //
    //modify 100% syntax
}
