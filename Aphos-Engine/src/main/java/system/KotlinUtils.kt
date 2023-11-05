package system

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class KotlinUtils {
    //so we'd need to create such waiters in a map again?
    class AsyncWaiter {
        private val deferred = CompletableDeferred<Any>()

        suspend fun waitForInput(): Any {
            return deferred.await()
        }

        fun provideInput(input: Any) {
            deferred.complete(input)
        }
    }
    /*
    So what would we need to improve this?
    Go away from static
    Use some keys ...
     */

    companion object {

        var input: Any? = null;
        inline fun doWithInput(input: Any, toRun: Runnable) = runBlocking {
            KotlinUtils.input = input
            val asyncWaiter = AsyncWaiter()
            // Launch a coroutine to wait for the input
            val job = launch {
                toRun.run()
                val input = asyncWaiter.waitForInput()
                println("Input received: $input")
            }

            thread {
                // Simulate providing input from a separate thread
                asyncWaiter.provideInput(input)
            }

            job.join()  // Wait for the coroutine to finish and receive input
        }

        fun receiveInput(): Any? {
            return input
        }


    }

}