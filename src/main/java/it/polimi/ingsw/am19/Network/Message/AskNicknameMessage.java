package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask a new nickname of a new match
 */
public class AskNicknameMessage extends Message {
    public AskNicknameMessage(MessageType messageType) {
        super("server ", MessageType.ASK_NICKNAME);
    }

    @Override
    public String toString() {
        return "AskNicknameMessage{}";
    }
}
