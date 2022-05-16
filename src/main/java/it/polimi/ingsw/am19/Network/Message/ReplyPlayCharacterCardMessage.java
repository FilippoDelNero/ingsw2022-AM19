package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;

/**
 * Message sent by a player of an expert match when he wants to use the effect of a CharacterCard
 * Contains the Card to use
 */
public class ReplyPlayCharacterCardMessage extends Message {
    private final AbstractCharacterCard cardToUse;

    public ReplyPlayCharacterCardMessage(String nickname, AbstractCharacterCard cardToUse) {
        super(nickname, MessageType.PLAY_CHARACTER_CARD);
        this.cardToUse = cardToUse;
    }

    public AbstractCharacterCard getCardToUse() {
        return cardToUse;
    }

    @Override
    public String toString() {
        return "ReplyPlayCharacterCardMessage{" +
                "nickname=" + getNickname() +
                "cardToUse=" + cardToUse +
                '}';
    }
}