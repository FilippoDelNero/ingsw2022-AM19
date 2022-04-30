package it.polimi.ingsw.am19.Network.Message;

public class ErrorMessage extends Message{
    private final String error;

    public ErrorMessage(String nickname, String error) {
        super(nickname, MessageType.ERROR_MESSAGE);
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "nickname: "+ getNickname() +
                ", error='" + error + '\'' +
                '}';
    }
}
