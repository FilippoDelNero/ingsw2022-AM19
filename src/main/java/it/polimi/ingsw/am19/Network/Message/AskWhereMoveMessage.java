package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask where move a student from the entrance in the first part of actionPhase
 */
public class AskWhereMoveMessage extends Message {
    public AskWhereMoveMessage() {
        super("server", MessageType.WHERE_MOVE);
    }

    @Override
    public String toString() {
        return "AskWhereMoveMessage{}";
    }
}
