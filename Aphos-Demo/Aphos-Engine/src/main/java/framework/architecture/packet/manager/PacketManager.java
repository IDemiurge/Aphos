package framework.architecture.packet.manager;

import framework.architecture.packet.data.EventPacket;
import framework.architecture.packet.handler.PacketOwner;
import framework.architecture.packet.manager.handler.PacketHandler;

import java.util.List;

/**
 * Created by Alexander on 11/2/2023
 */
public class PacketManager {

    private static PacketManager instance;
    private List<PacketHandler> handlers;

    public PacketManager(List<PacketHandler> handlers) {
        this.handlers = handlers;
        instance = this;
    }

    public <T extends EventPacket> void doFallible(PacketOwner<T> owner, boolean before) {
        handlers.forEach(packetHandler ->
        {
            try {
                // if (before)
                //     packetHandler.doFallibleBefore(owner.get());
                // else
                //     packetHandler.doFallibleAfter(owner.get());
            } catch (Exception e) {
                throw new RuntimeException(e); //TODO how to handle well?
            }
        });
        //add exception handling

    }

    public <T extends EventPacket> void doEssential(PacketOwner<T> owner) {
        handlers.forEach(packetHandler -> packetHandler.doEssential(owner.get()));
    }

    public static PacketManager pm() {
        return instance;
    }
}
