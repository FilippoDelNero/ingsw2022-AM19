package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;
import java.util.List;

/**
 * Message for ask to choose a cloud.
 * Contains an array with the num of the cloud still available. It can be used for print a custom message to the client.
 */
public class AskCloudMessage extends Message {
    private final List<Integer> cloudAvailable;

    /**
     * class constructor
     * @param cloudAvailable a list containing the indexes of the clouds the player can still select in their turn
     */
    public AskCloudMessage( List<Integer> cloudAvailable) {
        super("server", MessageType.CHOOSE_CLOUD);
        this.cloudAvailable = cloudAvailable;
    }

    /**
     * getter for the list of available clouds
     * @return the list containing the indexes of the clouds still available to be selected by the player
     */
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
