package tests.basic_init.exec;

import framework.combat.entity.field.Unit;
import tests.basic_init.basic.BattleInitTest;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/23/2023
 *
 * with retain condition also?
 */
public class ContinuousEffectTest extends BattleInitTest {
    @org.junit.jupiter.api.Test
    public void test() {
        Unit unit = combat().getUnitById(0);
        // conditional effect? reset via moves
        // effect that might change... atk or so
        //it's a pretty rare thing in fact
        // how about counters or some such first?
    }
}
