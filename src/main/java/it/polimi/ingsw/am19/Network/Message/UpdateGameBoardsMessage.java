package it.polimi.ingsw.am19.Network.Message;


import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * message used to update the player's gameboard
 */
public class UpdateGameBoardsMessage extends Message {
    /** list of the reducedGameBoard we need to send */
    private final List<ReducedGameBoard> list;

    /**
     * a constructor used when multiple gameboards need to be sent
     * @param l a list of reducedGameBoard
     */
    public UpdateGameBoardsMessage(List<ReducedGameBoard> l) {
        super("server", MessageType.UPDATE_GAMEBOARDS);
        list = l;
    }

    /**
     * a constructor used a single gameboard needs to be sent
     * @param rgb the reducedGameBoard to send
     */
    public UpdateGameBoardsMessage(ReducedGameBoard rgb) {
        super("server", MessageType.UPDATE_GAMEBOARDS);
        list = new ArrayList<>();
        list.add(rgb);
    }

    /**
     * getter for the list of reducedGameBoard
     * @return the list of reducedGameBoard
     */
    public List<ReducedGameBoard> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "UpdateGameBoardsMessage {" +
                "list =" + list +
                '}';
    }
}
