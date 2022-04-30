package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;

public class AskHelperCardMessage extends Message {
    private final int [] playableHelperCard;

    public AskHelperCardMessage(int[] playableHelperCard) {
        super("server", MessageType.PLAYABLE_HELPER_CARD);
        this.playableHelperCard = playableHelperCard;
    }

    public int[] getPlayableHelperCard() {
        return playableHelperCard;
    }

    @Override
    public String toString() {
        return "AskHelperCardMessage{" +
                "playableHelperCard=" + Arrays.toString(playableHelperCard) +
                '}';
    }
}
