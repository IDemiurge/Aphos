package adapter.engine;

import api.generic.engine.EngineEvents;
import api.generic.engine.EngineEvent;
import api.generic.engine.EngineEventType;
import framework.callback.Callbacks;
import framework.callback.ICallbacks;
import framework.datatypes.DequeImpl;

/**
 * Created by Alexander on 2/7/2024
 */
public class EngineEventHandler {
    //not that we'll want other impl's - it's just a queue
    EngineEvents events;
    //autowired
    ICallbacks<EventCallback> callbacks = new Callbacks<>(new GdxEventExecutor(),EventCallback.class );

    private DequeImpl<EngineEvent> taken;
    private boolean groupReady = false;
    private boolean actionInProgress = false;

    public enum EventCallback {
        action_anim_start,
        ;

    }

    //continuously
    public void processEvents() {
        //consider that some triggered stuff will be separate?

        if (!actionInProgress) {
            //normal processing of this and that ?
            return;
        }
        if (events.hasNext()) {
            taken.clear();
            EngineEvent event = null;
            while ((event = events.getNext()) != null) {
                taken.add(event);
                events.remove(event);
                if (event.getType() == EngineEventType.action_started) {
                    actionInProgress = true;
                } else if (event.getType() == EngineEventType.action_finished) {
                    groupReady = true;
                    break;
                }
            }
        }
        if (groupReady) {
            processEventGroup(taken);
            groupReady = false;
        }
        // connector.getEvents(); wait until group is formed
        //is there any parallelism here at all? No, which is good!
        //don't try to process all of them? Grouping? Priority?
    }

    public void processEventGroup(DequeImpl<EngineEvent> events) {
        //can even have pre- and after- phases for each event

    }

    //should we have another layer of graphic events?
    //register callbacks for this for sure! But how to handle their input?
    public void processEvent(EngineEventType type, Object data) {
        //we can start by making the simplest impl possible
        EventCallback key = getCallbackKey(type);
        //this is our translate layer
        callbacks.invoke(key, data);
        //at least callbacks should not have return types!
    }

    private EventCallback getCallbackKey(EngineEventType type) {

        return null;
    }

}
