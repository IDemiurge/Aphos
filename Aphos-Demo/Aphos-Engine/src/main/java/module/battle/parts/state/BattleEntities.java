package module.battle.parts.state;

import module.battle.handler.BattleHandler;
import module.battle.handler.BattleManager;
import elements.exec.EntityRef;
import elements.exec.condition.Condition;
import elements.exec.targeting.Targeting;
import elements.content.enums.stats.unit.UnitParam;
import elements.content.enums.stats.unit.UnitProp;
import elements.content.enums.stats.generic.StatConsts;
import framework.combat.entity.Entity;
import framework.combat.entity.field.FieldEntity;
import framework.combat.entity.field.FieldOmen;
import framework.combat.entity.field.HeroUnit;
import framework.combat.entity.field.Unit;
import framework.combat.entity.sub.UnitAction;
import framework.combat.entity.sub.TriggerPassive;
import logic.execution.event.combat.CombatEventType;
import system.consts.StatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 8/22/2023
 */
public class BattleEntities extends BattleHandler {
    Map<Class<? extends Entity>, Map<Integer, Entity>> entityMaps = new HashMap<>();
    private int ID = 0;

    public BattleEntities(BattleManager battleManager) {
        super(battleManager);
        entityMaps.put(Unit.class, new HashMap<>());
        entityMaps.put(HeroUnit.class, new HashMap<>());
        entityMaps.put(UnitAction.class, new HashMap<>());
        entityMaps.put(TriggerPassive.class, new HashMap<>());
        entityMaps.put(FieldEntity.class, new HashMap<>());
        entityMaps.put(FieldOmen.class, new HashMap<>());
    }

    ///////////////// region INIT METHODS ///////////////////
    public <T extends Entity> Integer addEntity(T entity) {
        Integer id = ID++;
        entityMaps.get(getKeyClass(entity.getClass())).put(id, entity);
        return id;
    }

    //endregion
    ///////////////// region GETTERS ///////////////////
    private Class<? extends Entity> getKeyClass(Class<? extends Entity> aClass) {
        //some exceptions?
        return aClass;
    }

    public <T extends Entity> T getEntityById(Integer id, Class<T> entityClass) {
        return (T) entityMaps.get(entityClass).get(id);
    }

    public List<Unit> getUnits() {
        // return entityMaps.get(Unit.class).values().stream().collect(Collectors.toList());
        return getEntityList(Unit.class);
    }

    public List<Unit> getAlliedUnits() {
        return getFilteredList(Unit.class, e -> e.isAlly());
    }

    public List<Unit> getEnemyUnits() {
        return getFilteredList(Unit.class, e -> !e.isAlly());
    }
    // public List<Unit> sorted(List<Unit> list) {
    //     return list.stream().sorted(getComparator()).collect(Collectors.toList());
    // }

    public List<Unit> getUnitsFiltered(Predicate<Unit>... predicates) {
        return getFilteredList(Unit.class, predicates);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> getFilteredList(Class<T> clazz, Predicate<T>... predicates) {
        return (List<T>) entityMaps.get(clazz).values().stream().filter(e -> {
                    for (Predicate<T> predicate : predicates) {
                        if (!predicate.test((T) e)) return false;
                    }
                    return true;
                }).
                collect(Collectors.toList());
    }

    public <T extends Entity> List<T> getEntityList(Class<T> clazz) {
        return (List<T>) entityMaps.get(clazz).values().stream().collect(Collectors.toList());
    }

    public <T extends Entity> List<T> getMergedEntityList(Class<? extends T>... clazz) {
        List<T> merged = new ArrayList<>();
        for (Class aClass : clazz) {
            List<T> collect = (List<T>) entityMaps.get(aClass).values().stream().collect(Collectors.toList());
            merged.addAll(collect);
        }
        return merged;
    }

    public List<FieldEntity> getFieldEntities() {
        return getMergedEntityList(FieldEntity.class, Unit.class);
    }

    //endregion

    ///////////////// region UPDATE METHODS ///////////////////

    @Override
    public void newRound() {
        // SysLog.
        forEach(u-> restoreRoundlyValues(u));
    }

    @Override
    public void roundEnds() {
        forEach(u-> markSavedValues(u));
    }

    private void markSavedValues(Unit unit) {
        int maxRetain = unit.getInt(UnitParam.Ap_retain);
        int retain = Math.min(maxRetain, unit.getInt(UnitParam.AP));
        unit.setValue(UnitParam.AP_saved, retain);

        maxRetain = unit.getInt(UnitParam.Moves_retain);
        retain = Math.min(maxRetain, unit.getInt(UnitParam.Moves));
        unit.setValue(UnitParam.Moves_saved, retain);
    }

    private void restoreRoundlyValues(Unit unit) {
        // saved | max | cur
        for (UnitParam param : StatConsts.roundlyParams) {
            if (unit.isTrue(StatUtils.getBroken(param))) {
                continue;
            }
            // if (broken) //disabled regen?
            //     continue; //what for? calc saved?
            int max = unit.getInt(StatUtils.getTotalParam(param.getName()));
            unit.setValue(param, max); //restore to full
            if (param == UnitParam.AP || param == UnitParam.Moves) {
                int saved = unit.getInt(StatUtils.getSavedParam(param.getName())); //sanity/faith?
                if (saved > 0) {
                    int cur = unit.getInt(param);
                    int retain = unit.getInt(StatUtils.getRetain(param)); //max that unit can retain on their own
                    if (cur <= max+ retain);
                    unit.addCurValue(param, saved); //bonus
                }
            }

        }
    }

    @Override
    public void reset() {
        //for all entities? I'd limit this for now
        //what about buffs?
        //check continuous conditions
        for (Unit unit : getUnits()) {
            unit.toBase();
        }
    }

    public void kill(EntityRef ref, boolean body) {
        ref.getTarget().setValue(UnitProp.Dead, true);
        BattleManager.combat().event(body ? CombatEventType.Unit_Death_Body : CombatEventType.Unit_Death_Soul, ref);
    }

    public List<FieldEntity> targetFilter(EntityRef ref, Targeting targeting) {
        Condition condition = targeting.getCondition();
        List<FieldEntity> list = getFieldEntities();
        list.removeIf(e -> !condition.check(ref.setMatch(e)));
        return list;
    }

    //endregion

}
