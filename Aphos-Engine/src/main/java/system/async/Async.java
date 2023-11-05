package system.async;

import kotlin.coroutines.Continuation;
import system.KotlinUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 11/3/2023
 * <p>
 * via kotlin? What's the advantage?
 * <p>
 * Let's first define a certain kind of API This could be a re-router also
 */
public class Async {
    static Map<Object, KotlinUtils.AsyncWaiter> waiterMap = new HashMap<>();

    public static Object await(Object key) { //, Runnable onTimeOut
        //timeout?
        KotlinUtils.AsyncWaiter waiter = new KotlinUtils.AsyncWaiter();
        //we can add some timeout info and runnable - and check waiters in the map continuously
        waiterMap.put(key, waiter);
        Continuation<? super Object> c=null ; // ??????
        Object o = waiter.waitForInput(c);
        return o;
    }

    public static void receive(Object key, Object input) {
        KotlinUtils.AsyncWaiter waiter = waiterMap.get(key);
        waiter.provideInput(input);
    }
}
