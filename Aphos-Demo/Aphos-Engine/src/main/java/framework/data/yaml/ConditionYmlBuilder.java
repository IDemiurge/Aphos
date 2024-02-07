package framework.data.yaml;

import elements.exec.build.ConditionBuilder;
import elements.exec.condition.Condition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 10/27/2023
 */
public class ConditionYmlBuilder {
    public static Condition condition(Object o) {
        return condition(o, new HashMap());
    }
    public static Condition condition(Object o, Map args) {
        if (o instanceof String condTempl){
            return  ConditionBuilder.build(condTempl, args);
        } else {
            //nested map?
        }
        return null;
    }
}
