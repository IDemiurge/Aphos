package tests.field;

import elements.content.enums.FieldConsts.Cell;
import elements.exec.EntityRef;
import elements.exec.targeting.Targeting;
import elements.exec.targeting.area.CellSets;
import elements.exec.targeting.area.MeleeTargeter;
import framework.entity.field.FieldEntity;
import framework.entity.sub.UnitAction;
import framework.field.Transformer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import system.log.SysLog;
import system.utils.ListUtils;
import tests.basic_init.basic.BattleInitTest;

import java.util.*;

import static combat.sub.BattleManager.combat;

/**
 * Created by Alexander on 10/19/2023 Substitute diff targeting for unit's attack? targeting should at first return us
 * maybe a list of targets?!!!
 */
@Disabled
public class MeleeTargetingTest extends BattleInitTest {
    //TODO extract generic for future targeting tests
    private static Map<Cell, List<Cell>> checkPosMap; //separate for close/long
    public static final Cell[] testPos = CellSets.allyArea;

    private static final List<Cell> targets1 = Arrays.asList(new Cell[]{
    });
/*
            Front_Player_1,
            Front_Player_2,
            Front_Player_3,
            Back_Player_1,
            Back_Player_2,
            Back_Player_3,
 */
    static {
        checkPosMap = new HashMap<>();
        // List<List<Cell>> list = List.of(targets1, targets2, targets3, back_targets1, back_targets2, back_targets3);
        // Iterator<List<Cell>> iterator = list.iterator();
        for (Cell pos : testPos) {
            checkPosMap.put(pos,    new ArrayList<>() );// iterator.next());
        }
    }

    // public void testTargets(boolean full, boolean ) {
    // }
    @Test
    public void test() {

        //cells test
        for (Cell pos : testPos) {
            Set<Cell> cellSet = MeleeTargeter.getCellSet(pos, true, false, false);
            SysLog.printLine(pos,"can target:", ListUtils.represent(cellSet)+"\n");
        }

        //fill entire non-ally board with dummy enemies
        //for each position? check against a static map of position to targets?
        UnitAction action = ally.getActionSet().getStandard();
        for (Cell pos : testPos) {
            ally.setPos(combat().getField().getPos(pos));

                Targeting targeting = action.getExecutable().getTargeting();
                EntityRef ref = new EntityRef(ally);
                List<FieldEntity> list = combat().getEntities().targetFilter(ref, targeting);
                List<Cell> positions = Transformer.toCells(list);

                check(checkPosMap.get(pos).containsAll(positions));
        }
        // action.getExecutable().
    }
}
