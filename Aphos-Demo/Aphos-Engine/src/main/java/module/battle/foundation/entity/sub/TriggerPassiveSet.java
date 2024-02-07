package module.battle.foundation.entity.sub;

import elements.content.battle.stats.unit.UnitProp;
import module.battle.foundation.entity.field.Unit;
import module.battle.foundation.entity.sub.generic.UnitAttachSet;
import framework.data.DataManager;
import utils.old.ContainerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 8/26/2023
 */
public class TriggerPassiveSet extends UnitAttachSet<TriggerPassive> {

    public TriggerPassiveSet(Unit unit) {
        super(unit);
    }

    @Override
    public List<TriggerPassive> createList() {
        List<TriggerPassive> list =   new ArrayList<>() ;
        for (String name : ContainerUtils.open(unit.getS(UnitProp.Passives))) {
            //check it is just a perk etc...
            Map<String, Object> values = DataManager.getPassiveData(name);
            Map<String, Object> vars = DataManager.getTypeVars(name);
            list.add(new TriggerPassive(values, vars, unit));
        }
        return list;
    }

    @Override
    public void apply() {
        for (TriggerPassive passive : list) {
            passive.apply();
        }
    }

    //continuously or once? To support removal of passives - either link it to Source, or add/remove for each reset()
    public void init(){
       apply();  //???
    }


    public boolean has(String name) {
        return list.stream().filter(p -> p.getName().equalsIgnoreCase(name)).count()==1;
    }
}
