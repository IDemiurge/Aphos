package elements.exec.effect.framework.wrap;

import elements.exec.EntityRef;
import elements.exec.effect.Effect;
import elements.exec.targeting.Targeting;

/**
 * Created by Alexander on 10/28/2023
 */
public class CustomTargetEffect extends WrapEffect {
    private Targeting targeting;

    public CustomTargetEffect(Targeting targeting, Effect effect) {
        super(effect);
        this.targeting = targeting;
    }

    public Targeting getTargeting() {
        return targeting;
    }


    @Override
    protected void applyThis(EntityRef ref) {
        if (!targeting.select(ref))
            return; //no available targets
        effect.apply(ref);

    }
}
