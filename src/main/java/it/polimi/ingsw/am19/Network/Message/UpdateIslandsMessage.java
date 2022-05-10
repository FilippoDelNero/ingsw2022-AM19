package it.polimi.ingsw.am19.Network.Message;


import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;

import java.util.ArrayList;
import java.util.List;

/**
 * message used to update the islands
 */
public class UpdateIslandsMessage extends Message{
    /** list of the reducedIsland we need to send */
    private final List<ReducedIsland> list;

    /**
     * a constructor used when multiple islands need to be sent
     * @param l a list of reducedIslands
     */
    public UpdateIslandsMessage(List<ReducedIsland> l) {
        super("server", MessageType.UPDATE_ISLANDS);
        list = l;
    }

    /**
     * a constructor used a single island needs to be sent
     * @param ri the reducedIsland to send
     */
    public UpdateIslandsMessage(ReducedIsland ri) {
        super("server", MessageType.UPDATE_GAMEBOARDS);
        list = new ArrayList<>();
        list.add(ri);
    }

    /**
     * getter for the list of reducedIsland
     * @return the list of reducedIsland
     */
    public List<ReducedIsland> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "UpdateIslandsMessage {" +
                "list =" + list +
                '}';
    }
}
