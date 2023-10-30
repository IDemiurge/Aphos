package elements.exec;

import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.CustomTargetEffect;
import elements.exec.targeting.Targeting;

import java.util.List;
/*
So this would be the thing to EXTEND
Should it handle its own execution? Probably not!
 */
public class Ability implements Executable {
    Targeting targeting;
    Effect effect;
    List<CustomTargetEffect> targetedEffects;


    public Ability(Targeting targeting, Effect effect) {
        this.targeting = targeting;
        this.effect = effect;
    }
    public void setTargetedEffects(List<CustomTargetEffect> targetedEffects) {
        this.targetedEffects = targetedEffects;
    }

    @Override
    public List<CustomTargetEffect> getTargetedEffects() {
        return targetedEffects;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public Targeting getTargeting() {
        return targeting;
    }


}
