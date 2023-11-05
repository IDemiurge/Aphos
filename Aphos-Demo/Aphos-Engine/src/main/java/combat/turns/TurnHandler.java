package combat.turns;

import apps.server.internal.InputKey;
import combat.BattleHandler;
import combat.sub.BattleManager;
import elements.stats.UnitParam;
import elements.stats.UnitProp;
import framework.entity.field.Unit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 10/21/2023
 */
public class TurnHandler extends BattleHandler {

    private List<InitiativeGroup> groups;
    private InitiativeGroup current;

    public TurnHandler(BattleManager battleManager) {
        super(battleManager);
    }

    public static int calcInitiative(Unit unit) {
        return unit.getInt(UnitParam.AP) * 2 + getPositionModifier(unit)
                + unit.getInt(UnitParam.Chaos); //also modifier?
    }

    private static int getPositionModifier(Unit unit) {
        return switch (unit.getPos().getCell().type) {
            case Flank -> -2;
            case Van -> 1;
            case Front -> 0;
            case Back -> -1;
            case Rear -> -3;
            case Reserve -> -999;
        };
    }

    @Override
    public void newRound() {
        reset();
        groups = createInitiativeGroups(); //we'd send some info before first step()?
    }

    public boolean step() {
        groups = createInitiativeGroups();
        current = nextGroup();
        groupStarts(current);
        while (true) {
            if (!turn())
                break;
        }
        groupFinished(current);
        if (checkFinished())
            return false;
        return true;
    }

    private boolean turn() {
        //wait for input?
        // KotlinUtils.Companion.doWithInput();
        //CONSIDER THAT NOT ONLY THIS HANDLER WILL NEED THIS AWAIT()!
        if (!current.ally) {
            //AI
        } else {
            await(InputKey.UnitTurn); //input for this must only be enabled HERE, eh?

        }
        //enable unit turn and wait for input; then execute and check results
        //how will it work for TESTS? Test can send input repeatedly with ActionInput?

        if (checkGroupDone(current))
            return false;
        //Vars.passed == true OR all done (ai/auto)
        return true;
    }


    private boolean checkGroupDone(InitiativeGroup group) {
        if (group.ally){
            // Switches.player_group_done().if
        } else {
            //same with AI?
        }
        for (Unit unit : group.units) {
            int minActionCost=0;
            if (!unit.checkParam(UnitParam.AP, minActionCost)){
                return false;
            }
        }

        return true;
    }

    private boolean checkFinished() {
        current = nextGroup();
        return current==null;
    }


    public void groupFinished(InitiativeGroup group) {
        group.units.forEach(unit -> unit.setProp(UnitProp.Active, false));
        group.units.forEach(unit -> unit.setProp(UnitProp.FinishedTurn, true));
    }

    public void groupStarts(InitiativeGroup group) {
        group.units.forEach(unit -> unit.setProp(UnitProp.Active, true));

    }

    public InitiativeGroup getCurrent() {
        return current;
    }


    public InitiativeGroup nextGroup() {
        return groups.get(0);
    }


    public List<Unit> sorted(List<Unit> list) {
        return list.stream().sorted(getComparator()).collect(Collectors.toList());
    }

    @Override
    public void reset() {
        forEach(unit -> unit.setValue(UnitParam.Initiative, calcInitiative(unit)));
        //[03-11-23] - we may have to update to show Preview of future
        //what should be done after each ACTION? same init recalc?

        //ONLY AFTER GROUP IS FINISHED! But we can displayed some future precalc
        // groups = createInitiativeGroups();


        //how to check if any initiative has changed and trigger group re-build? some hashcode for the sum?
        //some groups are already DONE - then their initiative is what?
        //use some history manager? with all actions etc?
        //some units will PASS their turn without spending all AP! So - 'over' status, as well as WAIT?

    }

    private Comparator<Unit> getComparator() {
        return (o1, o2) -> {
            if (o1.initiative() == o2.initiative())
                return 0;
            if (o1.initiative() > o2.initiative())
                return 1;
            return -1;
        };
    }

    private Comparator<InitiativeGroup> getGroupComparator() {
        return (o1, o2) -> {
            if (o1.maxInitiative == o2.maxInitiative)
                return o1.ally ? 1 : -1;
            if (o1.maxInitiative > o2.maxInitiative)
                return 1;
            return -1;
        };
    }

    public List<InitiativeGroup> createInitiativeGroups() {
        List<InitiativeGroup> ally_groups = createInitiativeGroups(true);
        List<InitiativeGroup> enemy_groups = createInitiativeGroups(false);
        List<InitiativeGroup> sortedGroups = new ArrayList<>();
        sortedGroups.addAll(ally_groups);
        sortedGroups.addAll(enemy_groups);
        Collections.sort(sortedGroups, getGroupComparator());
        return sortedGroups;
    }

    public List<InitiativeGroup> createInitiativeGroups(boolean ally) {
        List<Unit> units = sorted(getEntities().getUnitsFiltered(
                u -> u.isAlly() == ally &&
                        !u.isTrue(UnitProp.FinishedTurn) &&
                        !u.isTrue(UnitProp.Dead)));
        List<InitiativeGroup> groups = new ArrayList<>();
        int max = 3;
        int maxDiff = 1;
        Integer prevInitiative = null;
        ListIterator<Unit> iterator = units.listIterator();

        Unit nextUnit = null;
        loop:
        while (true) {
            List<Unit> sub = new ArrayList<>();
            if (nextUnit != null)
                sub.add(nextUnit);
            int maxInitiative = 0;
            for (int i = 0; i < max; i++) {
                if (!iterator.hasNext()) {
                    groups.add(new InitiativeGroup(sub, prevInitiative, ally));
                    break loop;
                }
                maxInitiative = units.get(iterator.nextIndex()).initiative();
                nextUnit = iterator.next();
                //if the closest next unit has 2+ initiative behind, they cannot be in this group
                if (prevInitiative != null && nextUnit.initiative() - prevInitiative > maxDiff) {
                    prevInitiative = nextUnit.initiative();
                    break;
                }
                prevInitiative = nextUnit.initiative();
                sub.add(nextUnit);
                nextUnit = null;
            }
            if (nextUnit == null) //exited normally
                groups.add(new InitiativeGroup(sub, maxInitiative, ally));

        }
        return groups;

    }

}
