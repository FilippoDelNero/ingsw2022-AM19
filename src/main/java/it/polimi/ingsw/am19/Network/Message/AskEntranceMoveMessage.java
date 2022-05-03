package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask what and where move a student from the entrance in the first part of actionPhase
 */
public class AskEntranceMoveMessage extends Message {
    public AskEntranceMoveMessage() {
        super("server", MessageType.ENTRANCE_MOVE);
    }

    @Override
    public String toString() {
        return "AskEntranceMoveMessage{}";
    }
}
