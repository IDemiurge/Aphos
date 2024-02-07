package module.battle.foundation.battlefield;

import module.battle.foundation.battlefield.enums.Cell;
import module.battle.foundation.battlefield.enums.Direction;

import java.util.Set;

/**
 * Created by Alexander on 11/20/2023
 */
public class GeomFunctions {

    public static Cell getAdjacent(Cell cell, Direction direction) {
        return FieldConsts.getAdjacent(cell, direction);
    }

    public static Set<Cell> getAdjacent(Cell cell) {
        return FieldConsts.getAdjacent(cell);
    }
}
