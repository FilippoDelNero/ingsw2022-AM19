package it.polimi.ingsw.am19.Network.Message;

public class AskWhereMoveMessage extends Message {
    public AskWhereMoveMessage() {
        super("server", MessageType.WHERE_MOVE);
    }

    @Override
    public String toString() {
        return "AskWhereMoveMessage{}";
    }
}
