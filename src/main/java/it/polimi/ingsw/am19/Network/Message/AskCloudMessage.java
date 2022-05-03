package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;

/**
 * Message for ask to choose a cloud.
 * Contains an array with the num of the cloud still available. It can be used for print a custom message to the client.
 */
public class AskCloudMessage extends Message {
    private final int [] cloudAvailable;

    public AskCloudMessage(int[] cloudAvailable) {
        super("server", MessageType.CHOOSE_CLOUD);
        this.cloudAvailable = cloudAvailable;
    }

    public int[] getCloudAvailable() {
        return cloudAvailable;
    }

    @Override
    public String toString() {
        return "AskCloudMessage{" +
                "cloudAvailable=" + Arrays.toString(cloudAvailable) +
                '}';
    }
}
