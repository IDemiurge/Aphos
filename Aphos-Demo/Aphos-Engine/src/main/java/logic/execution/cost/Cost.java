package logic.execution.cost;

import elements.exec.EntityRef;
import elements.stats.UnitParam;

import java.util.Map;

import static system.math.Formula.eval;

/**
 * Created by Alexander on 10/27/2023
 */
public class Cost {
    private final Map<UnitParam, String> map;
    //map to formula

    public Cost(Map<UnitParam, String> map) {
        this.map = map;
    }

    public UnitParam canPay(EntityRef ref){
        for (UnitParam param : map.keySet()) {
            if (!CostHandler.canPay(ref, param, map.get(param))) {
                return param;
            }
        }
        return null;
    }
    public void pay(EntityRef ref){
        map.forEach((p, val)->
                ref.getSource().modifyValue(p, -eval(val, ref)));
        // CostResult result = new CostResult();
        //check dead? :)
    }
}
