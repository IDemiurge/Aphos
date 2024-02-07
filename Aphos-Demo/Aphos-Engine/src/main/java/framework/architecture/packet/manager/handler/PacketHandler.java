package framework.architecture.packet.manager.handler;

import framework.architecture.packet.data.EventPacket;

/**
 * Created by Alexander on 11/2/2023
 *
 * just add them as a list of 'non-blocking receivers' on the backend side
 *
 * they will in turn send stuff to client? Yeah, we want to centralize this
 * Aggregate data from PM's to send a single ClientData object that is ready to animate and display EVERYTHING
 * up until the next player/ai input phase (so we must pre-calculate all triggers and their results)
 *
 *
 */
public abstract class PacketHandler {
    public abstract void doFallible(EventPacket t);

    public abstract void doEssential(EventPacket t);
    //should they know about each other ?
    //pub sub ?
    //'pass packet to others' - nah, better check with every agent I'd say, we don't have such a vast system
}
