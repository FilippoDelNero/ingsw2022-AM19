package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;

import java.util.List;
import java.util.Map;

/**
 * Cache class used to store objects to be displayed on the view
 */
public class Cache { //Non sono sicuro sia il package giusto
    /** a list of "clouds" */
    private List<Map<PieceColor, Integer>> clouds;

    /** a list of "gameboards" */
    private List<ReducedGameBoard> gameBoards;

    /** a list of "islands" */
    private List<ReducedIsland> islands;

    /** a list of HelperCard representing the deck of the player */
    private List<HelperCard> helperDeck;

    /** a list, if present, of "characterCards" */
    private List<Character> characterCards;

    /**
     * getter for the clouds attribute
     * @return the List of Maps representing the clouds
     */
    public List<Map<PieceColor, Integer>> getClouds() {
        return clouds;
    }

    /**
     * setter for the clouds attribute
     * @param clouds a list of Maps
     */
    public void setClouds(List<Map<PieceColor, Integer>> clouds) {
        this.clouds = clouds;
    }

    /**
     * getter for the GameBoards attribute
     * @return a list of reducedGameBoard
     */
    public List<ReducedGameBoard> getGameBoards() {
        return gameBoards;
    }

    /**
     * setter for the GameBoards attribute
     * @param gameBoards a list of reducedGameBoard
     */
    public void setGameBoards(List<ReducedGameBoard> gameBoards) {
        this.gameBoards = gameBoards;
    }

    /**
     * getter for the Islands attribute
     * @return a list of reducedIslands
     */
    public List<ReducedIsland> getIslands() {
        return islands;
    }

    /**
     * setter for the Islands attribute
     * @param islands a list of reducedIslands
     */
    public void setIslands(List<ReducedIsland> islands) {
        this.islands = islands;
    }

    /**
     * getter for the HelperDeck attribute
     * @return a list of HelperCard
     */
    public List<HelperCard> getHelperDeck() {
        return helperDeck;
    }

    /**
     * setter for the HelperDeck attribute
     * @param helperDeck a list of HelperCard
     */
    public void setHelperDeck(List<HelperCard> helperDeck) {
        this.helperDeck = helperDeck;
    }

    /**
     * getter for the CharacterCards attribute
     * @return a list of Character
     */
    public List<Character> getCharacterCards() {
        return characterCards;
    }

    /**
     * setter for the CharacterCard attribute
     * @param characterCards a list of Character
     */
    public void setCharacterCards(List<Character> characterCards) {
        this.characterCards = characterCards;
    }
}
