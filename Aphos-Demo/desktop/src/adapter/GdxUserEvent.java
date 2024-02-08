package adapter;

import api.generic.client.UserEvent;
import api.generic.client.UserEventType;

public class GdxUserEvent implements UserEvent {
    private UserEventType type;
    private Object data;

    public GdxUserEvent(UserEventType type, Object data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    public UserEventType getType() {
        return type;
    }


}
