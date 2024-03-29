package framework.combat.entity.field;

import elements.exec.EntityRef;
import elements.content.enums.entity.unit.Counter;
import elements.content.enums.stats.unit.UnitProp;
import framework.combat.entity.Entity;
import framework.combat.entity.sub.CountersSet;
import framework.combat.entity.sub.UnitAction;
import framework.combat.field.FieldPos;
import framework.combat.field.enums.Visibility;

import java.util.Map;

/**
 * Created by Alexander on 8/21/2023
 */
public class FieldEntity extends Entity {
    protected FieldPos pos; //what about LARGE?
    protected Visibility visibility = Visibility.Visible;
    protected FieldPos prevPos;
    protected CountersSet counters; //we can intercept get() for calc of formulas and so keep the valueMap free of these!


    public FieldEntity(Map<String, Object> valueMap, FieldPos pos) {
        super(valueMap);
        counters = new CountersSet(this);
        this.pos = pos;
    }

    public FieldPos getPos() {
        return pos;
    }

    public Visibility getEnemyVisibility() {
        return visibility;
    }

    public void setPos(FieldPos pos) {
        prevPos = this.pos;
        this.pos = pos;
    }

    public int getAtkOrSp(UnitAction action, EntityRef ref) {
        return 0;
    }

    public int getDefOrRes(UnitAction action, EntityRef ref) {
        return 0;
    }

    public boolean checkContainerProp(UnitProp prop, String value) {
       return  data.valueContains(prop.getName(), value);
    }

    public void addCounters(Counter counter, Integer amount, EntityRef ref) {
        counters.tryAdd(amount, counter, ref);
    }

    public CountersSet getCounters() {
        return counters;
    }
}
