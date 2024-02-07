package framework.architecture.packet.handler;

import framework.architecture.packet.data.ActionPacket;

/**
 * Created by Alexander on 11/2/2023
 */
public class ActionPacketOwner extends PacketOwner<ActionPacket> {

    public static final String PHASE_1_Targeting  = "Targeting";
    public static final String PHASE_2_Initiation = "Initiation";
    public static final String PHASE_3_Execution = "Execution";
    public static final String PHASE_4_Responses = "Responses ";
    public static final String PHASE_5_Aftermath = "Aftermath ";

    public ActionPacketOwner(ActionPacket packet) {
        super(packet);
    }

    public void phase(String phase){
        switch(phase){
            case PHASE_1_Targeting -> phase_targeting();
        }
    }

    public void phase_targeting(){
        //who else is interested in this packet?


        //aggregate and flush data?
        toClient();
        // receiver(TYPE, callback);

    }


}
