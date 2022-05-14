package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;
import java.util.List;

/**
 * Message for ask to choose a cloud.
 * Contains an array with the num of the cloud still available. It can be used for print a custom message to the client.
 */
public class AskCloudMessage extends Message {
    private final List<Integer> cloudAvailable;

    public AskCloudMessage( List<Integer> cloudAvailable) {
        super("server", MessageType.CHOOSE_CLOUD);
        this.cloudAvailable = cloudAvailable;
    }

    public List<Integer> getCloudAvailable() {
        return cloudAvailable;
    }

    @Override
    public String toString() {
        return "AskCloudMessage{" +
                "cloudAvailable=" + cloudAvailable +
                '}';
    }
}
