package api.generic.engine;

import framework.datatypes.DequeImpl;

public interface EngineEvents {

    void addEvent(EngineEvent event);

    boolean hasNext();

    void remove(EngineEvent event);

    EngineEvent getNext();
}
