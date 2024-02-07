package framework.data.consts;

import elements.content.battle.stats.unit.UnitParam;
import utils.collection.MapUtils;

import java.util.Map;


/**
 * Created by Alexander on 10/25/2023
 */
public class StatUtils {

    private static Map<String, UnitParam> paramMap = MapUtils.mapReverse(v->v.getName(), UnitParam.values());

    public static UnitParam getParam(String s) {
        return paramMap.get(s);
    }

    public static UnitParam getRetainParam(UnitParam param) {
        return getParam(getRetain(param));
    }
    public static String getRetain(UnitParam param) {
        return param.getName() + "_retain";
    }

    public static UnitParam getBrokenParam(UnitParam param) {
        return getParam(getBroken(param));
    }
    public static String getBroken(UnitParam param) {
        return param.getName() + "_broken";
    }

    public static UnitParam getTotalParam(String name) {
        return getParam(getTotal(name));
    }
    public static String getTotal(String s) {
        return s + "_total";
    }

    public static UnitParam getSavedParam(String name) {
        return getParam(getSaved(name));
    }
    private static String getSaved(String name) {
        return name + "_saved";
    }
}
