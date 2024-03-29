package tests.basic_init.damage;

import elements.content.enums.types.CombatTypes;
import framework.AphosTest;
import framework.combat.entity.field.Unit;
import logic.calculation.GradeCalc;
import utils.old.MapMaster;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 8/25/2023
 */
public class GradeCalcTest extends AphosTest {

    @org.junit.jupiter.api.Test
    public void test() {
        Map<String, Integer> gradesMap = new HashMap();
        for (int j = 0; j < 1000; j++) {
            CombatTypes.RollGrade grade = GradeCalc.calculateGrade(4, 6, 8, 0);
            MapMaster.addToIntegerMap(gradesMap, grade.getName(), 1);
        }
        System.out.println(MapMaster.getNetStringForMap(gradesMap).replace(";", "\n"));
    }

    @Override
    protected Unit getCheckedUnit() {
        return null;
    }
}
