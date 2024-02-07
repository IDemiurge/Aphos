package elements.exec.build.template;

import elements.exec.generic.effect.Effect;
import elements.exec.generic.effect.wrap.AddTriggerFx;
import elements.exec.generic.effect.wrap.IfElseEffect;
import elements.exec.generic.effect.wrap.PeriodicEffect;
import framework.data.yaml.EffectYmlBuilder;

import java.util.Map;
import java.util.function.Function;

import static framework.data.yaml.ConditionYmlBuilder.condition;
import static module.battle.foundation.execution.event.combat.CombatEvent.event;

public enum NestingEffect {

    IfElse(),
    Trigger,
    EndOfRound,
    ;
    public Function<Map, Effect> constructor;

    NestingEffect() {
        constructor = map -> construct(map, this);
    }

    private Effect construct(Map nestMap, NestingEffect nestingEffect) {
        EffectYmlBuilder builder = (EffectYmlBuilder) nestMap.get("builder");
        return switch (nestingEffect) {
            case IfElse -> new IfElseEffect(condition(nestMap.get("condition")),
                    builder.effect(nestMap.get("if")), builder.effect(nestMap.get("else")));
            case Trigger -> new AddTriggerFx(event(nestMap.get("event")),
                    condition(nestMap.get("condition")), builder.effect(nestMap.get("effect")));
            //TODO
            case EndOfRound -> new PeriodicEffect(event("end_of_round"), null, builder.effect(nestMap.get("effect")));
        };
    }


}
