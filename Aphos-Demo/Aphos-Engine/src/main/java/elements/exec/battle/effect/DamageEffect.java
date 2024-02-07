package elements.exec.battle.effect;

import elements.content.battle.types.CombatTypes;
import elements.exec.EntityRef;
import elements.exec.generic.effect.ValueEffect;
import framework.math.Formula;
import module.battle.features.damage.DamageCalc;
import module.battle.features.damage.DamageDealer;
import system.log.result.DamageCalcResult;

/**
 * Created by Alexander on 8/21/2023 what are the cases when we wanna deal damage w/o attack check? > Periodic? >
 * <p>
 * should this ADD damage instead? Otherwise, this matreshka is for naught! Maybe damage should not have a map in
 * fact...
 */
public class DamageEffect extends ValueEffect {

    private  CombatTypes.DamageType type;

    public DamageEffect() {
    }

    public DamageEffect(CombatTypes.DamageType type, Object formula) {
        this.type = type;
        this.valueFormula = formula;
        getData().set("damage_type", type.toString());
        getData().set("value", valueFormula);
    }

    @Override
    protected void init() {
        super.init();
        type = data.getEnum("damage_type", CombatTypes.DamageType.class);
    }
    protected void applyThis(EntityRef ref) {
        //where is it even used without Attack?
        int amount = Formula.eval(valueFormula, ref); //formula.getInt(ref);
        ref.setValueInt(amount);
        ref.setDamageType(type);
        DamageCalcResult result = new DamageCalc(ref).calculate(false);
        DamageDealer.deal(result);
        // boolean dead =!ref.get("target").isDead()
        // result.add("killed", true)
    }

}
