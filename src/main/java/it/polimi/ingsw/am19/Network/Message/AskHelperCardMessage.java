package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;

import java.util.Arrays;
import java.util.List;

/**
 * Message for ask an helperCard to use in the planning phase.
 * Contains an array with the nextRoundOrder of the card still available for the current player of this phase
 */
public class AskHelperCardMessage extends Message {
    private final List<HelperCard> playableHelperCard;

    public AskHelperCardMessage(List<HelperCard> playableHelperCard) {
        super("server", MessageType.PLAYABLE_HELPER_CARD);
        this.playableHelperCard = playableHelperCard;
    }

    public List<HelperCard> getPlayableHelperCard() {
        return playableHelperCard;
    }

    @Override
    public String toString() {
        return "AskHelperCardMessage{" +
                "playableHelperCard=" + playableHelperCard +
                '}';
    }
}
