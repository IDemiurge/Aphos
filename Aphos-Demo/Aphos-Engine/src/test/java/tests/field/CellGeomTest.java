package tests.field;

import framework.combat.field.FieldConsts;
import framework.AphosTest;
import framework.combat.field.FieldGeometry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Created by Alexander on 10/21/2023
 */
@Disabled
public class CellGeomTest extends AphosTest {

    @Test
    public void test() {
        check(FieldGeometry.get(FieldConsts.CellType.Front, true, true) == FieldConsts.Cell.Front_Player_3);
        check(FieldGeometry.get(FieldConsts.CellType.Back, true, false) == FieldConsts.Cell.Back_Player_1);
        check(FieldGeometry.get(FieldConsts.CellType.Flank, false, true) == FieldConsts.Cell.Top_Flank_Enemy);
        check(FieldGeometry.get(FieldConsts.CellType.Rear, true, null) == FieldConsts.Cell.Rear_Player);
        check(FieldGeometry.get(FieldConsts.CellType.Van, true, true) == FieldConsts.Cell.Vanguard_Top);
    }
}
