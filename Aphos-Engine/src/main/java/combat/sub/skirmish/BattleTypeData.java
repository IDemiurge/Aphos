package combat.sub.skirmish;

import combat.turns.LoopHandler;
import elements.content.enums.types.MiscTypes;

import java.util.HashMap;
import java.util.Map;

import static combat.turns.LoopHandler.RoundPhase.*;

/**
 * Created by Alexander on 11/3/2023
 */
public class BattleTypeData {
    private final MiscTypes.BattleType battleType;
    private Map<LoopHandler.RoundPhase, LoopHandler.RoundPhase> phaseMap;

    public BattleTypeData(MiscTypes.BattleType battleType) {
        this.battleType = battleType;
        initPhaseMap(battleType);
    }

    private void initPhaseMap(MiscTypes.BattleType battleType) {
        phaseMap = new HashMap<>();
        switch (battleType) {
            case Skirmish -> {
                phaseMap.put(netherflame, player_strategy);
                phaseMap.put(player_strategy, unit_group_turns);
                phaseMap.put(unit_group_turns, enemy_deployment);
                phaseMap.put(enemy_deployment, player_deployment);
                phaseMap.put(player_deployment, netherflame);
            }
        }
    }


    public Map<LoopHandler.RoundPhase, LoopHandler.RoundPhase> getPhaseMap() {
        return phaseMap;
    }

}
