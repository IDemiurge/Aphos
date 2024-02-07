package framework.data.yaml;

import system.utils.EnumFinder;
import elements.exec.build.ConditionBuilder;
import elements.exec.build.template.ConditionTemplate;
import elements.exec.build.template.TargetingTemplate;
import elements.exec.battle.targeting.Targeting;
import framework.data.TypeData;

import java.util.Map;

/**
 * Created by Alexander on 10/28/2023
 */
public class TargetingYmlBuilder {
    public static Targeting targeting(Object targetNode, Map args) {
        //condition - , [list]
        //can be for what? only targeting? but also trigger!
        Targeting targeting = new Targeting();

        if (targetNode instanceof String) {
            TargetingTemplate targetingTmlt = EnumFinder.get(TargetingTemplate.class, targetNode.toString());
            targeting.setCondition(targetingTmlt.supplier.get());
            targeting.setType(targetingTmlt.type);
        } else {
            if (targetNode instanceof Map) {
                Map map = (Map) targetNode;
                ConditionTemplate tmplt = EnumFinder.get(ConditionTemplate.class, map.get("condition"));
                targeting.setType(EnumFinder.get(Targeting.TargetingType.class, map.get("type")));
                targeting.setCondition(ConditionBuilder.build(tmplt, args));
            }
        }
        targeting.setData(new TypeData(args)); //blunt but will work? :)
        return targeting;
    }
}
