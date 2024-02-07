package elements.exec.build.template;

import elements.exec.build.ConditionBuilder;
import elements.exec.generic.condition.Condition;
import elements.exec.battle.condition.PositionCondition;
import elements.exec.battle.targeting.Targeting;
import elements.exec.battle.targeting.area.MeleeTargeter;
import elements.exec.battle.targeting.area.RangeTargeter;
import framework.data.datatypes.ArgMap;

import java.util.function.Supplier;

import static elements.exec.battle.targeting.Targeting.TargetingType.*;

/**
 * Created by Alexander on 10/28/2023
 */
public enum TargetingTemplate {
    TARGET(FIXED, () -> ref -> ref.getMatch() == ref.getPrevTarget()),
    SELF(FIXED, () -> ref -> ref.getMatch() == ref.getSource()),
    // MELEE,
    // RANGE,
    //RAY(ALL, ...
    RANDOM_LEFT_RIGHT(RANDOM, () -> ConditionBuilder.prebuild(true,
            ConditionTemplate.POS_CHECK, ArgMap.get("positions=left,right")).or().build()),

    MELEE(SELECTIVE, () -> MeleeTargeter.getMeleeCondition()),

    CLOSE_QUARTERS(SELECTIVE, () -> MeleeTargeter.getCloseQuartersCondition()),

    LONG_REACH(SELECTIVE, () -> MeleeTargeter.getLongReachCondition()),

    RANGED(SELECTIVE, () -> RangeTargeter.getRangeCondition()),

    CELL(SELECTIVE, () -> new PositionCondition());

    public final Targeting.TargetingType type;
    public final Supplier<Condition> supplier;

    TargetingTemplate(Targeting.TargetingType type, Supplier<Condition> supplier) {
        this.type = type;
        this.supplier = supplier;
    }
}
