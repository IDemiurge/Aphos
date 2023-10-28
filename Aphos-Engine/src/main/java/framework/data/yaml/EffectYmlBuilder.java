package framework.data.yaml;

import elements.content.enums.EnumFinder;
import elements.exec.build.template.EffectClass;
import elements.exec.build.template.EffectTemplate;
import elements.exec.condition.Condition;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.ContinuousEffect;
import elements.exec.effect.framework.wrap.Effects;
import framework.data.TypeData;
import system.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 10/27/2023
 */
public class EffectYmlBuilder {

    public static Effect effect(Object effectNode) {
        if (effectNode instanceof List list) {
            return listToEffect(list);
        } else if (effectNode instanceof Map map) {
            return mapToEffect(map);
        } else return effect(effectNode, null, new HashMap());
    }

    private static Effect mapToEffect(Map map) {
        //TODO retain and all
        return effect(map.get("effect"), map.get("retain"), new HashMap());
    }

    private static Effect listToEffect(List list) {
        Effects e = new Effects();
        for (Object o : list) {
            e.add(effect(o));
        }
        return e;
    }

    //targeting node => diff kind of TargetedEffect?
    //default - just on the same target as main!
    public static Effect effect(Object effectNode, Object retainNode, Map args) {

        if (effectNode instanceof String) {

            EffectTemplate effectTemplate = EnumFinder.get(EffectTemplate.class, effectNode.toString());
            Effect effect = effectTemplate.supplier.get();
            effect.setData(new TypeData(args));

            if (retainNode != null) {
                Condition retain = ConditionYmlBuilder.condition(retainNode, args);
                effect = new ContinuousEffect(effect, retain);
            }
            return effect;
        } else {
            if (effectNode instanceof Map map) {
                Object trigger = map.get("trigger");

                if (trigger instanceof Map triggerMap) {
                    //TODO
                    // return new AddTriggerFx(event, condition, data);
                } else {
                    // custom nested effect
                    String name = map.get("class").toString();
                    EffectClass effectTemplate = EnumFinder.get(EffectClass.class, name);
                    Map nestedArgs = (Map) map.get("nested");
                    try {
                        Effect effect = effectTemplate.constructor.apply(nestedArgs);
                        //check retain? should be one level above then!..
                        return effect;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(effectTemplate.toString()+ " failed to construct with " +
                                MapUtils.represent(nestedArgs));
                    }
                }
            }
        }
        return null;
    }

    // private static Map getNested(Map map, String nested) {
    //     if (map.get(nested) instanceof Map nestedMap)
    //         return nestedMap
    // }

}
















