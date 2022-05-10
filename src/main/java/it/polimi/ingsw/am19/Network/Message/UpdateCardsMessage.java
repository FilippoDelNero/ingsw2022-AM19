package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;

import java.util.List;
import java.util.Map;

/**
 * message used to update the cards, both Helper and Character, or just the Helper's ones
 */
public class UpdateCardsMessage extends Message{
    /** a map of player's nickname - list of helper cards we need to send */
    private final Map<String, List<HelperCard>> helperCardsMap;

    /** a list of the Character cards present */
    private final List<Character> characterCardList;

    /**
     * constructor used when both Helpers and Characters cards are presents
     * @param listHelpers the HelperCards present in the match
     * @param listCharacters the CharacterCards present in the match
     */
    public UpdateCardsMessage(Map<String, List<HelperCard>> listHelpers, List<Character> listCharacters) {
        super("server", MessageType.UPDATE_CARDS);

        this.helperCardsMap = listHelpers;
        this.characterCardList = listCharacters;
    }

    /**
     * constructor used when only Helpers are presents
     * @param listHelpers the HelperCards present in the match
     */
    public UpdateCardsMessage(Map<String, List<HelperCard>> listHelpers) {
        super("server", MessageType.UPDATE_CARDS);

        this.helperCardsMap = listHelpers;
        this.characterCardList = null;
    }

    /**
     * getter for the HelperCards map
     * @return the map nickname - HelperDeck
     */
    public Map<String, List<HelperCard>> getHelperCardsMap() {
        return helperCardsMap;
    }

    /**
     * getter for the CharacterCards list
     * @return the list of character cards
     */
    public List<Character> getCharacterCardList() {
        return characterCardList;
    }
}
