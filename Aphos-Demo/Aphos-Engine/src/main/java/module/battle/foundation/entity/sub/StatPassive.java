package module.battle.foundation.entity.sub;

import elements.exec.generic.condition.Condition;

/**
 * Created by Alexander on 11/6/2023 Conditional Variable Could be created automatically based on perks or such!
 * <p>
 * should we define it differently? accept just separately stuff from YML exec
 *
 * >> Many perks would parse into a conditional/var stat boost
 *
 * Extending via yaml should be easier - w/o modifying code as it were ; content packs and such
 *
 *
 */
public class StatPassive {
    Condition condition;

    public void apply() {
        // exec already defines targeting
        // wrap in conditional or check explicitly here?
    }
}
