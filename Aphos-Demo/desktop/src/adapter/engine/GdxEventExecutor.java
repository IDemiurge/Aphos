package adapter.engine;

import adapter.engine.EngineEventHandler.EventCallback;
import framework.callback.Executor;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Alexander on 2/8/2024
 */
public class GdxEventExecutor implements Executor<EventCallback> {
    Map<EventCallback, Consumer> dictionary;

    public GdxEventExecutor() {
        dictionary = new GdxEventDictionary().createDictionary();

    }

    @Override
    public Consumer getExecuteFunction(EventCallback enumConst) {
        //custom cases
        if (dictionary.containsKey(enumConst))
            return dictionary.get(enumConst);
        else
            return data ->
                    execute(enumConst, data);
    }

    private void execute(EventCallback enumConst, Object data) {
        //switch
    }
}
