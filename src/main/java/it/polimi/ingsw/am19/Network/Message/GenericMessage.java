package it.polimi.ingsw.am19.Network.Message;

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
