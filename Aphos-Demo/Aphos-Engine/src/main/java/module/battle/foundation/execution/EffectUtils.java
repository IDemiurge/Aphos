package module.battle.foundation.execution;

import elements.exec.generic.effect.Effect;
import framework.data.yaml.EffectYmlBuilder;

import java.util.List;

/**
 * Created by Alexander on 10/26/2023
 */
public class EffectUtils {
    public static Effect build(String s) {
        return new EffectYmlBuilder("").effect(s);
    }

    public static <T extends Effect> List<T> getByClass(Effect effect, Class<T> clazz) {
        //recursive
        //if instanceof wrap etc etc
        return null;
    }
}
