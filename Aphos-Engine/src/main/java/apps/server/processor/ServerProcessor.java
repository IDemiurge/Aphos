package apps.server.processor;

/**
 * Created by Alexander on 11/3/2023
 *
 * what part of this is (a)synchronous?
 * Well frankly, Logic server instance can be 100% blocking and synchronous
 * 2 threads - network and logic
 * There might still be cases when we need to keep network separate and open while logic works
 * >> We might send preliminary packets to client while logic continues to calculate ?
 *
 */
public class ServerProcessor {

    public void process(String json) {

    }

}
