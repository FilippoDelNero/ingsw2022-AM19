package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask the num of player of a new match
 */
public class AskNumOfPlayersMessage extends Message {
    public AskNumOfPlayersMessage() {
        super("Server", MessageType.ASK_NUM_PLAYERS);
    }

    @Override
    public String toString() {
        return "AskNumOfPlayersMessage{}";
    }
}
