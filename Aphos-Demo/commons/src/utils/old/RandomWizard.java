package utils.old;

import datatypes.WeightMap;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWizard<E> {
    static Random randomGenerator =  ThreadLocalRandom.current();
    private static boolean averaged;
    private static final Map<String, WeightMap> weighMapCache = new HashMap<>();
    private LinkedHashMap<Integer, E> invertedMap;

    public static boolean isWeightMap(String property) {
        for (String string : ContainerUtils.open(property)) {
            try {
                if (StringMaster.getWeight(string) > 1) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }


    public static int getRandomIntBetween(int i, int j, boolean inclusive) {
        return getRandomIntBetween(i, j, inclusive, randomGenerator);
    }

    public static int getRandomIntBetween(int i, int j, boolean inclusive, Random randomGenerator) {
        if (i > j && i > 0 && j > 0) {
            int buffer = i;
            i = j;
            j = buffer;
        }
        int n = j - i;
        if (n == 0) {
            return 0;
        }
        boolean negative = false;
        if (n < 0) {
            n = Math.abs(n);
            negative = true;
        }
        if (inclusive) {
            n++;
        }
        int k = (averaged) ? i + Math.round(n / 2) : i + randomGenerator.nextInt(n);
        // main.system.auxiliary.LogMaster.system.log(1, "*** NEW RANDOM: " + k + "[" +
        // i + " - " + j + "], "
        // + randomGenerator);
        if (negative) {
            return -k;
        }
        return k;
    }

    public static boolean isAveraged() {
        return averaged;
    }

    public static void setAveraged(boolean averaged) {
        RandomWizard.averaged = averaged;
    }

    public static int getRandomIntBetween(int i, int j) {
        return getRandomIntBetween(i, j, randomGenerator);
    }

    // INCLUSIVE
    public static int getRandomIntBetween(int i, int j, Random randomGenerator) {
        return getRandomIntBetween(i, j, false, randomGenerator);
    }

    public static boolean chance(int i) {
        return chance(i, randomGenerator);
    }

    public static boolean chance(int i, Random random) {
        if (i >= 100) {
            return true;
        }
        if (i <= 0) {
            return false;
        }
        int res = getRandomIntBetween(0, 100, random);
        return res < i;
    }

    public static int getRandomIndex(Collection list) {
        //TODO lock? finally?
        boolean bool = averaged;
        averaged = false;
        int index = getRandomIndex(list, randomGenerator);
        averaged = bool;
        return index;
    }

    public static int getRandomIndex(Collection list, Random random) {
        if (list == null) {
            return -1;
        }
        if (list.isEmpty()) {
            return -1;
        }
        return getRandomIntBetween(0, list.size(), random);
    }

    public static int getRandomInt(int j) {
        if (j == 0) {
            return 0;
        }
        if (j < 0) {
            return -getRandomIntBetween(0, -j);
        }

        return getRandomIntBetween(0, j);
    }

    public static boolean random() {
        return random(randomGenerator);
    }

    public static boolean random(Random random) {
        return random.nextBoolean();
    }

    public static Random getRandomGenerator() {
        return randomGenerator;
    }

    public static void setRandomGenerator(Random randomGenerator) {
        RandomWizard.randomGenerator = randomGenerator;
    }

    public static Object getRandomListObject(List list) {
        return list.get(getRandomIndex(list));
    }

    public static float getRandomFloat() {
        return getRandomFloatBetween(0, 1f);
    }

    public static float getRandomFloatBetween(float alphaMin, float alphaMax) {
        return
         alphaMin + (randomGenerator.nextFloat() * (alphaMax - alphaMin));
        //       brutish...
        // return new Float(getRandomIntBetween((int) (alphaMin * 100),
        //         (int) (alphaMax * 100))) / 100;
    }

    public static float randomize(float amount, float randomness) {
        return  amount * (1 - randomness + getRandomFloatBetween(0, randomness*2));
    }

    public static <T> T getRandomFrom(T... s) {
        return s[getRandomInt(s.length)];
    }

    public E getObjectByWeight(String string, Class<? extends E> CLASS) {
        return getObjectByWeight(constructWeightMap(string, CLASS));
    }

    public E getRandomSetItem(Set<E> set) {
        return (E) set.toArray()[getRandomIndex(set)];
    }

    public E getRandomListItem(List<E> list) {
        return list.get(getRandomIndex(list));
    }

    public E getObjectByWeight(Map<E, Integer> map) {
        Integer total_weight = 0;
        for (E group : map.keySet()) {
            total_weight += map.get(group);
        }
        E chosenObject = null;
        int random = RandomWizard.getRandomInt(total_weight);
        int index = 0;
        for (E group : map.keySet()) {
            index += map.get(group);
            if (random < index) {
                chosenObject = group;
                break;
            }
        }
        return chosenObject;
    }

    public Map<E, Integer> constructWeightMap(String property, Class<? extends E> CLASS) {
        return constructWeightMap(property, CLASS, null);
    }

    public static String getVarPartLast(String typeName) {
        int index = typeName.lastIndexOf('(');
        if (index == -1) {
            return "";
        }
        return typeName.substring(index);
    }
    @Deprecated
    public LinkedHashMap<String, E> constructStringWeightMapInversed(String property,
                                                                     Class<? extends E> CLASS) {
        LinkedHashMap<String, E> map = new LinkedHashMap<>();
        for (String string : ContainerUtils.open(property)) {
            // String value = "";
            // try {
            //     value = StringMaster.cropParenthesises(getVarPartLast(string));
            // } catch (Exception e) {
            //     system.ExceptionMaster.printStackTrace(e);
            // }
            // E object = CLASS.getEnumConstants() EnumFinder.get(CLASS, value);
            // string = (string).replace(StringMaster.wrapInParenthesis(value), "");
            // if (object != null) {
            //     map.put(string, object);
            // }
        }

        return map;
    }

    public static String removeVarPart(String value) {
        return value.replace(getVarPart(value), "");
    }
    public static String getVarPart(String typeName) {
        int index = typeName.indexOf('(');
        if (index == -1) {
            return "";
        }
        return typeName.substring(index);
    }

    public WeightMap<E> constructWeightMap(String property, Class<? extends E> CLASS, Object TYPE) {
        return constructWeightMap(property, CLASS, TYPE, false);
    }
    public Map<E, String> constructStringWeightMap(String property, Class<? extends E> CLASS) {
        LinkedHashMap<E, String> map = new LinkedHashMap<>();
        for (String string : ContainerUtils.open(property)) {
            // String value = "";
            // try {
            //     value = removeVarPart(string);
            // } catch (Exception e) {
            //     system.ExceptionMaster.printStackTrace(e);
            // }
            // E object = EnumFinder.get(CLASS, value);
            // string = StringMaster.cropParenthesises(getVarPartLast(string));
            // if (object != null) {
            //     map.put(object, string);
            // }
        }

        return map;
    }

    public WeightMap<E> constructWeightMap(String property, Class<? extends E> CLASS,
                                           Object TYPE, boolean inverse) {
        WeightMap<E> map = new WeightMap<>();
        if (inverse) {
            invertedMap = new LinkedHashMap<>();
        }
        String separator=ContainerUtils.getContainerSeparator();
        if (!property.contains(separator)) {
            separator= Strings.ALT_SEPARATOR;
        }
        for (String string : ContainerUtils.open(property, separator)) {
            Integer value = 0;
            try {
                value = StringMaster.getWeight(string, inverse);
            } catch (Exception e) {
                system.ExceptionMaster.printStackTrace(e);
            }
            if (value <= 0) {
                value = 1;
            }
            string = StringMaster.getWeightItem(string, inverse);
            E object = null;
            if (CLASS != null)
                if (CLASS == String.class)
                    object = (E) string;
                else {
                    // if (CLASS == ObjType.class) {
                    //     string = XML_Formatter.restoreXmlNodeName(string);
                    //     object = (E) DataManager.getType(string, TYPE);
                    //     if (object == null) {
                    //         object = (E) DataManager.findType(string, TYPE);
                    //     }
                    // } else {
                    //     object = EnumFinder.get(CLASS, string);
                    // }
                }
            map.put(object, value);
            if (inverse) {
                invertedMap.put(value, object);
            }
        }
        // if (object != null) //EMPTY option allowed!

        return map;
    }

    public LinkedHashMap<Integer, E> getInvertedMap() {
        return invertedMap;
    }

    public E getRandomEnumConst(Class<E> CLASS) {
        return CLASS.getEnumConstants()[getRandomInt(CLASS.getEnumConstants().length)];
    }

    public E getRandomArrayItem(E[] exits) {
        int i = getRandomInt(exits.length);
        return exits[i];
    }
}
