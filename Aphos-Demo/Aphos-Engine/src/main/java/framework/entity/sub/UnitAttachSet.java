package framework.entity.sub;

import framework.entity.field.Unit;

import java.util.List;

/**
 * Created by Alexander on 10/31/2023
 */
public abstract class UnitAttachSet<T> {
    protected final List<T> list;
    protected Unit unit;

    public UnitAttachSet(Unit unit) {
        this.unit = unit;
        list = createList();
    }

    public abstract List<T> createList();

    public List<T> getList() {
        return list;
    }

    public boolean has(String name) {
        return getList().stream().filter(p -> p.toString().equalsIgnoreCase(name)).count()==1;
    }
}
