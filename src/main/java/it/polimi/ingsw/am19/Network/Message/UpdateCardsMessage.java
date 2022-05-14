package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;

import java.util.List;
import java.util.Map;

/**
 * message used to update the cards, both Helper and Character, or just the Helper's ones
 */
public class UpdateCardsMessage extends Message{

    /** a list of the Character cards present */
    private final List<Character> characterCardList;

    /**
     * constructor used when both Helpers and Characters cards are presents
     * @param listCharacters the CharacterCards present in the match
     */
    public UpdateCardsMessage(List<Character> listCharacters) {
        super("server", MessageType.UPDATE_CARDS);

        this.characterCardList = listCharacters;
    }


    /**
     * getter for the CharacterCards list
     * @return the list of character cards
     */
    public List<Character> getCharacterCardList() {
        return characterCardList;
    }
}
