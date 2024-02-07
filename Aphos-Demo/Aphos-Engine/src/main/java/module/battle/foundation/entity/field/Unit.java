package module.battle.foundation.entity.field;

import module.battle.foundation.entity.sub.ActionSet;
import module.battle.foundation.entity.sub.OmenStack;
import module.battle.foundation.entity.sub.TriggerPassiveSet;
import module.battle.foundation.entity.sub.UnitAction;
import module.battle.foundation.battlefield.enums.Cell;
import module.battle.handler.init.ActionInitializer;
import elements.exec.EntityRef;
import elements.content.battle.stats.unit.UnitParam;
import elements.content.battle.stats.unit.UnitProp;
import elements.content.generic.stats.StatConsts;
import module.battle.foundation.battlefield.FieldPos;
import framework.data.consts.StatUtils;

import java.util.Map;

/**
 * Created by Alexander on 8/20/2023 Should we have a FieldEntity ? For obstacles? Maybe... something else? Omens? Hm Is
 * there a good way to use Aggregation?
 */
public class Unit extends FieldEntity {

    private final int faction;
    protected ActionSet actionSet; //HeroActionSet?
    protected TriggerPassiveSet triggerPassiveSet;
    protected OmenStack omens;

    public Unit(Map<String, Object> valueMap, int faction) {
        this(valueMap, faction  , new FieldPos(faction  > 0  ? Cell.Reserve_ally : Cell.Reserve_enemy));
        //create w/o pos to deploy later? Or with default 'reserve pos'? To make it non-null!
    }

    public Unit(Map<String, Object> valueMap, int faction, FieldPos pos) {
        super(valueMap, pos);
        this.faction = faction;
        initCurrentValues();
        actionSet = ActionInitializer.initActionSet(this);
        triggerPassiveSet = new TriggerPassiveSet(this);
        initDone();
    }

    protected void initAndRemoveFromMap(Map<String, Object> valueMap) {
        initPerks(valueMap);
    }
    private void initPerks(Map<String, Object> valueMap) {
        Object perks = valueMap.remove("Perks");
        if (perks!=null){
            //TODO add as plain params?
        }
    }

    private void initCurrentValues() {
        for (UnitParam cur : StatConsts.unitCurrentVals) {
            Integer value = getInt(cur);
            data.setCur(cur.getName(), value);
            UnitParam max = StatUtils.getTotalParam(cur.getName() );
            data.set(max, value);
        }
    }


    //////////////////region GETTERS ////////////////////
    public ActionSet getActionSet() {
        return actionSet;
    }

    public OmenStack getOmens() {
        return omens;
    }

    //endregion
    //////////////////region SHORTCUTS ////////////////////
    public int getAtkOrSp(UnitAction action, EntityRef ref) {
        Boolean min_base_max = null;//        omen.current.get();
        if (action.isSpell()) {
            //sp coef and all that?!
        }
        return getInt(StatConsts.getAtkVal(min_base_max));
    }

    public int getDefOrRes(UnitAction action, EntityRef ref) {
        Boolean min_base_max = null;//        omen.current.get();
        return getInt(action.isSpell()
                ? StatConsts.getResVal(min_base_max)
                : StatConsts.getDefVal(min_base_max));
    }

    public Object get(UnitProp stat) {
        return super.get(stat);
    }

    public Boolean isTrue(UnitProp key) {
        return data.isTrue(key);
    }

    public int getInt(String key) {
        Integer value = counters.get(key);
        if (value==null)
            return data.getInt(key);
        return value;
    }
    public int getInt(UnitParam stat) {
        return data.getInt(stat);
    }

    public String getS(UnitProp stat) {
        return getS(stat.getName());
    }

    public boolean isAlly() {
        return faction > 0;
    }

    public int initiative() {
        return getInt(UnitParam.Initiative);
    }

    public void setProp(UnitProp prop, Object value) {
        setValue(prop, value);
    }
    public void setParam(UnitParam param, Object value) {
        setValue(param, value);
    }

    //endregion

    @Override
    public EntityRef ref() {
        return new EntityRef(this);
    }
    public boolean checkParam(UnitParam p, int value) {
        return getInt(p) > value;
    }

    public TriggerPassiveSet getPassives() {
        return triggerPassiveSet;
    }
}
