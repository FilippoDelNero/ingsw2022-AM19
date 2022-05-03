package it.polimi.ingsw.am19.Network.Message;

/**
 * Generic message. Send by server, the client only has to print the string 'message'
 * No reply expected
 */
public class GenericMessage extends Message{
    private final String message;

    public GenericMessage(String message) {
        super(null, MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
