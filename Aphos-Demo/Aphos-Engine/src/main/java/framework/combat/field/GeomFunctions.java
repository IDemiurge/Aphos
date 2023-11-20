package framework.combat.field;

import framework.combat.field.enums.Cell;
import framework.combat.field.enums.Direction;

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
