package elements.exec.generic.effect.wrap;

import elements.exec.EntityRef;
import elements.exec.generic.effect.Effect;
import system.log.result.EffectResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 10/27/2023
 */
public class Effects extends Effect {

    List<Effect> effects = new ArrayList<>();

    public Effects() {
    }

    public void add(Effect e) {
        effects.add(e);
    }

    @Override
    protected void applyThis(EntityRef ref) {
        for (Effect effect : effects) {
            EffectResult result = effect.apply(ref);
            // this.result.nest(result);
        }
    }
}
