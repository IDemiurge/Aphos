package framework.packet.data;

import framework.packet.Packet;
import datatypes.LinkedStringMap;
import system.log.result.LoggableResult;

import java.util.Map;

/**
 * Created by Alexander on 11/2/2023
 *
 * Inspired by Cain
 * The object that ends up passed to client? I'd say that we should pass only 'actionable' data to the client, and thus have a
 * middle-man for that who filters and transforms the final Packet into ClientData!
 *
 * Encapsulate
 * Fire events and pass itself as main arg
 * Handlers modify the packet and may pass it to other handlers
 * For each modification, there should be history
 *
 * Only logic-data, all visual/audio/.. data should be created based on the packet and passed separately
 *
 * Nested with RESULT objects each with their own MAP - for roll, ...
 *
 * >> It is the singular source of truth - we don't have to hold state ANYWHERE else or check ANYTHING else, only via packet.
 */

public abstract class EventPacket {

    Map<String, LoggableResult> results = new LinkedStringMap<>();

    public abstract Packet getType();

}














