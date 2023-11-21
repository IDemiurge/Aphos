package logic.execution.cost;

import elements.exec.EntityRef;
import elements.content.enums.stats.unit.UnitParam;
import system.math.Formula;

/**
 * Created by Alexander on 10/27/2023
 */
public class CostHandler {
    public static boolean canPay(EntityRef ref, UnitParam p, String val) {
        int value = Formula.eval(val, ref);
        return ref.getSource().checkParam(p, value);
    }
}
