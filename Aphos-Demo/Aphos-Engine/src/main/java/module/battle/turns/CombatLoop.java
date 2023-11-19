package module.battle.turns;

import module.battle.handler.init.BattleSetup;
import module.battle.handler.BattleManager;

/**
 * Created by Alexander on 10/21/2023
 * <p>
 * controls game over etc? STRATEGIC PHASE! is this some kind of separate THREAD?
 */
public class CombatLoop {
    private final BattleManager manager;
    private final LoopHandler handler;
    private LoopHandler.RoundPhase phase;
    private boolean timeToFinish; //set from outside to FINISH COMBAT?

    public CombatLoop(BattleSetup battleSetup, BattleManager manager) {
        handler = new LoopHandler(battleSetup);
        this.manager = manager;

    }


    public void start() {
        phase = handler.getInitialPhase(); //we can mark when phase comes back to initial and call THAT newRound
        combatLoop: while (true){
            startPhase();
            if (!loopPhase())
            {
                break combatLoop;
            }
            finishPhase();
            nextPhase();
        }
        finished();

    }

    private void startPhase() {
        switch (phase) {
            case unit_group_turns -> manager.newRound(); //so the round starts/ends with UNIT TURNS?
            //before they go, it is ROUND ZERO?
            //is it normal that our phaseMap makes infinite loop?
        }
    }

    private void finished() {
        throw new RuntimeException("combat finished!");
    }


    private boolean loopPhase() {
        while (true) {
            //there can be any number of initiative groups e.g. or strategy "steps" - but any one of them could end
            //the phase
            boolean finished = phaseStep();
            if (checkCombatFinished()){
                return false;
            }
            if (finished){
                return true;
            }
        }
    }

    private boolean checkCombatFinished() {
        //some secondary condition?!
        return timeToFinish;
    }

    private boolean phaseStep() {
        return switch (phase) {
            case netherflame -> false;
            case player_strategy -> false;
            case enemy_strategy -> false;
            //make it more generic if it's all pretty much the same with step()?
            case unit_group_turns -> manager.getTurnHandler().step();
            case player_deployment -> false;
            case enemy_deployment -> false;
        };
    }

    //we'll have a separate manager for NF/Strategy/..
    //should we be able to decorate this logic in custom impl's of handler?
    private void finishPhase() {
         switch (phase) {
            case unit_group_turns -> manager.roundEnds();
        }
    }

    private void nextPhase() {
        phase = handler.getNext(phase);

        //run thru all handlers at each new phase
        //register for next() input event? Or via controller?

    }

    public void setTimeToFinish(boolean timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    /*
    turn order

     */

}
