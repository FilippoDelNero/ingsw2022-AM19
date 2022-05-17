package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.CharacterCards.Character;

import java.util.List;

public class AskPlayCharacterCardMessage extends Message {
    private final List<Character> availableCharacterCards;

    public AskPlayCharacterCardMessage(List<Character> availableCharacterCards) {
        super("server", MessageType.ASK_CHARACTER_CARD);
        this.availableCharacterCards = availableCharacterCards;
    }

    public List<Character> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    @Override
    public String toString() {
        return "Available Character Card"+ availableCharacterCards;
    }
}
