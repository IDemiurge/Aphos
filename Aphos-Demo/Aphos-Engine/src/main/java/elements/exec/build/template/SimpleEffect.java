package elements.exec.build.template;

import elements.exec.effect.DamageEffect;
import elements.exec.effect.Effect;
import elements.exec.effect.KillEffect;
import elements.exec.effect.ModifyStatEffect;
import elements.exec.effect.attack.DamageAttackEffect;
import elements.exec.effect.common.HealEffect;
import elements.exec.effect.counter.BashEffect;
import elements.exec.effect.counter.CounterEffect;

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
