package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.CharacterCards.Character;

public class PlayCharacterCardMessage extends Message {
    private final Character cardToUse;

    public PlayCharacterCardMessage(String nickname, Character cardToUse) {
        super(nickname, MessageType.PLAY_CHARACTER_CARD);
        this.cardToUse = cardToUse;
    }

    public Character getCardToUse() {
        return cardToUse;
    }

    @Override
    public String toString() {
        return "PlayCharacterCardMessage{" +
                "nickname=" + getNickname() +
                "cardToUse=" + cardToUse +
                '}';
    }
}
