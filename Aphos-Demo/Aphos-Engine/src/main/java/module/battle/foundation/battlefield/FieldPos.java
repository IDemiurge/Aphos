package module.battle.foundation.battlefield;

import module.battle.foundation.battlefield.enums.Cell;
import module.battle.foundation.battlefield.enums.Direction;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 6/10/2023
 *
 * 2x3 plus flanks, vanguard and rear Additional flanks/rear (due to ambush)
 *
 * In terms of coordinates - get range between function!
 */
public class FieldPos {
    private FieldPos[] areaPos;
    private final Cell cell;

    public FieldPos(FieldPos... pos) {
        areaPos=pos;
        cell = Cell.Multi;
    }
    public FieldPos(int cardinal) {
        this(FieldConsts.all[cardinal].getCell());
    }
    public FieldPos(Cell cell) {
        this.cell = cell;
    }

    public boolean isFlank() {
        return cell.isFlank();
    }

    @Override
    public boolean equals(Object obj) {
        //consider that we need intersection!
        if (obj instanceof FieldPos) {
            if (cell == Cell.Multi) {
                for (FieldPos pos : areaPos) {
                    if (obj.equals(pos))
                        return true;
                }
            }
            if (((FieldPos) obj).getCell().equals(cell)) {
                return true;
            }
        }
        return false;
    }
    public Cell getCell() {
        return cell;
    }

    public FieldPos getAdjacent(Direction direction) {
        return  combat().getField().getPos(GeomFunctions.getAdjacent(getCell(), direction));
    }

    // public FieldPos[] getAreaPos() {
    //     return areaPos;
    // }
    // public Integer getX() {
    //     return cell.x;
    // }
    // public Integer getY() {
    //     return cell.y;
    // }
}
