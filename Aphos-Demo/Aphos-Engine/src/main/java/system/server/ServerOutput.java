package system.server;

import api.dto.DTO;
import api.client.ClientInputType;

/**
 * Created by Alexander on 11/3/2023
 *
 * entire packet sent after each Client's input
 * (can there be multiple?)
 * supposed to render
 *
 * break it down into the DTO tree
 *
 * Logic binds data to some 'components' of its own - and we must do the same on client side, but maybe a
 * component can have more underlying ones
 */
public class ServerOutput extends DTO<ClientInputType> {
    public ServerOutput(ClientInputType type) {
        super(type);
    }
    /*
    This is what the server sends
     */
}
