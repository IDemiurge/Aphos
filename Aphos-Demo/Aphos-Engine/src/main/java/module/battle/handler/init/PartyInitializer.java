package module.battle.handler.init;

import module.battle.parts.battlefield.BattleField;
import framework.data.DataManager;
import framework.combat.entity.field.Unit;
import framework.combat.field.FieldPos;
import utils.old.NumberUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alexander on 8/23/2023
 */
public class PartyInitializer {

    private static final int ALLY = 1;
    private static final int ENEMY = -1;
    private final BattleField field;
    private final BattleSetup setup;

    public PartyInitializer(BattleField field, BattleSetup setup) {
        this.field = field;
        this.setup = setup;
    }

    public void initParties() {
        initParty(setup.getAlly().unitData, setup.getAlly().ally);
        initParty(setup.getEnemy().unitData, setup.getEnemy().ally);
        //setup env etc   setup.getData()
        //the rest should be done by specific managers
    }

    private void initParty(String partyData, Boolean ally) {
        Map<String, Object> map = DataManager.stringArrayToMap(partyData.split(";"));
        for (String name : map.keySet()) {
            Integer posId = NumberUtils.getInt(name);
            FieldPos pos = field.getPos(posId);
            Map<String, Object> unitData= DataManager.getUnitData(map.get(name).toString());
            Set<Unit> units = new HashSet<>();
            int faction = ally ? ALLY : ENEMY;
            units.add(new Unit(unitData, faction, pos));
            //save initial party? What for?
        }
    }

}
