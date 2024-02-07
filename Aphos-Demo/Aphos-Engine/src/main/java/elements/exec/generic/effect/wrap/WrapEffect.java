package elements.exec.generic.effect.wrap;

import elements.exec.generic.effect.Effect;
import framework.data.TypeData;

/**
 * Created by Alexander on 10/29/2023
 */
public abstract class WrapEffect extends Effect{
    protected Effect effect;

    public WrapEffect(Effect effect) {
        this.effect = effect;
    }
    public Effect getEffect() {
        return effect;
    }

    @Override
    public String getArgs() {
        return effect.getArgs();
    }

    @Override
    public TypeData getData() {
        return effect.getData();
    }

    @Override
    public void setData(TypeData effectData) {
        effect.setData(effectData);
    }

    @Override
    public void setAdditionalFx(Effect additionalFx) {
        effect.setAdditionalFx(additionalFx);
    }
}
