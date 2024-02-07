package elements.content.battle.entity.unit;

import elements.content.battle.stats.unit.UnitParam;

public enum Perk {
    Patience(UnitParam.Ap_retain),
    ;
    UnitParam param;

    Perk(UnitParam param) {
        this.param = param;
    }
}
