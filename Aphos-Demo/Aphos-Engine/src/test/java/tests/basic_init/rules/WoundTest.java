package tests.basic_init.rules;

import elements.exec.EntityRef;
import elements.content.enums.stats.unit.UnitParam;
import elements.content.enums.stats.unit.UnitProp;
import logic.rules.combat.wounds.Wounds;
import org.junit.jupiter.api.Disabled;
import framework.math.roll.Rolls;
import tests.basic_init.basic.BattleInitTest;

import static framework.math.DieType.d6;


/**
 * Created by Alexander on 10/24/2023
 */
@Disabled
public class WoundTest extends BattleInitTest {

    @org.junit.jupiter.api.Test
    public void test() {
        check(enemy.getInt(UnitParam.Defense_Auto_Fail) == 0);
        Rolls.setNext(d6, 1);
        EntityRef ref = new EntityRef(ally).setTarget(enemy);
        Wounds.apply(0, UnitParam.Health, ref);
        //pack into tick / reset
        //do we want continuous to be applied immediately?
        check(enemy.getInt(UnitParam.Defense_Auto_Fail) == 2);
        reset(2);
        check(enemy.getInt(UnitParam.Defense_Auto_Fail) == 2);
        enemy.setValue(UnitProp.Wound_Body, false);
        reset(2);
        check(enemy.getInt(UnitParam.Defense_Auto_Fail) == 0);

        //will have 2 auto-fails on defense? AYE! Or rather, enemy has 2 auto-successes?
    }

}
