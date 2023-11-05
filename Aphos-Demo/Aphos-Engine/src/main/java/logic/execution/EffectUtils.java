package logic.execution;

import elements.exec.effect.Effect;
import framework.data.yaml.EffectYmlBuilder;

/**
 * Created by Alexander on 10/26/2023
 */
public class EffectUtils {
    public static Effect build(String s) {
        return new EffectYmlBuilder("").effect(s);
    }
}
