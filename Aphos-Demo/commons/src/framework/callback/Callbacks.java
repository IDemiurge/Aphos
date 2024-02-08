package framework.callback;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Alexander on 2/7/2024
 */
public  class Callbacks<T> implements ICallbacks<T> {
    private Map<T, Consumer<Object>> dictionary;

    private final Executor executor;

    public Callbacks(Executor executor, Class<T> enumClass) {
        this.executor = executor;
        createDictionary(enumClass);
    }

    private void createDictionary(Class<T> enumClass) {
        dictionary = new HashMap<>();
        for (T enumConstant : enumClass.getEnumConstants()) {
            dictionary.put(enumConstant, executor.getExecuteFunction(enumConstant));
        }
    }

    @Override
    public void invoke(T key, Object data) {
        get(key).accept(data);
    }

    private Consumer<Object> get(T key) {
        Consumer<Object> callback = dictionary.get(key);
        if (callback == null) {
            callback = (input) -> {}  ;
        }
        return callback;
    }
}
