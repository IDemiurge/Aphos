package apps.server;

import apps.SHARED.NetworkMessage;

/*
Actionable input - segment of input package
from user - can there be any 'type'?

i: We should pre-fetch targeting filter for each available action!
 */
public enum ServerInputType implements NetworkMessage {

    PhaseDone,
    Action, //unit action, deploy, feat/omen/strategy action
    Termination,


}
