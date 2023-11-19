package tests.basic_init.basic;

import module.campaign.run.battle.BattleBuilder;
import module.battle.Battle;
import module.battle.handler.init.BattleSetup;
import framework.AphosTest;
import framework.combat.entity.field.Unit;
import framework.combat.entity.sub.UnitAction;
import framework.combat.field.FieldPos;
import system.KotlinUtils;

import static module.battle.handler.BattleManager.combat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static resources.TestData.battleData;

/**
 * Created by Alexander on 8/22/2023
 */
public class BattleInitTest extends AphosTest {

    protected Unit ally;
    protected Unit enemy;
    protected String[] customData;

    public BattleInitTest() {
        super();
        setup();
        test();
    }

    private void test() {
        assertTrue(combat().getEntities().getUnits().size() == getInitialUnitCount());
        assertTrue(combat().getBattleState().getRound() == 1);
    }

    public void setup() {
        BattleSetup setup = new BattleBuilder().build(getBattleData());
        Battle battle = new Battle(setup);
        // battle.init();
        battle.start();

        ally = getMainAllyUnit();
        enemy = getMainEnemyUnit();
    }

    protected int getInitialUnitCount() {
        return 3;
    }

    protected Unit getMainAllyUnit() {
        return combat().getUnitById(0);
    }

    protected Unit getMainEnemyUnit() {
        return (Unit) combat().getField().getByPos(new FieldPos(12));
    }

    protected Unit getCheckedUnit() {
        return ally;
    }

    protected String[] getBattleData() {
        if (customData != null)
            return customData;
        return battleData;
    }

    protected void stdAttack(Unit unit, Unit target) {
        KotlinUtils.Companion.doWithInput(target, () ->
                combat().getExecutor().activate(
                        unit.getActionSet().getStandard()));

    }

    protected void defAction(Unit unit) {
        UnitAction action = unit.getActionSet().getDefense();
        combat().getExecutor().activate(action);
    }
}
