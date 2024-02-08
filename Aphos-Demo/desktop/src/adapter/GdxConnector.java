package adapter;

import api.generic.engine.EngineEvents;
import api.generic.engine.EngineEvent;
import framework.datatypes.DequeImpl;

/**
 * Created by Alexander on 2/7/2024
 */
public class GdxConnector implements EngineEvents {

    private DequeImpl<EngineEvent> events= new DequeImpl<>();

    @Override
    public void addEvent(EngineEvent event) {
        events.add(event);
    }

    @Override
    public boolean hasNext() {
        return !events.isEmpty();
    }

    @Override
    public void remove(EngineEvent event) {
         events.pop();
    }

    @Override
    public EngineEvent getNext() {
        return events.poll();
    }
}
