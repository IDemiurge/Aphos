package module.battle.foundation.battlefield;

import module.battle.foundation.battlefield.enums.Cell;
import module.battle.foundation.battlefield.helpers.XYGeometry;

/**
 * Created by Alexander on 11/20/2023
 */
public class CellFinder {
    public static Cell get(FieldConsts.CellType type, boolean ally, Boolean top) {
        int x = XYGeometry.getX(type);
        int y = XYGeometry.getY(type, top);
        if (!ally)
            x = -x;

        return XYGeometry.get(x, y).cell();
    }
}
