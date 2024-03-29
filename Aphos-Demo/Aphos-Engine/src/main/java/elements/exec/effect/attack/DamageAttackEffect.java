package elements.exec.effect.attack;

import elements.content.enums.types.CombatTypes;
import elements.exec.EntityRef;
import logic.calculation.damage.DamageCalc;
import logic.calculation.damage.DamageDealer;
import logic.execution.event.combat.CombatEventType;
import system.log.result.DamageCalcResult;
import system.log.result.DamageResult;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/25/2023
 */
public class DamageAttackEffect extends AttackEffect {
    @Override
    public void process(CombatTypes.RollGrade grade, EntityRef ref) {

        ref.setDamageType(data.getEnum( "damage_type", CombatTypes.DamageType.class));
        DamageCalc damageCalc = new DamageCalc(ref);
        damageCalc.setGrade(grade);
        damageCalc.setValues(data.getInt("value_min"), data.getInt("value_base"), data.getInt("value_max"));
        DamageCalcResult result = damageCalc.calculate(false);
        if (result.isMiss()){
            combat().event(CombatEventType.Miss, ref);
            // handleMiss();
        }
        DamageResult finalResult = DamageDealer.deal(result);
        effectResult.addAll(finalResult);
        combat() .stats().add(finalResult);
        // effectResult.addAll(finalResult.getData());
    }


    @Override
    public String getArgs() {
        return "damage_type|" + super.getArgs();
    }

}
