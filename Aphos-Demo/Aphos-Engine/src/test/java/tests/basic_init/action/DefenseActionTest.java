package tests.basic_init.action;

import module.battle.foundation.entity.field.Unit;
import module.battle.foundation.entity.sub.UnitAction;
import tests.basic_init.basic.BattleInitTest;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/22/2023
 */
public class DefenseActionTest extends BattleInitTest {
    @org.junit.jupiter.api.Test
    public void test() {
        Unit unit = combat().getUnitById(0);
        int before = unit.getInt("defense_base") ;
        UnitAction action = unit.getActionSet().getDefense();
        combat().getExecutor().activate(action );
        check(
                unit.getInt("defense_base") > before);
        //check that it stacks
        before = unit.getInt("defense_base") ;
        combat().getExecutor().activate(action );
        check(
                unit.getInt("defense_base") > before);

    }
}
