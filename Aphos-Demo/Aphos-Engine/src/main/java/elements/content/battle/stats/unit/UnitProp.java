package elements.content.battle.stats.unit;

import elements.content.generic.stats.Stat;

public enum UnitProp implements Stat {
    //bools | qualities
    Daemon, Pure, Infiltrator, Agile, Large,

    //bools | statuses
    Waiting(true), Active(true),FinishedTurn(true),
    Dead(true), Death_Door(true),
    Wound_Body(true),Wound_Head(true),Wound_Limbs(true),
    //ap regen mod?
    Mind_Restore_Blocked, //apply dynamically while wound is there


    Summoned(true),
    //containers
    Wards, Immune, Vulnerable,
    Perks, Passives, Extra_Actions,
    //single
    Faction,
    Standard_Attack,Power_Attack,Defense_Action,
    ;
    boolean persistent;

    UnitProp() {
    }

    UnitProp(boolean persistent) {
        this.persistent = persistent;
    }

    public boolean isPersistent() {
        return persistent;
    }
}
