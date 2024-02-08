package api.old.client;

import api.old.server.ServerInputType;

/**
 * Created by Alexander on 11/3/2023
 */
public class ClientDataBuilder {

    private static final String SOURCE_ID = "SOURCE_ID"; //these should be in COMMONS!
    private static final String ACTION_ID = "ACTION_ID";
    private static final String TARGET_DATA = "TARGET_DATA";

    public ClientOutput getActionData(String sourceId, String actionId, Object targetingData){
        ClientOutput data= new ClientOutput(ServerInputType.Action);
        data.put(SOURCE_ID, sourceId);
        data.put(ACTION_ID, actionId);
        data.put(TARGET_DATA, format(targetingData));
        return data;
    }

    private Object format(Object targetingData) {
        //list?
        return targetingData.toString();
    }
}
