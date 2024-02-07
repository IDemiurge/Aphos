package framework.combat.field;

import framework.combat.field.enums.Cell;
import framework.combat.field.enums.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexander on 6/13/2023 what other static data do we need?
 */
public class FieldConsts {
    private static final Map<Cell, Set<Cell>> adjMap = new HashMap<>();
    private static final Map<Cell, Map<Direction, Cell>> adjMapDirection = new HashMap<>();

    //IDEA: what if the order of this set determined Direction? ...
    // OR we could try to use X:Y here
    //Special logic: Flank - has 2 pos on bottom?! Well, it's bottom_left/right! interesting...
    public static void putAdj(Cell key, Set<Cell> set){
        adjMap.put(key, set);
        //TODO
        // Map<Direction, Cell> dirMap= new HashMap<>();
        // adjMapDirection.put(key, dirMap);
        // for (Cell cell : set) {
        //     for (Direction value : Direction.values()) {
        //         if (key.getDirection(cell)==value)
        //             dirMap.put(value, cell);
        //     }
        // }
    }
    static {
        putAdj(Cell.Front_Enemy_1, Set.of(Cell.Vanguard_Top, Cell.Back_Enemy_2, Cell.Back_Enemy_1, Cell.Front_Enemy_2, Cell.Top_Flank_Enemy));
        adjMap.put(Cell.Front_Enemy_2, Set.of(Cell.Vanguard_Top, Cell.Vanguard_Bot, Cell.Back_Enemy_3, Cell.Back_Enemy_2, Cell.Back_Enemy_1, Cell.Front_Enemy_1, Cell.Front_Enemy_3));
        adjMap.put(Cell.Front_Enemy_3, Set.of(Cell.Vanguard_Bot, Cell.Back_Enemy_2, Cell.Back_Enemy_3, Cell.Front_Enemy_2, Cell.Bottom_Flank_Enemy));

        adjMap.put(Cell.Back_Enemy_2, Set.of(Cell.Rear_Enemy, Cell.Back_Enemy_1, Cell.Back_Enemy_3, Cell.Front_Enemy_1, Cell.Front_Enemy_2, Cell.Front_Enemy_3));
        adjMap.put(Cell.Back_Enemy_3, Set.of(Cell.Rear_Enemy, Cell.Back_Enemy_2, Cell.Front_Enemy_3, Cell.Front_Enemy_2, Cell.Bottom_Flank_Enemy));

        adjMap.put(Cell.Back_Enemy_1, Set.of(Cell.Rear_Enemy, Cell.Back_Enemy_2, Cell.Front_Enemy_1, Cell.Front_Enemy_2, Cell.Top_Flank_Enemy));
        adjMap.put(Cell.Back_Enemy_2, Set.of(Cell.Rear_Enemy, Cell.Back_Enemy_1, Cell.Back_Enemy_3, Cell.Front_Enemy_1, Cell.Front_Enemy_2, Cell.Front_Enemy_3));
        adjMap.put(Cell.Back_Enemy_3, Set.of(Cell.Rear_Enemy, Cell.Back_Enemy_2, Cell.Front_Enemy_3, Cell.Front_Enemy_2, Cell.Bottom_Flank_Enemy));
        adjMap.put(Cell.Bottom_Flank_Enemy, Set.of(Cell.Rear_Enemy, Cell.Vanguard_Bot, Cell.Back_Enemy_3, Cell.Front_Enemy_3, Cell.Bottom_Flank_Player));
        adjMap.put(Cell.Top_Flank_Enemy, Set.of(Cell.Rear_Enemy, Cell.Vanguard_Top, Cell.Back_Enemy_1, Cell.Front_Enemy_1, Cell.Top_Flank_Player));
        adjMap.put(Cell.Rear_Enemy, Set.of(Cell.Back_Enemy_1, Cell.Back_Enemy_2, Cell.Back_Enemy_3, Cell.Bottom_Flank_Enemy, Cell.Top_Flank_Enemy));

        adjMap.put(Cell.Front_Player_1, Set.of(Cell.Vanguard_Top, Cell.Back_Player_2, Cell.Back_Player_1, Cell.Front_Player_2, Cell.Top_Flank_Player));
        adjMap.put(Cell.Front_Player_2, Set.of(Cell.Vanguard_Top, Cell.Vanguard_Bot, Cell.Back_Player_3, Cell.Back_Player_2, Cell.Back_Player_1, Cell.Front_Player_1, Cell.Front_Player_3));
        adjMap.put(Cell.Front_Player_3, Set.of(Cell.Vanguard_Bot, Cell.Back_Player_2, Cell.Back_Player_3, Cell.Front_Player_2, Cell.Bottom_Flank_Player));

        adjMap.put(Cell.Back_Player_2, Set.of(Cell.Rear_Player, Cell.Back_Player_1, Cell.Back_Player_3, Cell.Front_Player_1, Cell.Front_Player_2, Cell.Front_Player_3));
        adjMap.put(Cell.Back_Player_3, Set.of(Cell.Rear_Player, Cell.Back_Player_2, Cell.Front_Player_3, Cell.Front_Player_2, Cell.Bottom_Flank_Player));

        adjMap.put(Cell.Back_Player_1, Set.of(Cell.Rear_Player, Cell.Back_Player_2, Cell.Front_Player_1, Cell.Front_Player_2, Cell.Top_Flank_Player));
        adjMap.put(Cell.Back_Player_2, Set.of(Cell.Rear_Player, Cell.Back_Player_1, Cell.Back_Player_3, Cell.Front_Player_1, Cell.Front_Player_2, Cell.Front_Player_3));
        adjMap.put(Cell.Back_Player_3, Set.of(Cell.Rear_Player, Cell.Back_Player_2, Cell.Front_Player_3, Cell.Front_Player_2, Cell.Bottom_Flank_Player));
        adjMap.put(Cell.Bottom_Flank_Player, Set.of(Cell.Rear_Player, Cell.Vanguard_Bot, Cell.Back_Player_3, Cell.Front_Player_3, Cell.Bottom_Flank_Enemy));
        adjMap.put(Cell.Top_Flank_Player, Set.of(Cell.Rear_Player, Cell.Vanguard_Top, Cell.Back_Player_1, Cell.Front_Player_1, Cell.Top_Flank_Enemy));
        adjMap.put(Cell.Rear_Player, Set.of(Cell.Back_Player_1, Cell.Back_Player_2, Cell.Back_Player_3, Cell.Bottom_Flank_Player, Cell.Top_Flank_Player));
        
        adjMap.put(Cell.Vanguard_Top, Set.of(Cell.Vanguard_Bot,  Cell.Front_Enemy_2, Cell.Front_Enemy_1,Cell.Front_Player_2, Cell.Front_Player_1, Cell.Top_Flank_Enemy));
        adjMap.put(Cell.Vanguard_Bot, Set.of(Cell.Vanguard_Top,  Cell.Front_Enemy_2, Cell.Front_Enemy_3,Cell.Front_Player_2, Cell.Front_Player_3, Cell.Bottom_Flank_Enemy));
    }

    protected static Set<Cell> getAdjacent(Cell cell) {
        return adjMap.get(cell);
    }

    protected static Cell getAdjacent(Cell cell, Direction direction) {
        //aha! so left/right is actually top/bot? Yeah so far I don't wanna bother with FACING - but maybe we could
        //consider some FLIP ?
        return  adjMapDirection.get(cell).get(direction);

    }

    public enum Shape {
        Line,
    }

    public enum CellType {
        Flank,
        Van,
        Front,
        Back,
        Rear,
        Reserve,
    }


    public static final FieldPos[] all = {
            new FieldPos(Cell.Vanguard_Bot), //0
            new FieldPos(Cell.Vanguard_Top), //1

            new FieldPos(Cell.Top_Flank_Player), //2
            new FieldPos(Cell.Front_Player_1), //3
            new FieldPos(Cell.Front_Player_2), //4
            new FieldPos(Cell.Front_Player_3), //5
            new FieldPos(Cell.Back_Player_1), //6
            new FieldPos(Cell.Back_Player_2), //7
            new FieldPos(Cell.Back_Player_3), //8
            new FieldPos(Cell.Bottom_Flank_Player), //9
            new FieldPos(Cell.Rear_Player), //10

            new FieldPos(Cell.Top_Flank_Enemy), //11
            new FieldPos(Cell.Front_Enemy_1), //12
            new FieldPos(Cell.Front_Enemy_2), //13
            new FieldPos(Cell.Front_Enemy_3), //14
            new FieldPos(Cell.Back_Enemy_1), //15
            new FieldPos(Cell.Back_Enemy_2), //16
            new FieldPos(Cell.Back_Enemy_3), //17
            new FieldPos(Cell.Bottom_Flank_Enemy), //18
            new FieldPos(Cell.Rear_Enemy), //19

    };
}
