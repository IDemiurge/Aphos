package framework;

import elements.stats.UnitParam;
import elements.stats.UnitProp;
import framework.data.yaml.YamlBuilder;
import framework.entity.field.Unit;
import org.junit.jupiter.api.Test;
import system.log.SysLog;

import java.util.HashMap;
import java.util.Map;

import static combat.sub.BattleManager.combat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Alexander on 8/22/2023
 */
public abstract class AphosTest {
    private static boolean testInitDone;
    private Map<UnitParam, Integer> checkParamMap = new HashMap<>();
    private Map<UnitProp, Object> checkPropMap = new HashMap<>();

    @Test
    public void test() {
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
        SysLog.printOut(getCheckedUnit() , "'s " , param , " has changed by " + offset);
    }

    protected void checkValueIsSame(UnitParam... params) {
        if (params.length == 0) {
            //all
            if (!checkParamMap.isEmpty())
                checkValueIsSame(checkParamMap.keySet().toArray(new UnitParam[0]));
        } else {
            for (UnitParam param : checkParamMap.keySet()) {
                int value = getCheckedUnit().getInt(param);
                check(getCheckedUnit() + "'s " + param + " has changed to " + value, value == checkParamMap.get(param));
                //++ prefix
                SysLog.printOut(getCheckedUnit() , "'s " , param , " has not changed" );
            }
        }
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