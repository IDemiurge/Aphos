package framework.connector;

import api.generic.client.InputReceiver;
import api.generic.client.UserEvent;

/**
 * Created by Alexander on 2/7/2024
 * shouldn't there only be ONE receiver?
 */
public class ReceiverImpl implements InputReceiver {

    @Override
    public void receive(UserEvent event) {
        //at what point do we make a QUEUE?
        switch (event.getType()) {
            case ActionConfirmed:

                break;
            case UnitGroupFinished:
                break;
        }
    }
}
