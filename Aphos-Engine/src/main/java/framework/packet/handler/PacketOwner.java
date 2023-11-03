package framework.packet.handler;

import framework.packet.data.EventPacket;

import static framework.packet.manager.PacketManager.pm;

/**
 * Created by Alexander on 11/2/2023
 * <p>
 * impl for each type
 */
public abstract class PacketOwner<T extends EventPacket> {

    protected T packet;

    public PacketOwner(T packet) {
        this.packet = packet;
    }

    public void step() {
        pm().doFallible(this, true);
        pm().doEssential(this);
        pm().doFallible(this, false);
    }

    public T get() {
        return packet;
    }

    public void toClient() {

    }

}
