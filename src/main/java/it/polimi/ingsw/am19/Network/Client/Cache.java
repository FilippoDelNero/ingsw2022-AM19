package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cache class used to store objects to be displayed on the view
 */
public class Cache {
    /** a list of "clouds" */
    private List<Map<PieceColor, Integer>> clouds;

    /** a list of "gameboards" */
    private List<ReducedGameBoard> gameBoards;

    /** a list of "islands" */
    private List<ReducedIsland> islands;

    /** a list, if present, of "characterCards" */
    private List<Character> characterCards;

    private String nickname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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
        List<ReducedGameBoard> list = new ArrayList<>();

        for(ReducedGameBoard rgb : gameBoards) {
            if(rgb.playerNickname().equals(nickname))
                list.add(0, rgb);
            else
                list.add(rgb);
        }

        this.gameBoards = list;
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
