package it.polimi.ingsw.am19.Network.Message;

public class AskNumOfPlayersMessage extends Message {
    public AskNumOfPlayersMessage() {
        super("Server", MessageType.ASK_NUM_PLAYERS);
    }

    @Override
    public String toString() {
        return "AskNumOfPlayersMessage{}";
    }
}
