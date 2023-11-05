package apps.client.data;

import apps.SHARED.DTO;
import apps.server.ServerInputType;

/**
 * Created by Alexander on 11/3/2023
 *
 * the input data coming from client
 * to be parsed into json
 *
 * use builder to construct it? Or else how?
 */
public class ClientOutput extends DTO<ServerInputType> {
    public ClientOutput(ServerInputType type) {
        super(type);
    }
}
