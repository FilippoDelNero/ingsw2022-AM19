package it.polimi.ingsw.am19.Network.Message;

public class AskResumeMatchMessage extends Message {
    public AskResumeMatchMessage() {
        super("server", MessageType.ASK_RESUME_MATCH);
    }

    @Override
    public String toString() {
        return "AskResumeMatchMessage{}";
    }
}
