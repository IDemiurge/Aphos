package elements.exec.targeting;

// import com.google.common.collect.ImmutableSet;


import elements.exec.EntityRef;
import elements.exec.condition.Condition;
import framework.data.TypeData;
import framework.combat.entity.field.FieldEntity;
import system.KotlinUtils;
import utils.collection.ListUtils;

import java.util.List;

import static battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 6/11/2023 Special rules will be for Flank-to-Flank etc * or should these all be aggregated
 * behavior methods? * Targeting should actually be more like... data object, not an executor! * So all we need is: *
 * Template * Conditions * Args (e.g. number of targets?) * * Usage cases * >> Depending on target, action has diff
 * effects (WH's pierce, Soldier's spear..) * >>
 * <p>
 * <p>
 * Let's really examine our targeting reqs: 1) Selective for action is DIFF 2)
 */
public class Targeting {
    protected TypeData data;
    protected Condition condition;

    public TargetingType type;
    // boolean all_in_range; //if true, action affects all units that match the Targeting condition
    // //E.G. - All Melee, All Range (1), All Enemy, All <...>
    // boolean friendly_fire;
    // boolean left_right; //for ray only?
    // maybe these can be part of TARGETING DATA? Parsed same way as for entity?

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }

    public boolean canSelect(EntityRef ref) {
        List<FieldEntity> fieldEntities = combat().getEntities().targetFilter(ref, this);
        if (fieldEntities.isEmpty())
            return false;
        return true;
    }
    public boolean select(EntityRef ref) {
        //if there is only Self - will auto-target (if some option is checked)
        //modify conditions based on data
        /*
        FIXED - single always?
        ALL
        SELECTIVE (++ multi hits)
         */
        // List<FieldEntity> fieldEntities = combat().getEntities().getFieldEntities();//should we always start with that list? then remove omens/obst/..
        // // filter( condition.check())
        // //how to easily assemble filters? Should have some MNGR for that
        // //here we determine if there is Manual Selection
        // fieldEntities.removeIf(e -> !condition.check(ref.setMatch(e)));
        // single = true;

        List<FieldEntity> fieldEntities = combat().getEntities().targetFilter(ref, this);
        if (fieldEntities.isEmpty())
            return false;
        if (type == TargetingType.RANDOM){
            // if ( data.has(TargetingParams.Number_Of_Targets))
            if (!data.has("Number_Of_Targets"))
                ref.setTarget(ListUtils.getRandom(fieldEntities));
            else {
                Integer numberOfTargets = data.getInt("Number_Of_Targets");
                List<FieldEntity> randomGroup = (ListUtils.getRandomElements(fieldEntities, numberOfTargets));
                ref.setGroup(new TargetGroup(randomGroup));
            }
        } else
        if (type == TargetingType.FIXED){
            if (fieldEntities.size() == 1) {
                ref.setTarget(fieldEntities.get(0));
            } else {
                //same as ALL!?
                //a bit weird, or? Maybe use this for something else
                ref.setGroup(new TargetGroup(fieldEntities));
            }
        } else
        if (type == TargetingType.SELECTIVE){
            // WaitMaster.receiveInput(WaitMaster.WAIT_OPERATIONS.SELECTION, fieldEntities);
            //
            // Object o = WaitMaster.waitForInput(WaitMaster.WAIT_OPERATIONS.SELECT_BF_OBJ);

            Object o =  KotlinUtils.Companion.receiveInput();
            if (o instanceof FieldEntity entity){
                ref.setTarget(entity);
            } else
            if (o instanceof List list){
                ref.setGroup(new TargetGroup(list));
            }
        } else
        if (type == TargetingType.ALL){
            ref.setGroup(new TargetGroup(fieldEntities));
        }

        return true;
    }


    public void setData(TypeData data) {
        this.data = data;
    }

    public TypeData getData() {
        return data;
    }

    public void setType(TargetingType type) {
        this.type = type;
    }

    public TargetingType getType() {
        return type;
    }


    public enum TargetingType {
        FIXED, SELECTIVE, ALL, RANDOM,
    }
    public enum TargetingKeyword {
        Close_Quarters,
        Melee,
        Long_Range,
        Range,
        Ray,
        Ray_2x,
        Any,
    }
}
















