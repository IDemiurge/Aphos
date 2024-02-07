package elements.exec;

import elements.exec.generic.effect.Effect;
import elements.exec.generic.effect.wrap.CustomTargetEffect;
import elements.exec.battle.targeting.Targeting;

import java.util.List;

public interface Executable {
    List<CustomTargetEffect> getTargetedEffects();

    Targeting getTargeting();

    Effect getEffect();

    default boolean isMultiExec() {
        return getTargetedEffects() != null ;
    }
}
