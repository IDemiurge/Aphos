package logic.execution.cost;

import elements.content.enums.stats.unit.UnitParam;

import java.util.LinkedHashMap;
import java.util.Map;

import static system.consts.StatUtils.getParam;

/**
 * Created by Alexander on 10/27/2023
 */
public class CostFactory {

    public static Cost get(Map<String, Object> valueMap) {
        Map<UnitParam, String> costMap= new LinkedHashMap<>();
        valueMap.forEach((key, val)->
        {
            if (key.contains(" cost"))
                costMap.put(getParam(key.replace(" cost", "")), val.toString());
        });
        return new Cost(costMap);
    }
}
