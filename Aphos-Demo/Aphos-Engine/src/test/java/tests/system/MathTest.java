package tests.system;

import elements.content.enums.stats.unit.UnitParam;
import elements.exec.EntityRef;
import system.math.Formula;
import org.junit.jupiter.api.Test;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 11/6/2023
 *
 * Test that var-stat perk gives what it's supposed
 *
 * IDEA: maybe upon reset(), each unit can have its abils propagate all stats used in vars and then
 * we can add their actual values to global context ?
 */

//Wrap 3d Party API!
public class MathTest extends BattleInitTest {

    @Test
    public void test(){
        reset(1);
        String param= UnitParam.Health.getName();
        String formula= "%Source_" +
                param +
                "%*3" +
                "+2*" +
                "%target_" +
                param +
                "%";
        compare(Formula.eval(formula, new EntityRef(ally).setTarget(enemy)) ,
                ally.getInt(param)*3+enemy.getInt(param)*2);


    }
}
