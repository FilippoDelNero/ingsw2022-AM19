package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;

import java.util.List;

public class AskPlayCharacterCardMessage extends Message {
    private final List<AbstractCharacterCard> availableCharacterCards;

    public AskPlayCharacterCardMessage(List<AbstractCharacterCard> availableCharacterCards) {
        super("server", MessageType.ASK_CHARACTER_CARD);
        this.availableCharacterCards = availableCharacterCards;
    }

    public List<AbstractCharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    @Override
    public String toString() {
        return "Available Character Card"+ availableCharacterCards;
    }
}
