package api.generic.engine;


public interface EngineEvent {
    Object getData();
    EngineEventType getType();
}
