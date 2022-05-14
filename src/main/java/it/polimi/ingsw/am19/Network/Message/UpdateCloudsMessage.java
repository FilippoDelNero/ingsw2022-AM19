package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Map;

/**
 * message used to update the clouds
 */
public class UpdateCloudsMessage extends Message {
    /** a list of the maps inside the clouds */
    private final List<Map<PieceColor, Integer>> clouds;

    /**
     * constructor
     * @param list the list of maps found inside the clouds
     */
    public UpdateCloudsMessage(List<Map<PieceColor, Integer>> list) {
        super("server", MessageType.UPDATE_CLOUDS);
        this.clouds = list;
    }

    /**
     * getter for the list of maps of the clouds
     * @return the list of maps of the clouds
     */
    public List<Map<PieceColor, Integer>> getClouds() {
        return clouds;
    }

    @Override
    public String toString() {
        return "UpdateCloudMessage {" +
                "clouds =" + clouds +
                '}';
    }
}
