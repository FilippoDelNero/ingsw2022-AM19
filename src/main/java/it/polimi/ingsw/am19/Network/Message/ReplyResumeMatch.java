package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskResumeMatch if we have to resume the last match
 */
public class ReplyResumeMatch extends Message {
    private final boolean resuming;

    public ReplyResumeMatch(String nickname, boolean resuming) {
        super(nickname, MessageType.RESUME_MATCH);
        this.resuming = resuming;
    }

    public boolean isResuming() {
        return resuming;
    }

    @Override
    public String toString() {
        return "ReplyResumeMatch{" +
                "nickname= "+ getNickname() +
                "resuming=" + resuming +
                '}';
    }
}
