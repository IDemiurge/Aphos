package tests.basic_init.action;

import elements.content.enums.stats.unit.UnitParam;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 8/26/2023
 */
public class RetainTest extends BattleInitTest {

    @org.junit.jupiter.api.Test
    public void test() {
        int defense = ally.getInt(UnitParam.Defense_Base);
        defAction(ally);
        check(defense < (ally.getInt(UnitParam.Defense_Base)));
        stdAttack(ally, enemy); //Defense cancels after attack
        check(defense == (ally.getInt(UnitParam.Defense_Base)));
    }

}
