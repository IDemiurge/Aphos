package tests.basic_init.damage;

import elements.content.enums.types.CombatTypes.DamageType;
import elements.content.enums.stats.unit.UnitParam;
import elements.content.enums.stats.unit.UnitProp;
import logic.rules.combat.WardRule;
import org.junit.jupiter.api.Disabled;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 10/24/2023
 */
@Disabled
public class WardTest extends BattleInitTest {
    @org.junit.jupiter.api.Test
    public void test() {
        check(ally.checkContainerProp(UnitProp.Wards, DamageType.Splash.toString()));

        markValueToCheck(UnitParam.Health, UnitParam.Armor);
        // WardRule.checkWard(ally, DamageType.Splash, enemy);
        stdAttack(enemy, ally);
        //need actual attack for this!
        checkValueIsSame();
        check(ally.isTrue(WardRule.getBrokenKey(DamageType.Splash)));
        stdAttack(enemy, ally);
        checkValuesDecreased(UnitParam.Health, UnitParam.Armor);
        // check(ally.checkContainerProp(UnitProp.Wards, DamageType.Splash.toString()));

    }

}
