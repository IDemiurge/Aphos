package module.battle.handler.meta;

import system.log.result.LoggableResult;
import utils.collection.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 8/25/2023
 *
 * Sometimes we're using those result objects to perform further actions/calc
 *
 */
public class BattleStatistics {
    Map<Class, List<LoggableResult>> map= new HashMap<>();

    public LoggableResult getLast(Class clazz){
        return ListUtils.getLast(map.get(clazz));
    }

    public void add(LoggableResult result) {
        List<LoggableResult> list = map.get(result.getClass());
        if (list == null) {
            map.put(result.getClass(), list = new ArrayList<>());
        }
        list.add(result);
    }

    public void report(){

    }
}
