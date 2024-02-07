package framework.architecture.packet.data;

import elements.exec.EntityRef;
import framework.architecture.packet.Packet;

/**
 * Created by Alexander on 11/2/2023
 *
 *
 * interface that we'd use:
 * Event has Packet
 *
 * we are handling this packet over some length
 *
 * I think it should not contain any functional logic after all
 */
public class ActionPacket extends EventPacket {
    EntityRef ref;
    String phase;

    @Override
    public Packet getType() {
        return null;
    }
}
