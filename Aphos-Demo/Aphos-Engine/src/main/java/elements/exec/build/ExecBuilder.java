package elements.exec.build;

import elements.exec.Ability;
import elements.exec.Executable;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.CustomTargetEffect;
import elements.content.enums.stats.action.ActionProp;
import framework.data.yaml.EffectYmlBuilder;
import framework.combat.entity.sub.generic.ExecEntity;
import framework.datatypes.LinkedStringMap;

import java.util.Map;


/**
 * Created by Alexander on 8/22/2023
 */
    /*
    REVAMP GOALS:
    1) Lazy - init also what is needed
    2) Flexible - insert Extensions
     */
public class ExecBuilder {
    public static final Map<String, Executable> execMap = new LinkedStringMap<>();
    public static final Map<String, Object> execDataMap = new LinkedStringMap<>();

    public static void setExecData(String typeName, Object o) {
        execDataMap.put(typeName, o);
    }
    //so could this be useful outside of Actions? Maybe not as per GAME needs, but TEST?
    //well, now most of these EXECS will not be self-sufficient without VARS, eh?!
    public static Executable getExecutable(String execKey) {
        if (execMap.containsKey(execKey))
        return execMap.get(execKey);
        //so testing execs outside pas/act is hard now, eh?
        //TODO
        return null;
    }

    public static void addExec(String execKey, Executable exec) {
        execMap.put(execKey, exec);
    }

    public static Executable initExecutable(ExecEntity unitAction, boolean boost) {
        String execKey = unitAction.get(boost? ActionProp.Exec_data_boost : ActionProp.Exec_data).toString();
        // if (execKey.isEmpty())
        //     execKey = unitAction.getName();

        EffectYmlBuilder builder = new EffectYmlBuilder(unitAction.getName());
        Effect effect = builder.effect(execDataMap.get(execKey));
        builder.resolveVariables(unitAction.getVarMap());

        //COPY FOR OTHERS!
        Executable exec = fromEffect(effect);
        // exec = AbilExtensions.extend(exec, unitAction);
        return exec;
    }



    public static Executable fromEffect(Effect effect) {
        if (effect instanceof CustomTargetEffect c) {
            return new Ability(c.getTargeting(), c.getEffect());
        }
         throw new RuntimeException("Cannot create executable from non-targeted effect: "+ effect);
    }

    public static Executable build(Object o) {
        return fromEffect(new EffectYmlBuilder("Custom").effect(o));
    }

}

























