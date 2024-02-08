package adapter.engine;

import adapter.engine.EngineEventHandler.EventCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Alexander on 2/8/2024
 */
public class GdxEventDictionary {

    private Consumer FUNC_ACTION_ANIM_START = data -> func_action_anim_start(data);

    private void func_action_anim_start(Object data) {
    }

    public Map<EventCallback, Consumer> createDictionary() {
        Map<EventCallback, Consumer> dictionary = null;// = new ImmutableMap<>();
        /*

         */
        dictionary.put(EventCallback.action_anim_start, FUNC_ACTION_ANIM_START);

        return dictionary;
    }
}
