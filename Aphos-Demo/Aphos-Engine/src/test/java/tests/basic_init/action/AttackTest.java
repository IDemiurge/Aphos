package tests.basic_init.action;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tests.basic_init.basic.BattleInitTest;

/**
 * Created by Alexander on 8/22/2023
 */
@Disabled
public class AttackTest extends BattleInitTest {
    @Test
    public void test() {
        stdAttack(enemy, ally);
        //TODO checks!

        int hp = enemy.getInt("hp");
        int armor = enemy.getInt("armor");
        int ap = ally.getInt("ap");
        // testDamage(); some basic variant always used? With preset numbers that system must arrive to?

    }
}
