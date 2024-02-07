package framework.combat.entity.sub;

import framework.combat.entity.field.Unit;
import framework.combat.entity.sub.generic.UnitSubEntity;
import system.utils.EnumFinder;
import elements.exec.effect.Effect;
import elements.content.enums.entity.omen.OmenProp;
import elements.content.enums.entity.omen.OmenRollFx;

import java.util.Map;

import static elements.content.enums.types.EntityTypes.OmenType;

/**
 * Created by Alexander on 8/21/2023
 */
public class Omen extends UnitSubEntity {
    int rollIndex = 0;
    Effect effect;
    public Omen(Map<String, Object> valueMap, Unit unit) {
        super(valueMap, unit);
        effect =logic.execution.EffectUtils.build(getS(OmenProp.Effect));

    }

    public void applied() {
        if (getOmenType() == OmenType.Basic){
            // logic.execution.EffectUtils.apply(effect, unit);
        } else {
            //burn - ?
        }
    }


    public OmenRollFx roll() {
        OmenRollFx rollFx = getRollFx(rollIndex++);
        // if (getRoll(rollIndex) == null)
        //     setInactive();
        return rollFx;
    }

    private OmenRollFx getRollFx(int i) {
        String rollData = getS(switch (i) {
            case 0 -> OmenProp.Roll_1;
            case 1 -> OmenProp.Roll_2;
            case 2 -> OmenProp.Roll_3;
            default -> throw new RuntimeException("Roll index for omen must be within 0 and 2, not " + i);
        });

        return EnumFinder.get(OmenRollFx.class, rollData);
    }

    private OmenType getOmenType() {
        return getEnum(getS(OmenProp.OmenType), OmenType.class);
    }
}
