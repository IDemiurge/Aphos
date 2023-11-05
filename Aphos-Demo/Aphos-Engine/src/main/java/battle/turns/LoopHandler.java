package battle.turns;

import battle.handler.init.BattleSetup;
import battle.handler.subtypes.BattleTypeData;

import java.util.Map;

/**
 * Created by Alexander on 11/3/2023
 */
public class LoopHandler {
    private final BattleTypeData battleTypeData;
    private Map<RoundPhase, RoundPhase> nextMap ;

    public LoopHandler(BattleSetup battleSetup) {
        battleTypeData = battleSetup.getBattleTypeData();
        nextMap=battleTypeData.getPhaseMap();
    }


    public RoundPhase getInitialPhase() {
        return RoundPhase.unit_group_turns;
    }

    public RoundPhase getNext(RoundPhase phase) {
        return nextMap.get(phase);
    }

    public enum RoundPhase {
        netherflame,
        player_strategy,
        enemy_strategy,
        unit_group_turns,
        player_deployment,
        enemy_deployment,
        //boss? NF?

    }
}
