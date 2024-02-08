package framework.callback;

import java.util.function.Consumer;

public interface Executor<T> {

    Consumer<T> getExecuteFunction(T enumConst);
}
