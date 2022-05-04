package it.polimi.ingsw.am19.Network.Message;

/**
 * Error message. Send by server, the client only has to print the string 'error'
 * No reply expected
 */
public class ErrorMessage extends Message{
    private final String error;

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR_MESSAGE);
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
