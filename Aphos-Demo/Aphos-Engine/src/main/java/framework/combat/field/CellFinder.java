package framework.combat.field;

import framework.combat.field.enums.Cell;
import framework.combat.field.helpers.XYGeometry;

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
