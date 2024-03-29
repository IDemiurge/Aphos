package tests.field;

import framework.combat.entity.field.Unit;
import framework.combat.field.FieldPos;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tests.basic_init.basic.BattleInitTest;

import java.util.Set;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/22/2023
 */
@Disabled
public class MoveTest extends BattleInitTest {
    @Test
    public void test() {
        Unit unit = combat().getUnitById(0);
        Set<FieldPos> positions = combat().getField().getAvailablePositions(unit);

        /*
        exhaustive move test - for all positions? What is the baseline logic we can check?

         */

        for (FieldPos position : positions) {
            combat().getField().stepMove(unit, position);
            check(unit.getPos().equals(position));
            check(combat().getField().getAvailablePositions(unit).size() == 0);
            combat().newRound();
            check(combat().getField().getAvailablePositions(unit).size() != 0);
            // check( combat().getField().);
        }
    }
}
