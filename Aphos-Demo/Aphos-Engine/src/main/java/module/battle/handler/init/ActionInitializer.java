package module.battle.handler.init;

import elements.content.enums.stats.unit.UnitProp;
import framework.data.DataManager;
import framework.combat.entity.field.Unit;
import framework.combat.entity.sub.ActionSet;
import framework.combat.entity.sub.UnitAction;

/**
 * Created by Alexander on 8/23/2023
 */
public class ActionInitializer {
    public static ActionSet initActionSet(Unit unit) {
        UnitAction std = initAction(unit, unit.getS(UnitProp.Standard_Attack));
        UnitAction pwr =null;// initAction(unit, unit.getS(UnitProp.Power_Attack));
        UnitAction def = initAction(unit, unit.getS(UnitProp.Defense_Action ));
        return new ActionSet(std, pwr, def);
    }

    private static UnitAction initAction(Unit unit, String actionName) {
        return new UnitAction(DataManager.getActionData(actionName),DataManager.getTypeVars(actionName), unit);
    }
}
