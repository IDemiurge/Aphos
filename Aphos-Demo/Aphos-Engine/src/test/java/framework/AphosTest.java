package framework;

import elements.content.enums.stats.unit.UnitParam;
import elements.content.enums.stats.unit.UnitProp;
import framework.data.yaml.YamlBuilder;
import framework.combat.entity.field.Unit;
import system.log.SysLog;
import utils.collection.MathUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import static combat.handler.BattleManager.combat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Alexander on 8/22/2023
 */
public abstract class AphosTest {
    private static boolean testInitDone;
    private Map<UnitParam, Integer> checkParamMap = new HashMap<>();
    private Map<UnitProp, Object> checkPropMap = new HashMap<>();

    public AphosTest() {
        init();
    }

    protected void init() {
        if (testInitDone) return;
        Core.init();
        new YamlBuilder().buildYamlFiles();
        testInitDone = true;
    }

    //aggregate errors instead?
    public void check(boolean bool) {
        check(null, bool);
    }

    public void check(String comment, boolean bool) {
        assertTrue(bool, comment);
    }

    protected void reset(int i) {
        for (int j = 0; j < i; j++) {
            combat().resetAll();
            combat().afterResetAll();
        }
    }

    protected void checkValueChanged(UnitParam param, Integer offset) {
        int value = getCheckedUnit().getInt(param);
        check(getCheckedUnit() + "'s " + param + " is at value " + value, value - checkParamMap.get(param) == offset);
        SysLog.printLine(getCheckedUnit(), "'s ", param, " has changed by " + offset);
    }

    protected void checkValueIsSame(UnitParam... params) {
        checkValues(null, params);
    }

    protected void checkValuesDecreased(UnitParam... params) {
        checkValues(false, params);
    }
    protected void checkValuesIncreased(UnitParam... params) {
        checkValues(true, params);
    }
    protected void checkValues(Boolean more_less_same, UnitParam... params) {
        if (params.length == 0) {
            //all
            if (!checkParamMap.isEmpty())
                checkValueIsSame(checkParamMap.keySet().toArray(new UnitParam[0]));
        } else {
            for (UnitParam param : checkParamMap.keySet()) {
                int value = getCheckedUnit().getInt(param);
                BiPredicate<Integer, Integer> predicate = MathUtils.getComparison(more_less_same);
                check(getCheckedUnit() + "'s " + param + " has changed to " + value,
                        predicate.test(value, checkParamMap.get(param)));
                //++ prefix
                SysLog.printLine(getCheckedUnit(), "'s ", param, getStatusDescription(more_less_same));
            }
        }
    }

    private String  getStatusDescription(Boolean moreLessSame) {
        if (moreLessSame == null)
            return " has not changed";
        if (moreLessSame)
            return " has increased";
        return " has decreased";
    }

    protected void markValueToCheck(UnitParam... params) {
        for (UnitParam param : params) {
            checkParamMap.put(param, getCheckedUnit().getInt(param));
        }
    }

    protected Unit getCheckedUnit() {
        return null;
    }
}
