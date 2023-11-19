package framework.combat.entity.sub.generic;

import elements.exec.EntityRef;
import framework.combat.entity.Entity;
import framework.combat.entity.field.Unit;

import java.util.Map;

/**
 * Created by Alexander on 8/21/2023
 */
public class UnitSubEntity extends Entity {
    protected Unit unit;

    public UnitSubEntity(Map<String, Object> valueMap, Unit unit) {
        super(valueMap);
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public EntityRef ref() {
        return new EntityRef(unit);
    }
}
