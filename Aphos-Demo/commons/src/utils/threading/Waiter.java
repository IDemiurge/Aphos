package utils.threading;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class Waiter {
    private static final long default_time_limit = 100000; // what if user is
    private static final long PING_PERIOD = 100;
//    private Date createdAt;
    Lock lock = new ReentrantLock();
    Condition waiting = lock.newCondition();
    Long timeLimit;
    long timeElapsed;
    // afk?! no, no...
    private Object input;
    private WaitMaster.WAIT_OPERATIONS operation;
    private boolean interrupted;
    private int n = 0;
    private Object expected;

    public Waiter(WaitMaster.WAIT_OPERATIONS operation) {
        this.operation = operation;
//        createdAt = Calendar.getInstance().getTime();
    }

    public Long getDefaultTimeLimitForOperation(WaitMaster.WAIT_OPERATIONS operation) {
        switch (operation) {
            case SELECTION:
                return default_time_limit;
            case SELECT_BF_OBJ:
                return default_time_limit;
            case CUSTOM_SELECT:
                return default_time_limit;

        }
        return null;
    }

    private void startPingingThread() {
        new Thread(new Runnable() {
            public void run() {
                Object monitor = new Object();
                while (true) {
                    if (timeElapsed >= timeLimit) {
                        break; //TODO remake to
                        // makeWakeupNeeded();
                        // lock.notifyAll();
                    }
                    synchronized (monitor) {
                        try {
                            monitor.wait(PING_PERIOD);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                lock.lock();
                waiting.signal();
                lock.unlock();
            }
        }, getId() + "'s pinging thread").start();
    }

    public synchronized Object startWaiting() {
        return startWaiting(null);
    }

    public synchronized Object startWaiting(Long timeLimit) {
        // LogMaster.log(LogMaster.WAITING_DEBUG, operation.name() + " WAITING STARTED : " + input
        //  + interrupted);
        this.timeLimit = timeLimit;

        n++;
        await();
        return input;
    }

    public void await() {
        input = null;
        interrupted = false;
        if (timeLimit != null) {
            Chronos.mark(getId());
            startPingingThread();
        }
        Predicate<Object> predicate =
                expected == null
                        ? input -> input == null
                        : input -> input != expected;

        while (predicate.test(input) && interrupted == false) {
            if (timeLimit != null) {
                if (timeElapsed >= timeLimit) {
                    break;
                } else {
                    timeElapsed = Chronos.getTimeElapsedForMark(getId());
                }
            }
            if (timeLimit != null)
                try {
                    wait(50);
                } catch (InterruptedException e) {
                    system.ExceptionMaster.printStackTrace(e);
                }
            else {
                lock.lock();
                try {
                    waiting.await();
                } catch (InterruptedException e1) {

                    e1.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }

        // LogMaster.log(LogMaster.WAITING_DEBUG, operation.name()
        //  + " WAIT LOOP EXITED WITH : " + input);
        lock.lock();
        waiting.signal();
        lock.unlock();
    }


    private String getId() {
        return "Waiter " + operation + "#" + n;
    }

    // ??? do i need that ???
    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {

        // LogMaster.log(LogMaster.WAITING_DEBUG, "INPUT RECEIVED: " + input);
        if (input == null) {
            interrupt();
            return;
        }

        lock.lock();
        this.input = input;

        waiting.signal();
        lock.unlock();
    }

    public void interrupt() {
        lock.lock();
        interrupted = true;
        waiting.signal();
        lock.unlock();

        // LogMaster.log(LogMaster.WAITING_DEBUG, "WAITER INTERRUPTED: "
        //  + operation.name());
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public void setExpected(Object expected) {
        this.expected = expected;
    }
}
