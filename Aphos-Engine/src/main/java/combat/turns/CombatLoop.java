package combat.turns;

import combat.sub.BattleManager;

/**
 * Created by Alexander on 10/21/2023
 * <p>
 * controls game over etc? STRATEGIC PHASE! is this some kind of separate THREAD?
 */
public class CombatLoop {
    RoundPhase phase;

    public CombatLoop(BattleManager manager) {

    }

    public void playerGroupFinish() {
        //when all current are out of AP + auto-pass | or player manually presses <?>
    }

    public void start() {
        // client().sendEvent();
    }
    public void nextPhase(){
        //run thru all handlers at each new phase
        //register for next() input event? Or via controller?

    }

    public enum RoundPhase {
        player_strategy,
        enemy_strategy,
        player_deployment,
        enemy_deployment,


    }
    /*
    turn order

     */

}
