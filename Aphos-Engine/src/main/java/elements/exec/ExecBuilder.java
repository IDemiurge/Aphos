package elements.exec;

import elements.content.enums.EnumFinder;
import elements.exec.condition.Condition;
import elements.exec.condition.ConditionBuilder;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.EffectTemplate;
import elements.exec.effect.framework.wrap.ContinuousEffect;
import elements.exec.targeting.Targeting;
import elements.exec.targeting.TargetingTemplates.ConditionTemplate;
import elements.exec.targeting.TargetingTemplates.TargetingTemplate;
import elements.exec.targeting.TargetingTemplates.TargetingType;
import elements.stats.ActionProp;
import framework.data.TypeData;
import framework.data.yaml.YamlBuilder;
import framework.entity.sub.UnitAction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import system.datatypes.LinkedStringMap;

import java.util.*;

/**
 * Created by Alexander on 8/22/2023
 */
public class ExecBuilder {
    public static final Map<String, Executable> execMap = new LinkedStringMap<>();

    public static Executable initExecutable(UnitAction unitAction) {
        String execKey = unitAction.get(ActionProp.Exec_data).toString();
        if (execKey.isEmpty())
            execKey = unitAction.getName();
        return getExecutable(execKey);
    }

    public static Executable getExecutable(String execKey) {
        return execMap.get(execKey);
    }

    public static Executable build(Object o) {
        List<Pair<Targeting, Effect>> fx = new ArrayList<>();
        if (o instanceof Collection){
            ((Collection) o).forEach(n ->
                    fx.add(buildPair(YamlBuilder.getMap(n, "exec"))));
        } else {
            fx.add(buildPair((Map) o));
        }
        return new ActionExecutable(fx);
    }

    //if maps to string - TEMPLATE; else - nested construct
    private static Pair<Targeting, Effect> buildPair(Map o) {
        //TODO  TRIGGER ???
        Map args = (Map) o.get("args");
        if (args == null) {
            args = new HashMap();
        }
        Targeting targeting = createTargeting(o.get("targeting"), args);
        Effect effect = createEffect(o.get("effect"), o.get("retain"), args);
        Pair<Targeting, Effect> pair = new ImmutablePair<>(targeting, effect);
        return pair;
    }

    private static Effect createEffect(Object effectNode,Object retainNode, Map args) {
        if (effectNode instanceof String) {
            EffectTemplate effectTemplate = EnumFinder.get(EffectTemplate.class, effectNode.toString());
            Effect effect= effectTemplate.supplier.get();
            effect.setData(new TypeData(args));
            if (retainNode!=null){
                Condition retain = null; //can be just duration? can be empty - i.e. Permanent?
                if (retainNode instanceof String condTempl){
                    retain = ConditionBuilder.build(condTempl, args);
                } else {
                    //nested map?
                }
                effect = new ContinuousEffect(effect, retain);
            }
            return effect;
            // return ExecPresetConstructor.createTemplateEffect(effect, args);
        } else {
            if (effectNode instanceof Map map){
                Object trigger = map.get("trigger");
                //TODO
                if (trigger instanceof Map triggerMap){
                    // return new AddTriggerFx(event, condition, data);
                }
            }
        }
        return null;
    }

    private static Targeting createTargeting(Object targetNode, Map args) {
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
                targeting.setType(EnumFinder.get(TargetingType.class, map.get("type")));
                targeting.setCondition(ConditionBuilder.build(tmplt, args));
            }
        }
        targeting.setData(new TypeData(args)); //blunt but will work? :)
        return targeting;
    }

    public static void addExec(String execKey, Executable exec) {
        execMap.put(execKey, exec);
    }
}
























