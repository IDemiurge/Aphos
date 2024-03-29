package tests.basic_init.exec;

import elements.exec.EntityRef;
import elements.exec.build.ConditionBuilder;
import elements.exec.condition.Condition;
import framework.data.DataManager;
import tests.basic_init.basic.BattleInitTest;

import static elements.exec.build.template.ConditionTemplate.SELF_VALUE_CHECK;

/**
 * Created by Alexander on 8/25/2023
 *
 * types of conditions - filters vs direct
 */
public class ConditionTest extends BattleInitTest {
    @org.junit.jupiter.api.Test
    public void test() {
        String data="value=3;key=Armor";
        Condition build = ConditionBuilder.build(SELF_VALUE_CHECK, DataManager.deconstructDataString(data));

        check(build.check(new EntityRef(ally)));
        ally.setCur("armor", 1);
        check(!build.check(new EntityRef(ally)));

    }
}
