package framework.math;

import elements.exec.EntityRef;
import framework.combat.entity.Entity;
import framework.combat.entity.field.Unit;
import framework.datatypes.LinkedStringMap;
import system.math.MathException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 11/20/2023
 */
public class RefCalc {
    private static Map<Entity, RefCalc> refMap = new HashMap<>();
    private Map<String, Double> argMap = new LinkedStringMap<>();

    public RefCalc(Entity entity) {
        refMap.put(entity, this);
    }

    public void addArgument(String s, double val){
        argMap.put(s, val);
        //can we know which vals will be needed? It's not so hard to add on the fly ofc
    }
    public double eval_(String s) {
        //can there be more?
        Double result = argMap.get(s); //what if it's empty? Then try function?
        if (result == null){
            throw new MathException(s+ " is not in the arg map!");
        }
        return result;
    }
    /*
    case: +$src_prop=value$*1
    case: if (src_prop, 1, 0)
    >> src_prop as 1/0 by default? Can just add 1's for existing True's
     */


    //perhaps we should just have a separate calculator class for FUNCS - this works fine for val_args
    public static double eval(String s, EntityRef ref) {
        //src_str, func(...),
        String key = getKey(s); //multi_key?
        Entity entity = ref.get(key);
        if (entity!=null) {
            return getRefCalc(entity).eval_(format(s));
        }
        //TODO ?
        return 0;
    }

    private static String getKey(String s) {
        return s.substring(0, s.indexOf("_"));
    }

    private static String format(String s) {
        return s.substring(s.indexOf("_"));
    }

    private static RefCalc getRefCalc(Entity source) {
        return refMap.get(source);
    }

}
