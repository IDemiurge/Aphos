package framework.datatypes;


import utils.old.StringMaster;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class XLinkedMap<E, T> extends LinkedHashMap<E, T> {
    private boolean findClosest;

    public XLinkedMap(int i) {
        super(i);
    }

    public XLinkedMap(Map<? extends E, ? extends T> m) {
        super(m);
    }

    public XLinkedMap() {
        super();
    }

    public void setFindClosest(boolean findClosest) {
        this.findClosest = findClosest;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(size() + " size map: ");
        for (Map.Entry<E, T> entry : entrySet()) {
            string.append(entry.getKey()).append(" = ").append(entry.getValue()).append(";");
        }
        return string.toString();
    }

    public T getByIndex(int index) {
        return get(new ArrayList<>(keySet()).get(index));
    }

    @Deprecated
    public T smartGet(Object key) {
        if (key == null) {
            return null;
        }
        T t = super.get(key);
        if (findClosest)
            if (t == null) {
                for (E e : keySet()) {
                    if (e != null) {
                        if (e.toString().equalsIgnoreCase(key.toString())) {
                            return super.get(e);
                        }
                    }

                }
                if (key instanceof String) {
                    String string = (String) key;
                    t = super.get(string.toUpperCase());
                    if (t == null) {
                        t = super.get(string.toLowerCase());
                    }
                    if (t == null) {
                        String wellFormattedString = StringMaster.format(string);
                        t = super.get(wellFormattedString);

                        // s.replace(" ", "")

                        if (t == null) {
                            if (wellFormattedString.endsWith("s")) {
                                t = super.get(wellFormattedString.substring(0, string.length() - 1));
                            } else {
                                t = super.get(wellFormattedString + "s");
                            }
                        }
                    }
                }
            }
        return t;
    }

    @Override
    public T remove(Object key) {
        return super.remove(key);
    }

}
