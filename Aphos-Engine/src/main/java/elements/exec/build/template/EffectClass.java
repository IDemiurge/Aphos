package elements.exec.build.template;

import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.IfElseEffect;

import java.util.Map;
import java.util.function.Function;

import static framework.data.yaml.ConditionYmlBuilder.condition;
import static framework.data.yaml.EffectYmlBuilder.effect;

public enum EffectClass {

    IfElse(),
    ;
    public  Function<Map, Effect> constructor;

    EffectClass() {
        constructor = map -> construct(map, this);
    }

    private Effect construct(Map nestMap, EffectClass effectClass) {
        return switch (effectClass){
            case IfElse -> new IfElseEffect(condition(nestMap.get("condition")),
                    effect(nestMap.get("if")), effect(nestMap.get("else")) );
        };
    }

}
