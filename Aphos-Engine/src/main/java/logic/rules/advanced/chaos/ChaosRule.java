package logic.rules.advanced.chaos;

import elements.content.enums.types.CombatTypes;
import elements.exec.EntityRef;
import elements.stats.UnitParam;
import framework.entity.field.Unit;
import logic.calculation.damage.DamageCalc;
import logic.calculation.damage.DamageDealer;
import system.log.result.DamageCalcResult;

/**
 * Created by Alexander on 10/25/2023
 *
 */
public class ChaosRule {
/*
 * Reduced by 1 per round
 * Hits with soul dmg each round
 * Increases Initiative
 * May be used in spell formulas
 */
    public void roundEnds(Unit unit){
        int value = unit.getInt(UnitParam.Chaos);

        EntityRef ref = new EntityRef(unit);
        //via effect?
        ref.setValueInt(value);
        ref.setDamageType(CombatTypes.DamageType.Nether);
        DamageCalcResult calc = new DamageCalc(ref).calculate(false);
        DamageDealer.deal(calc);

        int reduction= 1;
        unit.modifyValue("Chaos", -reduction);
    }
}
