package apps.server.connect;

import apps.SHARED.NetworkMessage;
import com.google.gson.Gson;

/**
 * Created by Alexander on 11/4/2023
 */
public class Connection {
    private static int i = 0;

    private Object generateKey(NetworkMessage type) {
        return type.toString()+ i++;
    }
    //Discuss with GPT if this design is good
    //design via Coroutines?
    //So we could have multiple such threads?
    public void received(String s) {

        Gson gson = new Gson();
        NetworkData data = gson.fromJson(s, NetworkData.class);
        Object key = generateKey(data.type);
        try {
            queueResponse(data.type, key);
            process(data.type, data.body);
        } catch (Exception e) {
            system.ExceptionMaster.printStackTrace(e);
        } finally {
            sendResponse(data.type, key);
        }
    }

    private void queueResponse(NetworkMessage type, Object key) {
    }


    private void sendResponse(NetworkMessage type, Object key) {
        queueToSend(getResponse(type, key));
    }

    private void queueToSend(Object response) {
        //ensure that same order is preserved! No sending before previous requests were responded to!
    }

    private Object getResponse(NetworkMessage type, Object key) {

        return null;
    }

    /*
    In kotlin, we had this    is TYPE -> {}
     */
    private void process(NetworkMessage type, Object body) {
        //do we use await() here also?
        // getProcessor(type).process(body);
    }
}
