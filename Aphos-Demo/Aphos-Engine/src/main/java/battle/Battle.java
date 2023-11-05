package battle;

import system.server.internal.InputKey;
import async.Async;
import battle.handler.init.BattleSetup;
import battle.handler.BattleManager;
import battle.handler.subtypes.skirmish.SkirmishManager;
import battle.turns.CombatLoop;
import elements.content.enums.types.MiscTypes;
import utils.threading.WaitMaster;

/**
 * Created by Alexander on 6/10/2023 Replay - set of states?
 */
public class Battle {
    public static Battle current;
    private BattleManager manager;
    CombatLoop combatLoop;
    //state - round, phase, global data (nf level, ...)
    // BattleAi battleAi;

    public Battle(BattleSetup battleSetup) {
        current = this;
        // init();

        // state = createState();
        if (battleSetup.getBattleType() == MiscTypes.BattleType.Skirmish) {
            //so if manager encapsulates handlers - how big of a diff should this make?
            // will this be somewhat like metaGameHandlers?!
            // well, if it is all centralized.... maybe it won't be such a disaster?
            manager = new SkirmishManager(battleSetup);
        }
        combatLoop = new CombatLoop(battleSetup, manager);
    }

    public void end() {
        manager.battleEnds();
    }

    public void start() {
        manager.battleStarts();
        new Thread("Combat Loop"){
            @Override
            public void run() {
                combatLoop.start();
            }
        }.start();
        WaitMaster.WAIT(100);
        //TODO
        //we should wait until we know that there's a waiter for UnitTurn!
        Async.receive(InputKey.UnitTurn, "100ms => Fake Turn Started!");
        //just a check, right?

        // further turns are to be made/mocked with this
    }


    public BattleManager getManager() {
        return manager;
    }
}
