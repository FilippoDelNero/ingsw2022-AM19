package it.polimi.ingsw.am19.Network.Message;

public class AskNicknameMessage extends Message {
    public AskNicknameMessage(MessageType messageType) {
        super("server ", MessageType.ASK_NICKNAME);
    }

    @Override
    public String toString() {
        return "AskNicknameMessage{}";
    }
}
