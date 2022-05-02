package it.polimi.ingsw.am19.Network.Message;

public class AskExpertMatchMessage extends Message {
    public AskExpertMatchMessage() {
        super("Server", MessageType.ASK_EXPERT_MATCH);
    }

    @Override
    public String toString() {
        return "AskExpertMatchMessage{}";
    }
}
