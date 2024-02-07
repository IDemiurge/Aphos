package tests.basic_init.advanced;

import elements.content.enums.types.CombatTypes;
import elements.exec.EntityRef;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import system.utils.TestUtils;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 10/31/2023
 */
@Disabled
public class PassiveTest extends BattleInitTest {
    @Test
    public void test(){
        check(enemy.getPassives().has("Clumsy"));
        EntityRef ref=new EntityRef(ally);
        //so this would be applied by passive - with default retain condition of ... source is alive
        ref.setTarget(enemy);

        TestUtils.fix("grade", CombatTypes.RollGrade.Miss);
        // eventExpected()
        stdAttack(enemy, ally);

        //event check?

    }
}
