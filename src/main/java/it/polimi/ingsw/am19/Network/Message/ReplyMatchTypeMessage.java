package it.polimi.ingsw.am19.Network.Message;

public class ReplyMatchTypeMessage extends Message {
    private final boolean isExpertMatch;

    public ReplyMatchTypeMessage(String nickname, boolean isExpertMatch) {
        super(nickname, MessageType.MATCH_TYPE);
        this.isExpertMatch = isExpertMatch;
    }

    public boolean isExpertMatch() {
        return isExpertMatch;
    }

    @Override
    public String toString() {
        return "ReplyMatchTypeMessage{" +
                "nickname: "+ getNickname() +
                "isExpertMatch=" + isExpertMatch +
                '}';
    }
}
