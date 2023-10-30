package elements.exec;

import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.CustomTargetEffect;
import elements.exec.targeting.Targeting;

import java.util.List;

public interface Executable {
    List<CustomTargetEffect> getTargetedEffects();

    Targeting getTargeting();

    Effect getEffect();

    default boolean isMultiExec() {
        return getTargetedEffects() != null ;
    }
}
