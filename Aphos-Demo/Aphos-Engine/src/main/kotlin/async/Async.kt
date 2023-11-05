package async

import system.KotlinUtils.AsyncWaiter
import java.util.function.Consumer

/**
 * Created by Alexander on 11/3/2023
 *
 *
 * via kotlin? What's the advantage?
 *
 *
 * Let's first define a certain kind of API This could be a re-router also
 */
object Async {
    var waiterMap: MutableMap<Any, AsyncWaiter> = HashMap()
    @JvmStatic
    fun await(key: Any, consumer: Consumer<Any>): Any { //, Runnable onTimeOut
        //timeout?
        val waiter = AsyncWaiter()
        //we can add some timeout info and runnable - and check waiters in the map continuously
        waiterMap[key] = waiter
        return waiter.await(consumer)
    }

    fun receive(key: Any, input: Any?) {
        val waiter = waiterMap[key]
        waiter!!.provideInput(input!!)
    }
}