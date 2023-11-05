package elements.content.enums.entity.unit;

import elements.content.enums.stats.unit.UnitParam;

public enum Perk {
    Patience(UnitParam.Ap_retain),
    ;
    UnitParam param;

    Perk(UnitParam param) {
        this.param = param;
    }
}
