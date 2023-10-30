package logic.execution;

import combat.BattleHandler;
import combat.sub.BattleManager;
import elements.exec.EntityRef;
import elements.exec.Executable;
import elements.exec.effect.Effect;
import elements.exec.effect.framework.wrap.CustomTargetEffect;
import elements.exec.targeting.TargetGroup;
import framework.client.user.UserEventHandler;
import framework.entity.field.FieldEntity;
import framework.entity.sub.UnitAction;
import logic.execution.event.user.UserEventType;
import system.log.result.EffectResult;

import static combat.sub.BattleManager.combat;

/**
 * Created by Alexander on 8/21/2023
 */
public class ActionExecutor extends BattleHandler {

    public ActionExecutor(BattleManager manager) {
        super(manager);
        UserEventHandler.bind(UserEventType.activate_action, p ->
                activate(manager.getEntities().getEntityById(p.getInt("action"), UnitAction.class)));
    }


    public boolean canActivate(UnitAction action) {
        //sometimes this would be part of target filtering?!
        //will we have actions that will cost, say, % of target's HP or so?
        if (action.getCost().canPay(action.ref()) != null)
            return false;
        return true;
    }

    public boolean canBoost(UnitAction action) {
        if (action.isBoostable()) {
            if (action.getCost(true).canPay(action.ref()) != null)
                return false;
            return true;
        }
        return false;
    }

    public void activate(UnitAction action) {
        activate(action, false);
    }

    public void activate(UnitAction action, boolean boosted) {
        EntityRef ref = action.ref();
        execute(ref, action.getExecutable(boosted));

        action.getCost(boosted).pay(ref);
        manager.executableActivated(action, ref);

        //triggers visual effect and waits for input
        //pack all the cost/boost/targeting/fx into 'executable'?

        // action.getCost().pay();
        //set each action to disabled after a toBase() if can't pay; are there any fringe cases?

    }

    public void executeTrigger(Executable executable, EntityRef ref) {
        execute(ref, executable);
    }

    private void execute(EntityRef ref, Executable executable) {
        executable.getTargeting().select(ref);
        applyEffects(executable.getEffect(), ref);
        if (executable.getTargetedEffects() != null)
            for (CustomTargetEffect targetedEffect : executable.getTargetedEffects()) {
                // targetsSet.add(ref.getTarget()); // group too
                ref.setPrevTarget(ref.getTarget());
                targetedEffect.apply(ref);
            }
    }

    private boolean applyEffects(Effect effect, EntityRef ref) {
        // EntityRef ref = new EntityRef(action.getUnit());
        TargetGroup targets = ref.getGroup();
        if (targets == null) {
            effect.apply(ref);
            //some effects don't need us to inflate the group?
        } else
            // apply()
            for (FieldEntity target : targets.getTargets()) {
                EntityRef REF = ref.copy();
                REF.setTarget(target);
                EffectResult result = effect.apply(REF); //this obj should absorb other results ?

                combat().stats().add(result);
                combat().getEventHandler().afterEvents(result);
                // boolean result = action.getExecutable().execute(REF);
                // if (REF.isValueBool()) {
                //     break; //interrupted
                // }
            }

        //state.toBase();
        return true; //meaning? premature turn end?
    }

}
