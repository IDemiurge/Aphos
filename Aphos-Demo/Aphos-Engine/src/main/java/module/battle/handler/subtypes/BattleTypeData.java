package module.battle.handler.subtypes;

import module.battle.foundation.turns.LoopHandler;
import elements.content.battle.types.MiscTypes;

import java.util.HashMap;
import java.util.Map;

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
                phaseMap.put(LoopHandler.RoundPhase.netherflame, LoopHandler.RoundPhase.player_strategy);
                phaseMap.put(LoopHandler.RoundPhase.player_strategy, LoopHandler.RoundPhase.unit_group_turns);
                phaseMap.put(LoopHandler.RoundPhase.unit_group_turns, LoopHandler.RoundPhase.enemy_deployment);
                phaseMap.put(LoopHandler.RoundPhase.enemy_deployment, LoopHandler.RoundPhase.player_deployment);
                phaseMap.put(LoopHandler.RoundPhase.player_deployment, LoopHandler.RoundPhase.netherflame);
            }
        }
    }


    public Map<LoopHandler.RoundPhase, LoopHandler.RoundPhase> getPhaseMap() {
        return phaseMap;
    }

}
