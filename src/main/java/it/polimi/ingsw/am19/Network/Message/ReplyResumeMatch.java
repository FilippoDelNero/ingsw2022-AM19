package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskResumeMatch if we have to resume the last match
 */
public class ReplyResumeMatch extends Message {

    public ReplyResumeMatch(String nickname) {
        super(nickname, MessageType.RESUME_MATCH);
    }


    @Override
    public String toString() {
        return "ReplyResumeMatch{" +
                "nickname= " + getNickname() +
                '}';
    }
}
