package module.battle.foundation.battlefield.helpers;

import module.battle.foundation.battlefield.FieldConsts;
import module.battle.foundation.battlefield.enums.Cell;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 10/21/2023
 */
public class XYGeometry {
    private static final Map<Integer, Cell> cellMap = new HashMap<>();
    public static final Integer MIDDLE_Y = 1;

    static {
        //Initialize XY Cells
        for (Cell cell : Cell.values()) {
            cellMap.put(getKey(cell.x, cell.y), cell);
        }
    }

    private static Integer getKey(Integer x, Integer y) {
        int key = Math.abs(x) * 10 + y;
        if (x < 0) key = -key;
        return key;
    }

    //duplicates logic in ENUM - could use enum.get + abs
    public static int getX(FieldConsts.CellType type) {
        return switch (type) {
            case Rear -> 3;
            case Back -> 2;
            case Front -> 1;
            case Flank -> Cell.Bottom_Flank_Player.x;
            case Van -> Cell.Vanguard_Bot.x;
            default -> 0;
        };
    }

    public static int getY(FieldConsts.CellType type, Boolean top) {
        if (type == FieldConsts.CellType.Rear) {
            return Cell.Rear_Enemy.y;
        }
        if (type == FieldConsts.CellType.Flank) {
            return top ? Cell.Top_Flank_Enemy.y : Cell.Bottom_Flank_Enemy.y;
        }
        if (type == FieldConsts.CellType.Van) {
            return top ? Cell.Vanguard_Top.y : Cell.Vanguard_Bot.y;
        }
        return
                top == null ? 1 : top ? 2 : 0;

    }

    public static XYCell get(int x, int y) {
        return new XYCell(x, y);
    }

    public static int dst(XYCell cell1, XYCell cell2) {
        int diffX = Math.abs(cell1.x - cell2.x);
        int diffY = Math.abs(cell1.y - cell2.y);
        return (int) Math.round(Math.sqrt(diffX*diffX+diffY*diffY));
    }

    public static class XYCell {
        int x, y;

        public XYCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Cell cell() {
            return cellMap.get(getKey(x, y)); //resolve - hash map by key?
        }
    }
}
