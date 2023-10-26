package tests.field;

import framework.entity.field.Unit;
import framework.field.FieldPos;
import org.junit.jupiter.api.Disabled;
import tests.basic_init.basic.BattleInitTest;

import java.util.Set;

import static combat.sub.BattleManager.combat;

/**
 * Created by Alexander on 8/22/2023
 */
@Disabled
public class MoveTest extends BattleInitTest {
    @Override
    public void test() {
        super.test();
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
