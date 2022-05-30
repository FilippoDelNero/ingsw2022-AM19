package it.polimi.ingsw.am19.Network.Message;

/**
 * Generic message. Send by server, the client only has to print the string 'message'
 * No reply expected
 */
public class GenericMessage extends Message{
    private final String message;

    public GenericMessage(String message, MessageType type) {
        super(null, type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
