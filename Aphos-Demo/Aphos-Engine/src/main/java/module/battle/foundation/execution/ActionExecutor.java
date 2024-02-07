package module.battle.foundation.execution;

import module.battle.handler.BattleHandler;
import module.battle.handler.BattleManager;
import elements.exec.EntityRef;
import elements.exec.Executable;
import elements.exec.generic.effect.Effect;
import elements.exec.generic.effect.wrap.CustomTargetEffect;
import elements.exec.battle.targeting.TargetGroup;
import module.battle.foundation.entity.field.FieldEntity;
import module.battle.foundation.entity.sub.UnitAction;
import module.battle.foundation.entity.sub.TriggerPassive;
import system.log.result.EffectResult;

import static module.battle.handler.BattleManager.combat;

/**
 * Created by Alexander on 8/21/2023
 */
public class ActionExecutor extends BattleHandler {

    public ActionExecutor(BattleManager manager) {
        super(manager);
        //this is a very simplistic approach, eh?
        // UserEventHandler.bind(UserEventType.activate_action, p ->
        //         activate(manager.getEntities().getEntityById(p.getInt("action"), UnitAction.class)));
    }
    //TODO MOVE!

    //     public boolean canActivate(UnitAction action) {
    //         //sometimes this would be part of target filtering?!
    //         //will we have actions that will cost, say, % of target's HP or so?
    //         if (action.getCost().canPay(action.ref()) != null)
    //             return false;
    //         return true;
    //     }
    //     public boolean canBoost(UnitAction action) {
    //         if (action.isBoostable()) {
    //             if (action.getCost(true).canPay(action.ref()) != null)
    //                 return false;
    //             return true;
    //         }
    //         return false;
    //     }

    public void passiveApplies(TriggerPassive unitPassive, Executable exec) {
        execute(unitPassive.ref(), exec);
    }

    public void activate(UnitAction action) {
        activate(action, false);
    }

    public void activate(UnitAction action, boolean boosted) {
        //can't this be triggered also? we should have distinct entry points then!
        EntityRef ref = action.ref();
        // Packets.create(Packet.ACTION, action.getAndCreatePacketKey(), ref);
        // Packets.get(action.key());
        execute(ref, action.getExecutable(boosted));

        action.getCost(boosted).pay(ref);
        manager.executableActivated(action, ref);
        // new ActionPacket()
    }

    //entry point for triggered?!
    public void executeTrigger(Executable executable, EntityRef ref) {
        execute(ref, executable);
    }

    private void execute(EntityRef ref, Executable executable) {

        //separate phase?!
        executable.getTargeting().select(ref);
        //how to combine this logic that must send AND receive with packet wrapping?
        //at targeting step, what do we want to have in that packet?
        //=> PRECALC!!!

        //check interrupts

        //here, even if there are some triggers and interrupts, we can precalculate everything because
        //there is no Input required!

        // HOWEVER - what about multi-executables with selective targeting?
        applyEffects(executable.getEffect(), ref);
        if (executable.getTargetedEffects() != null)
            for (CustomTargetEffect targetedEffect : executable.getTargetedEffects()) {
                // targetsSet.add(ref.getTarget()); // group too
                ref.setPrevTarget(ref.getTarget());

                //start new packet?
                targetedEffect.getTargeting().select(ref);
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
