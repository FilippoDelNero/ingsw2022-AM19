package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;

/**
 * Reply to AskHelperCard with the nextRoundOrderChosen
 */
public class ReplyHelperCardMessage extends Message {
    private final HelperCard helperCard;

    public ReplyHelperCardMessage(String nickname, HelperCard helperCard) {
        super(nickname, MessageType.PLAY_HELPER_CARD);
        this.helperCard = helperCard;
    }

    public HelperCard getHelperCard() {
        return helperCard;
    }



    @Override
    public String toString() {
        return "ReplyHelperCardMessage{" +
                "nickname="+ getNickname() +
                "nextRoundOrderChosen=" + helperCard +
                '}';
    }
}
