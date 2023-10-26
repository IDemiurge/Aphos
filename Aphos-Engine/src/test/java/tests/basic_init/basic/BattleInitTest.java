package tests.basic_init.basic;

import campaign.run.battle.BattleBuilder;
import combat.Battle;
import combat.init.BattleSetup;
import framework.AphosTest;
import framework.entity.field.Unit;
import framework.entity.sub.UnitAction;
import framework.field.FieldPos;
import logic.execution.event.user.UserEventType;
import system.threading.WaitMaster;
import system.threading.Weaver;

import static combat.sub.BattleManager.combat;
import static framework.client.ClientConnector.client;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static resources.TestData.battleData;
/**
 * Created by Alexander on 8/22/2023
 */
public class BattleInitTest extends AphosTest {

    protected Unit ally;
    protected Unit enemy;
    protected String[] customData;

    @org.junit.jupiter.api.Test
    public void test() {
        // new MockRun();
        super.test();

        BattleSetup setup = new BattleBuilder().build(getBattleData());
        Battle battle = new Battle(setup);
        // battle.init();
        battle.start();
        //after this, we can use combat() freely?

        assertTrue(combat().getEntities().getUnits().size() == getInitialUnitCount());
        ally =  getMainAllyUnit();
        enemy = getMainEnemyUnit();
        // assertTrue(ally.getName().equals(unit_name_ally));
        // assertTrue(enemy.getName().equals(unit_name_enemy));
        assertTrue(combat().getBattleState().getRound() == 1);
    }

    protected int getInitialUnitCount() {
        return 3;
    }

    protected Unit getMainAllyUnit() {
        return  combat().getUnitById(0);
    }
    protected Unit getMainEnemyUnit() {
        return (Unit) combat().getField().getByPos(new FieldPos(12));
    }

    protected Unit getCheckedUnit() {
        return ally;
    }
    protected String[] getBattleData() {
        if (customData!=null)
            return customData;
        return battleData;
    }

    protected void stdAttack(Unit unit, Unit target) {

        Weaver.inNewThread(() ->
                combat().getExecutor().activate(
                        unit.getActionSet().getStandard()));
        WaitMaster.waitForInput(WaitMaster.WAIT_OPERATIONS.SELECTION);
        WaitMaster.WAIT(10);
        int id = target.getId();
        //simulation eh?
        client().receivedEvent(UserEventType.Selection, id);
        WaitMaster.WAIT(10);
        // DialogMaster.confirm(unit + " Attacked " + target);

    }

    protected void defAction(Unit unit) {
        UnitAction action = unit.getActionSet().getDefense();
        combat().getExecutor().activate(action);
    }
}