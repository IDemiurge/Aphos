package tests.basic_init.content;

import elements.exec.EntityRef;
import elements.exec.build.ExecBuilder;
import elements.stats.UnitParam;
import org.junit.jupiter.api.Test;
import system.KotlinUtils;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 10/27/2023
 */
public class IfElseFxTest extends BattleInitTest {

    @Test
    public void test(){
        ally.addCurValue(UnitParam.Health, -3);
        markValueToCheck(UnitParam.Health);
        EntityRef ref = ally.ref(); //ally is ally of ally ... so heal
        KotlinUtils.Companion.doWithInput(ally, ()-> ExecBuilder.getExecutable("Illuminate").execute(ref));
        checkValueChanged(UnitParam.Health, 2);

        EntityRef ref2 = enemy.ref(); //ally is enemy of enemy ... so Holy dmg
        markValueToCheck(UnitParam.Faith);
        KotlinUtils.Companion.doWithInput(ally, ()-> ExecBuilder.getExecutable("Illuminate").execute(ref2));
        //hard to predict how much
        checkValueChanged(UnitParam.Faith, -2);

    }
}
