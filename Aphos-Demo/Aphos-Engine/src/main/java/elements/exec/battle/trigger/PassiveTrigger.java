package elements.exec.battle.trigger;

import elements.exec.Executable;
import elements.exec.generic.condition.Condition;

/**
 * Created by Alexander on 8/26/2023
 * or should we just use a flag?
 */
public class PassiveTrigger extends ExecTrigger {
    // should know its source!
    public PassiveTrigger(Condition condition, Executable executable) {
        super(condition, executable);
    }
}
