package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask if the first player want to play an expert match.
 */
public class AskExpertMatchMessage extends Message {
    /**
     * class constructor
     */
    public AskExpertMatchMessage() {
        super("Server", MessageType.ASK_EXPERT_MATCH);
    }

    @Override
    public String toString() {
        return "AskExpertMatchMessage{}";
    }
}
