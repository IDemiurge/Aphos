package module.battle.handler;

import module.battle.handler.meta.BattleStatistics;
import module.battle.foundation.battlefield.BattleField;
import module.battle.handler.state.BattleEntities;
import module.battle.handler.state.BattleState;
import system.server.internal.InputHandler;
import system.server.internal.InputKey;
import elements.exec.EntityRef;
import module.battle.foundation.entity.Entity;
import module.battle.foundation.entity.field.Unit;
import module.battle.foundation.execution.ActionExecutor;
import module.battle.foundation.execution.event.combat.CombatEventHandler;
import module.battle.foundation.execution.event.combat.CombatEventType;
import async.Async;
import utils.threading.WaitMaster;

import java.util.function.Consumer;

/**
 * Created by Alexander on 8/21/2023
 */
public abstract class BattleHandler {
    protected BattleManager manager;

    public BattleHandler(BattleManager battleManager) {
        this.manager = battleManager;
    }

    public void battleStarts(){
    }
    public void battleEnds(){
    }
    public void reset(){
    }
    public void afterReset(){
    }
    public void newRound(){
    }

    public void roundEnds() {
    }

    protected void await(InputKey key) {
         Async.await(key, input -> getInputHandler(key).handle(input));
         WaitMaster.WAIT(100);
         //open receiver for data with this key?
    }

    private InputHandler getInputHandler(InputKey key) {
        return new InputHandler();
    }

    public void resetAll() {
        manager.resetAll();
    }

    public void forEach(Consumer<Unit> func) {
        getEntities().getUnits().forEach(unit -> func.accept(unit));
    }

    public BattleEntities getData() {
        return manager.getEntities();
    }

    public BattleField getField() {
        return manager.getField();
    }

    public BattleState getBattleState() {
        return manager.getBattleState();
    }
    // manager delegates getters

    public BattleEntities getEntities() {
        return manager.getEntities();
    }

    public CombatEventHandler getEventHandler() {
        return manager.getEventHandler();
    }

    public ActionExecutor getExecutor() {
        return manager.getExecutor();
    }

    public void event(CombatEventType type, EntityRef ref, Object... args) {
        manager.event(type, ref, args);
    }

    public Unit getUnitById(Integer id) {
        return manager.getUnitById(id);
    }

    public <T extends Entity> T getById(Integer id, Class<T> entityClass) {
        return manager.getById(id, entityClass);
    }

    public BattleStatistics stats() {
        return manager.stats();
    }

}
