package tests.basic_init.action;

import elements.content.battle.stats.unit.UnitParam;
import org.junit.jupiter.api.Test;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 10/27/2023
 */
public class CostTest extends BattleInitTest {

    @Test
    public void test(){
        markValueToCheck(UnitParam.AP);
        int cost =2;
        defAction(ally);
        checkValueChanged(UnitParam.AP, -cost);
        check(ally.getActionSet().getDefense().getCost().canPay(ally.ref()) == UnitParam.AP);

    }
}
