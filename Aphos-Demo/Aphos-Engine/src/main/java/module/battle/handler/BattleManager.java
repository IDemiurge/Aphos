package module.battle.handler;

import module.battle.Battle;
import module.battle.parts.battlefield.BattleField;
import module.battle.handler.init.BattleSetup;
import module.battle.handler.init.PartyInitializer;
import module.battle.parts.state.BattleEntities;
import module.battle.parts.state.BattleState;
import module.battle.handler.meta.BattleStatistics;
import module.battle.turns.TurnHandler;
import elements.exec.EntityRef;
import framework.combat.entity.Entity;
import framework.combat.entity.field.Unit;
import framework.combat.entity.sub.UnitAction;
import logic.execution.ActionExecutor;
import logic.execution.event.combat.CombatEvent;
import logic.execution.event.combat.CombatEventHandler;
import logic.execution.event.combat.CombatEventType;
import system.log.SysLog;
import system.log.result.EventResult;
import utils.collection.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 8/22/2023
 */
public class BattleManager {
    private final BattleStatistics statistics= new BattleStatistics();
    private final BattleSetup battleSetup;

    private final List<BattleHandler> handlers = new ArrayList<>();
    private final BattleEntities battleEntities;
    private final BattleField battleField;
    private final BattleState battleState;
    private final CombatEventHandler eventHandler;
    private final ActionExecutor executor;
    private final TurnHandler turnHandler;

    public BattleManager(BattleSetup battleSetup) {
        this.battleSetup = battleSetup;
        //order of handlers matters!
        handlers.add(battleEntities = new BattleEntities(this));
        handlers.add(battleField = new BattleField(this));
        handlers.add(battleState = new BattleState(this));
        handlers.add(eventHandler = new CombatEventHandler(this));
        handlers.add(executor = new ActionExecutor(this));
        handlers.add(turnHandler = new TurnHandler(this));
        // what should we do with ALL handlers?
        // on game end? On event?
    }

    //////////////////region MAIN METHODS
    public void executableActivated(UnitAction action, EntityRef ref) {
        //is this when we send anim data to client? Of results? Interesting
        action.executed();
        resetAll();
        afterResetAll();
    }
    //endregion
    //////////////////region UNIVERSAL METHODS
    public void resetAll() {
        handlers.forEach(handler -> handler.reset());
    }
    public void afterResetAll() {
        handlers.forEach(handler -> handler.afterReset());
    }
    public void roundEnds() {
        handlers.forEach(handler -> handler.roundEnds());

        // loop.
    }
    public void newRound() {
        // SysLog.increaseIndent();
        SysLog.printLine(SysLog.LogChannel.Gameflow, "New Round: ",  battleState.getRound());
        handlers.forEach(handler -> handler.newRound());
    }
    public void battleStarts() {
        PartyInitializer battleInit = new PartyInitializer(battleField, battleSetup);
        battleInit.initParties();
        handlers.forEach(handler -> handler.battleStarts());
    }
    public void battleEnds() {
        handlers.forEach(handler -> handler.battleEnds());
    }
    //endregion

    //////////////////region GETTERS
    public BattleEntities getEntities() {
        return battleEntities;
    }

    public BattleField getField() {
        return battleField;
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public CombatEventHandler getEventHandler() {
        return eventHandler;
    }

    public ActionExecutor getExecutor() {
        return executor;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    //endregion

    public void event(CombatEventType type, EntityRef ref, Object... args) {
        Map<Class, Object> map=  MapUtils.toClassMap(args);
        CombatEvent event= new CombatEvent(type, ref,  map);
        EventResult result = eventHandler.handle(event);
        //wazzup here?
    }
    //////////////////region SHORTCUTS

    public Unit getUnitById(Integer id) {
        return getById(id, Unit.class);
    }

    public <T extends Entity> T getById(Integer id, Class<T> entityClass) {
        return getEntities().getEntityById(id, entityClass);
    }
    //endregion


    public static BattleManager combat(){
        return Battle.current.getManager();
    }

    public BattleStatistics stats() {
        return statistics;
    }

}
