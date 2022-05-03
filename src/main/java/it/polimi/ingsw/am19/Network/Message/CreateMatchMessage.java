package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskResumeMatch if we have to create a new match
 */
public class CreateMatchMessage extends Message {
    public CreateMatchMessage(String nickname) {
        super(nickname, MessageType.NEW_MATCH);
    }

    @Override
    public String toString() {
        return "CreateMatchMessage{" +
                "nickname: "+ getNickname() +
                "}";
    }
}
