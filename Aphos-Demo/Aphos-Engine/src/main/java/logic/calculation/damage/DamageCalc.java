package logic.calculation.damage;

import elements.exec.EntityRef;
import elements.content.enums.stats.action.ActionParam;
import elements.content.enums.stats.unit.UnitParam;
import framework.combat.entity.field.FieldEntity;
import framework.combat.entity.field.Unit;
import framework.combat.entity.sub.UnitAction;
import system.log.result.DamageCalcResult;
import utils.old.RandomWizard;

import java.util.ArrayList;
import java.util.List;

import static elements.content.enums.types.CombatTypes.RollGrade;

/**
 * Created by Alexander on 8/20/2023
 * <p>
 * For entire action? Aye... >>> What about cases where action is NULL?
 * <p>
 * calculate HP/ARmor/.. to be deducted Further trigger-reductions may apply on deal() step
 * <p>
 * Should work for soul too!
 */
public class DamageCalc {
    private EntityRef ref;
    private boolean precalc;
    private DamageCalcResult result;
    private RollGrade grade;
    private Integer[] values;
    // CalcResult  store result and calc process here

    public DamageCalc(EntityRef ref) {
        this.ref = ref;
    }

    // for precalc? NO WARDS e.g.!
    public DamageCalcResult calculate(boolean precalc) {
        this.precalc = precalc; //what side effects could there be?
        result = new DamageCalcResult(ref);

        MultiDamage damage = getDamage(grade);
        result.setDamageToDeal(damage);

        return result;
    }

    /*
    what happens when we deal dmg of 2 types in a single attack? each instance is blocked separately?
    but for an action...
     */
    public Damage applyReductions(Damage damage) {
        //does this apply to each type? Might be logical!

        int dr = ref.get("attacked").getInt(UnitParam.DR);
        int drSoul = ref.get("attacked").getInt(UnitParam.DR_Soul);

        boolean hp = damage.getType().isHp();
        Integer integer = damage.getAmount();
        int reduced = Math.max(0, integer + (hp ? -dr : -drSoul));
        // int reducedBy = integer - reduced;
        // result.add(hp ? TOTAL_REDUCED : TOTAL_REDUCED_SOUL, reducedBy);
        damage.setAmount(reduced);
        return damage;
    }

    public MultiDamage getDamage(RollGrade grade) {
        return getDamage(grade, 1);
    }

    public MultiDamage getDamage(RollGrade grade, int attacks) {
        UnitAction attack = (UnitAction) ref.get("action");
        Unit attacker = (Unit) ref.get("attacker");
        FieldEntity attacked = (FieldEntity) ref.get("attacked");

        if (grade == RollGrade.Miss) {
            result.setMiss(true);
            return new MultiDamage();
        }

        List<Damage> list = new ArrayList<>();
        for (int i = 0; i < attacks; i++) {
            //add pair
            int amount = 0;
            if (grade == null || attack == null) {
                amount = ref.getValueInt();
            } else {
                int baseAmount = getBaseAmount(grade, attack);
                float mod = 1;
                if (grade == RollGrade.Ultimate) {
                    getUltimateModifier(attack, attacker, attacked);
                }
                amount = Math.round(baseAmount * mod);
            }

            Damage damage = new Damage(ref.getDamageType(), amount);
            applyReductions(damage);
            if (damage.getAmount() > 0) {
                if (isWardBlock(damage, ref))
                    continue;
            }

            list.add(damage);
            Object args = null;
            // EventResult fire = combat().getEventHandler().fire(CombatEventType.Damage_Being_Dealt, ref, map);
            //may have modified the damage map!

        }
        return new MultiDamage(list);
    }

    private boolean isWardBlock(Damage damage, EntityRef ref) {
        if (logic.rules.combat.ImmuneRule.checkImmune(ref.getTarget(), damage.getType(), ref.getSource())) {
            return true;
        }
        if (logic.rules.combat.WardRule.checkWard(ref.getTarget(), damage.getType(), ref.getSource())) {
            return true;
        }
        return false;
    }

    private int getBaseAmount(RollGrade grade, UnitAction attack) {

        if (values == null) {
            ActionParam
                    minValue = ActionParam.Value_Min,
                    value = ActionParam.Value_Base,
                    maxValue = ActionParam.Value_Max;
            values = new Integer[]{
                    attack.getInt(minValue),
                    attack.getInt(value),
                    attack.getInt(maxValue),
            };
        }

        switch (grade) {
            case Ultimate:
            case Max:
                return values[2];
            case Rnd_Avrg_Max:
                return random(values[1], values[2]);
            case Avrg:
                return values[1];
            case Rnd_Min_Avrg:
                return random(values[0], values[1]);
            case Min:
                return values[0];
        }
        return 0;
    }

    private int random(int anInt, int anInt1) {
        return RandomWizard.getRandomIntBetween(anInt, anInt1);
    }


    private float getUltimateModifier(UnitAction attack, Unit attacker, FieldEntity attacked) {
        return 2;
    }

    public void setGrade(RollGrade grade) {
        this.grade = grade;
    }

    public RollGrade getGrade() {
        return grade;
    }

    public void setValues(Integer... values) {
        this.values = values;
    }

}
