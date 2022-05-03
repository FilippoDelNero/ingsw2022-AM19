package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskHelperCard with the nextRoundOrderChosen
 */
public class ReplyHelperCardMessage extends Message {
    private final int nextRoundOrderChosen;

    public ReplyHelperCardMessage(String nickname, int nextRoundOrderChosen) {
        super(nickname, MessageType.PLAY_HELPER_CARD);
        this.nextRoundOrderChosen = nextRoundOrderChosen;
    }

    public int getNextRoundOrderChosen() {
        return nextRoundOrderChosen;
    }

    @Override
    public String toString() {
        return "ReplyHelperCardMessage{" +
                "nickname="+ getNickname() +
                "nextRoundOrderChosen=" + nextRoundOrderChosen +
                '}';
    }
}
