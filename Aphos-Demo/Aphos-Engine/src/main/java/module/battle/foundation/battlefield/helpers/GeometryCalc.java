package module.battle.foundation.battlefield.helpers;

import module.battle.foundation.battlefield.GeomFunctions;
import module.battle.foundation.battlefield.enums.Cell;
import module.battle.foundation.battlefield.helpers.XYGeometry;
import module.battle.foundation.battlefield.FieldPos;
import utils.collection.SortUtils;

/**
 * Created by Alexander on 11/20/2023
 */
public class GeometryCalc {

    public static int dst(FieldPos src, FieldPos dest){
        //for multi - use the one closest to the other
        Cell  srcCell = checkClosestMulti(src, dest);
        Cell  destCell = checkClosestMulti(dest, src);
        return dst(srcCell, destCell);
    }

    private static Cell checkClosestMulti(FieldPos src, FieldPos dest) {
        if (src.getCell() == Cell.Multi){
            //stream - filter by equals() and sort by dst?
            Cell cell = GeomFunctions.getAdjacent(src.getCell()).stream()
                    .filter(c -> c.equals(src.getCell()))
                    .sorted(SortUtils.comparator(c -> dst(c, dest.getCell()))).findFirst().orElse(null);
            return cell;
        }
        return src.getCell();
    }

    public static int dst(Cell src, Cell dest){
        //Q: find by graph adjacency?
        //TODO
        // if (cache)
        XYGeometry.XYCell cell1 = XYGeometry.get(src.x, src.y);
        XYGeometry.XYCell cell2 = XYGeometry.get(dest.x, dest.y);
        return XYGeometry.dst(cell1, cell2);
    }
}
