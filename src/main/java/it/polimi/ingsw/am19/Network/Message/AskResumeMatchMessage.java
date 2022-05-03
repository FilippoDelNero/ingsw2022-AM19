package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask if we have to resume an old match (if there's one)
 * The reply have to be a 'RESUME_MATCH' if it's true, or 'CREATE_MATCH' if we want a new match
 */
public class AskResumeMatchMessage extends Message {
    public AskResumeMatchMessage() {
        super("server", MessageType.ASK_RESUME_MATCH);
    }

    @Override
    public String toString() {
        return "AskResumeMatchMessage{}";
    }
}
