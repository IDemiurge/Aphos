package elements.exec.battle.effect.common;

import elements.exec.EntityRef;
import elements.exec.generic.effect.Effect;
import elements.exec.generic.effect.ValueEffect;
import elements.content.battle.stats.unit.UnitParam;

/**
 * Created by Alexander on 10/28/2023
 */
public class HealEffect extends ValueEffect {

    private UnitParam param;
    private Effect overflowEffect;

    public HealEffect() {

    }
    public HealEffect(UnitParam param) {
        this(param,  null);
    }
    public HealEffect(UnitParam param, Effect overflowEffect) {
        this.param = param;
        this.overflowEffect = overflowEffect;
    }

    // public void init(){
    //     // getData().get
    // }
    @Override
    protected void applyThis(EntityRef ref) {
        // int overflow = HealRule.heal(ref, param, valueFormula);
        // if (overflow>0 && overflowEffect!=null){
        //     overflowEffect.setValue("value", overflow);
        // }
        // ref.getTarget().modifyValue();
    }
}
