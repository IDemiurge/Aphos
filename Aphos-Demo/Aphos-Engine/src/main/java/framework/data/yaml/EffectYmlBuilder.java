package framework.data.yaml;

import elements.content.enums.EnumFinder;
import elements.exec.build.VarHolder;
import elements.exec.build.VarTracker;
import elements.exec.build.template.NestingEffect;
import elements.exec.build.template.SimpleEffect;
import elements.exec.condition.Condition;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.ContinuousEffect;
import elements.exec.effect.framework.wrap.CustomTargetEffect;
import elements.exec.effect.framework.wrap.Effects;
import elements.exec.targeting.Targeting;
import system.datatypes.ArgMap;
import utils.collection.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 10/27/2023
 * <p>
 * basic recursive design
 */
public class EffectYmlBuilder {
   private final VarTracker tracker;

    public EffectYmlBuilder(String key) {
        tracker= new VarTracker(key);
    }

    public void resolveVariables(Map<String, Object> map) {
        tracker.apply(map);
    }
    private   Effect listToEffect(List list) {
        Effects e = new Effects();
        for (Object o : list) {
            e.add(effect(o));
        }
        return e;
    }


    public   Effect effect(Object effectNode) {
        if (effectNode instanceof List list) {
            return listToEffect(list);
        } else if (effectNode instanceof Map map) {
            return upperMapToEffect(map);
        }
        throw new RuntimeException("Invalid effect yaml structure!");
    }

    private   Effect upperMapToEffect(Map o) {
        Object fxNode = o.get("effect");
        Object retain = o.get("retain");
        Object targeting = o.get("targeting"); //always on top level

        Map nested = (Map) o.get("nested");
        if (fxNode instanceof Map submap) {
            o = submap;
            fxNode = o.get("effect");
            nested = (Map) o.get("nested");
        }
        //TODO
        //Allow this only on the top level?
        Map args = (Map) o.get("args");
        if (args == null) {
            args = new ArgMap();
        } else {
            args = new ArgMap(args);
        }
        Effect effect = singleToEffect(fxNode.toString(), nested);
        effect = checkWrapInRetain(effect, retain, args);
        effect = checkWrapInTargeting(effect, targeting, args);

        if (!args.isEmpty()){
            effect.setData(args);
        } else {
            tracker.varHolderAdded(effect);
        }

        return effect;
    }

    private   Effect checkWrapInRetain(Effect effect, Object retainNode, Map args) {
        if (retainNode != null) {
            Condition retain = ConditionYmlBuilder.condition(retainNode, args);
            //If args not provided here, try to resolve them via Vars
            for (String argName : retain.getArgNames()) {
                if (!args.containsKey(argName))
                {
                    tracker.varHolderAdded((VarHolder) retain);
                    break;
                }
            }
            effect = new ContinuousEffect(effect, retain);
        }
        return effect;
    }

    private   Effect checkWrapInTargeting(Effect effect, Object targetingNode, Map args) {
        if (targetingNode != null) {
            Targeting targeting = TargetingYmlBuilder.targeting(targetingNode, args);
            return new CustomTargetEffect(targeting, effect);
        }
        return effect;
    }

    private   Effect singleToEffect(String effectName, Map nested) {
        if (nested != null) {
            NestingEffect fxClass = EnumFinder.get(NestingEffect.class, effectName);
            if (fxClass != null) {
                try {
                    //pass reference to itself for tracing?

                    nested.put("builder", this);
                    Effect effect = fxClass.constructor.apply(nested); //MUST CALL UPPER MAP TO RECURSE
                    return effect;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(fxClass + " effect failed to construct with nested args: \n" +
                            MapUtils.represent(nested));
                }
            }
        }
        SimpleEffect simpleEffect = EnumFinder.get(SimpleEffect.class, effectName);
        if (simpleEffect == null) {
            //use single and wrap into Effects ? But no - it should be more like... apply additional fx!
            //yeah this will be used very often indeed.
            throw new RuntimeException(effectName + " effectTemplate does not exist");
        }
        Effect effect = simpleEffect.supplier.get();
        if (nested != null) {
            Effect additionalFx = effect(nested);
            effect.setAdditionalFx(additionalFx);
        }
        return effect;
    }
}
















