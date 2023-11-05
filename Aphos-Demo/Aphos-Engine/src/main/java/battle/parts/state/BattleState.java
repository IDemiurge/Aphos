package battle.parts.state;

import battle.handler.BattleHandler;
import battle.handler.BattleManager;
import elements.exec.EntityRef;
import elements.exec.condition.Condition;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.TargetedEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 8/22/2023
 */
public class BattleState extends BattleHandler {
    List<TargetedEffect> effects = new ArrayList<>();
    // Map<EntityRef, Effect> effects = new LinkedHashMap<>();
    int round;
    // Seals seals;

    public BattleState(BattleManager battleManager) {
        super(battleManager);
    }

    @Override
    public void newRound() {
        round++;
    }

    @Override
    public void reset() {
        //check remove
        effects.removeIf(ef-> ef.checkRemove());
        for (TargetedEffect ef : effects) {
            ef.apply();
        }
    }

    @Override
    public void afterReset() {
        forEach(u-> u.getCounters().apply());
    }

    public int getRound() {
        return round;
    }

    public void addEffect(EntityRef ref, Effect effect, Condition retainCondition) {
        effects.add(new TargetedEffect(ref, effect).setRetainCondition(retainCondition));
    }
}
