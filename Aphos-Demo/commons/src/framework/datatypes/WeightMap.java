package framework.datatypes;


import utils.old.ContainerUtils;
import utils.old.MapMaster;
import utils.old.RandomWizard;
import utils.old.StringMaster;

import java.util.Map;

public class WeightMap<E> extends XLinkedMap<E, Integer> {

    private Class<E> clazz;
    private String separator = ContainerUtils.getContainerSeparator();

    public static final boolean smartRandom = true;
    private WeightMap<Object> original;
    private Object objType;

    public WeightMap(String data, Class<E> clazz) {
        super(new RandomWizard<E>().constructWeightMap(data, clazz));
        this.clazz = clazz;
    }

    public WeightMap() {

    }

    public WeightMap(Class<E> clazz) {
        this.clazz = clazz;
    }

    public WeightMap(Map<E, Integer> map) {
        putAll(map);
    }

    public WeightMap<E> chain(Object key, Integer value) {
        if (key.getClass() == clazz)
            return putChain((E) key, value);
        // if (key instanceof OBJ_TYPE_ENUM) {
        //     return putChain((E) ((OBJ_TYPE_ENUM) key).getName(), value);
        // }
        return putChain((E) key.toString(), value);
    }

    public WeightMap<E> putChain(E key, Integer value) {
        super.put(key, value);
        return this;
    }

    public WeightMap<E> merge(WeightMap<E> map) {
        for (E e : map.keySet()) {
            MapMaster.addToIntegerMap(this, e, map.get(e));
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (E e : keySet()) {
            string.append(e).append(StringMaster.wrapInParenthesis(StringMaster.toFormattedString(get(e)))).append(separator);

        }
        return string.toString();
    }

    public E getRandomByWeight() {
        if (smartRandom && objType != null)
            if (original == null) {
                original = new WeightMap<>();
                for (E e : keySet()) {
                    if (e != null)
                        original.put(e, get(e));
                }
            }

        E obj = new RandomWizard<E>().getObjectByWeight(this);

        if (get(obj) == null) {
            return obj;
        } else if (smartRandom && objType != null) {
            put(obj, get(obj) - 1 - get(obj) / 10);
            if (get(obj) <= 0)
                remove(obj);
            if (isEmpty()) {
                for (Object e : original.keySet()) {
                    if (objType != null) {
//                         ObjType type = (ObjType) e;
// //                        if (type == null) {
// //                            main.system.auxiliary.system.log.LogMaster.system.log(1,"No such type: " +e);
// //                            continue;
// //                        }
//                         put((E) type, original.get(e));
                    } else
                        chain(e, original.get(e));
                }
            }
        }
        return obj;
    }

    public E getGreatest() {
        E greatest = null;
        Integer greatestN = 0;
        for (E key : keySet()) {
            Integer integer = get(key);
            if (integer != null) {
                if (integer > greatestN) {
                    greatest = key;
                    greatestN = integer;
                }
            }
        }
        return greatest;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getSeparator() {
        return separator;
    }

    public Object getObjType() {
        return objType;
    }

    public void setObjType(Object objType) {
        this.objType = objType;
    }

    public void modifyByCoef(float v) {
        for (E e : keySet()) {
            put(e, Math.round(get(e) * v));
        }
    }

//    public void sortByWeight() {
//        List<E> list = new SortMaster<E>().sortByExpression_(false, new ArrayList<>(keySet()),
//                (o) -> get(o));
//        for (E e : list) {
//            put(e, get(e));
//        }
//    }

}
