package framework.entity.sub;

import elements.stats.UnitProp;
import framework.data.DataManager;
import framework.entity.field.Unit;
import system.utils.old.ContainerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 8/26/2023
 */
public class PassiveSet extends UnitAttachSet<UnitPassive> {

    public PassiveSet(Unit unit) {
        super(unit);
    }

    @Override
    public List<UnitPassive> createList() {
        List<UnitPassive> list =   new ArrayList<>() ;
        for (String name : ContainerUtils.open(unit.getS(UnitProp.Passives))) {
            //check it is just a perk etc...
            Map<String, Object> values = DataManager.getPassiveData(name);
            Map<String, Object> vars = DataManager.getTypeVars(name);
            list.add(new UnitPassive(values, vars, unit));
        }
        return list;
    }

    //continuously or once? To support removal of passives - either link it to Source, or add/remove for each reset()
    public void init(){
        for (UnitPassive passive : list) {
            passive.apply(); //some passives may have a condition that disables them, e.g. BURNS
        }
    }


    public boolean has(String name) {
        return list.stream().filter(p -> p.getName().equalsIgnoreCase(name)).count()==1;
    }
}
