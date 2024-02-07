package elements.exec.battle.effect.counter;

import elements.exec.EntityRef;
import elements.exec.generic.effect.ValueEffect;
import elements.content.battle.entity.unit.Counter;
import module.battle.features.rules.counter.CounterRule;

/**
 * Created by Alexander on 8/27/2023
 */
public class CounterEffect extends ValueEffect {
    @Override
    protected void applyThis(EntityRef ref) {
        Counter counter = CounterRule.dictionary.get(data.get(getArgNames()[0]));
        //counters on field objs?

        ref.getTarget().addCounters(counter, data.getInt(getArgNames()[1]), ref);
        // effectResult.addAll(counterResult);
    }

    @Override
    public String getArgs() {
        return super.getArgs()+ "counter";
    }
}
