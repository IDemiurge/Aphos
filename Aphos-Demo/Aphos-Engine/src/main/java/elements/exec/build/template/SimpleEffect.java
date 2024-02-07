package elements.exec.build.template;

import elements.exec.battle.effect.DamageEffect;
import elements.exec.generic.effect.Effect;
import elements.exec.battle.effect.KillEffect;
import elements.exec.battle.effect.ModifyStatEffect;
import elements.exec.battle.effect.attack.DamageAttackEffect;
import elements.exec.battle.effect.common.HealEffect;
import elements.exec.battle.effect.counter.BashEffect;
import elements.exec.battle.effect.counter.CounterEffect;

import java.util.function.Supplier;

/**
 * Created by Alexander on 8/24/2023
 */
public enum SimpleEffect {
    MODIFY(() -> new ModifyStatEffect()),
    ATTACK(()-> new DamageAttackEffect()),
    BASH(()-> new BashEffect()),
    KILL(()-> new KillEffect()),
    DAMAGE(()-> new DamageEffect()),
    COUNTERS(()-> new CounterEffect()),
    HEAL(()-> new HealEffect()),
    EFFECT(()-> null),

    ;
    public final Supplier<Effect> supplier;

    SimpleEffect(Supplier<Effect> supplier) {
        this.supplier = supplier;
    }
}
