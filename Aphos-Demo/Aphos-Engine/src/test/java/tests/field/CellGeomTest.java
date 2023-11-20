package tests.field;

import framework.combat.field.CellFinder;
import framework.combat.field.enums.Cell;
import framework.combat.field.FieldConsts;
import framework.AphosTest;
import logic.calculation.GeometryCalc;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Created by Alexander on 10/21/2023
 */
@Disabled
public class CellGeomTest extends AphosTest {

    @Test
    public void rangeTest() {
        check(GeometryCalc.dst(Cell.Back_Enemy_1, Cell.Back_Enemy_2) == 1);

    }
    @Test
    public void test() {
        check(CellFinder.get(FieldConsts.CellType.Front, true, true) == Cell.Front_Player_3);
        check(CellFinder.get(FieldConsts.CellType.Back, true, false) == Cell.Back_Player_1);
        check(CellFinder.get(FieldConsts.CellType.Flank, false, true) == Cell.Top_Flank_Enemy);
        check(CellFinder.get(FieldConsts.CellType.Rear, true, null) == Cell.Rear_Player);
        check(CellFinder.get(FieldConsts.CellType.Van, true, true) == Cell.Vanguard_Top);
    }
}
