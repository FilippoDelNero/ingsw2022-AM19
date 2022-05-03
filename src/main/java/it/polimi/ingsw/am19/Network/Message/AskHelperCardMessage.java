package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;

/**
 * Message for ask an helperCard to use in the planning phase.
 * Contains an array with the nextRoundOrder of the card still available for the current player of this phase
 */
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
