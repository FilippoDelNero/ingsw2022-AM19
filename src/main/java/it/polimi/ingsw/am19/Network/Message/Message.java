package it.polimi.ingsw.am19.Network.Message;

import java.io.Serializable;

/**
 * Abstract class for a message.
 * Every message have to extend this class
 */
public abstract class Message implements Serializable {
    private final String nickname;
    private final MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "nickname='" + nickname + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
