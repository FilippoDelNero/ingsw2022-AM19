package it.polimi.ingsw.am19.Network.Message;

/**
 * Ping message to keep track for client's connection
 */
public class PingMessage extends Message{

    public PingMessage() {
        super(null, MessageType.PING_MESSAGE);
    }
}
