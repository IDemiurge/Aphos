package framework.combat.field.enums;

import framework.combat.field.FieldConsts;

/**
 * Created by Alexander on 11/20/2023
 */ //X / Y are set wrong and may be deprecated!
public enum Cell {
    Multi(555, 555, null),
    Reserve_ally(999, 999, FieldConsts.CellType.Reserve),
    Reserve_enemy(666, 666, FieldConsts.CellType.Reserve),

    Vanguard_Bot(0, 1, FieldConsts.CellType.Van), //2 vals? /100 and %100
    Vanguard_Top(0, 2, FieldConsts.CellType.Van),
    //TODO SWAP minus
    Rear_Player(3, 1, FieldConsts.CellType.Rear),
    Rear_Enemy(-3, 1, FieldConsts.CellType.Rear),

    Top_Flank_Player(100, 3, FieldConsts.CellType.Flank),
    Top_Flank_Enemy(-100, 3, FieldConsts.CellType.Flank),

    Bottom_Flank_Player(100, -1, FieldConsts.CellType.Flank),
    Bottom_Flank_Enemy(-100, -1, FieldConsts.CellType.Flank),


    Front_Player_1(1, 0, FieldConsts.CellType.Front),
    Back_Player_1(2, 0, FieldConsts.CellType.Back),
    Front_Enemy_1(-1, 0, FieldConsts.CellType.Front),
    Back_Enemy_1(-2, 0, FieldConsts.CellType.Back),

    Front_Player_2(1, 1, FieldConsts.CellType.Front),
    Back_Player_2(2, 1, FieldConsts.CellType.Back),
    Front_Enemy_2(-1, 1, FieldConsts.CellType.Front),
    Back_Enemy_2(-2, 1, FieldConsts.CellType.Back),

    Front_Player_3(1, 2, FieldConsts.CellType.Front),
    Back_Player_3(2, 2, FieldConsts.CellType.Back),
    Front_Enemy_3(-1, 2, FieldConsts.CellType.Front),
    Back_Enemy_3(-2, 2, FieldConsts.CellType.Back),
    ;
    public final Integer x;
    public final Integer y;
    public final FieldConsts.CellType type;

    Cell(Integer x, Integer y, FieldConsts.CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public boolean isPlayerZone() {
        return x > 0 && isMain();
    }

    public boolean isEnemyZone() {
        return x < 0 && isMain();
    }

    public boolean isFlank() {
        return type == FieldConsts.CellType.Flank;
    }

    public boolean isRear() {
        return type == FieldConsts.CellType.Rear;
    }

    public boolean isVan() {
        return type == FieldConsts.CellType.Van;
    }

    public boolean isBack() {
        return type == FieldConsts.CellType.Back;
    }

    public boolean isMain() {
        return type == FieldConsts.CellType.Front || type == FieldConsts.CellType.Back;
    }

    public Direction getDirection(Cell cell) {
        for (Direction direction : Direction.values()) {
            if (check(direction, x, y, cell.x, cell.y))
                return direction;
        }
        return null;
    }

    private boolean check(Direction direction, Integer x, Integer y, Integer x1, Integer y1) {
        //middle position - both? well this is kinda custom...
        //there's UP_LEFT for that!
        switch (direction) {
            //what about positions that are between?
            case UP:
                return x == x1 && y > y1;
            case DOWN:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case UP_LEFT:
                break;
            case UP_RIGHT:
                break;
            case DOWN_RIGHT:
                break;
            case DOWN_LEFT:
                break;
        }
        return false;
    }

}
