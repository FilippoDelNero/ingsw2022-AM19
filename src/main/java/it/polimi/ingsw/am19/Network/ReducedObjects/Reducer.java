package it.polimi.ingsw.am19.Network.ReducedObjects;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class to reduce objects into their network-friendly version
 */
public class Reducer {

    /**
     * reduce a list of clouds into a list of the maps contained into said clouds
     * @param clouds a list of clouds
     * @return a list of maps
     */
    public List<Map<PieceColor, Integer>> reduceClouds(List<Cloud> clouds) {
        List<Map<PieceColor, Integer>> listOfReducedClouds = new ArrayList<>();
        for(Cloud c : clouds) {
            listOfReducedClouds.add(c.getStudents());
        }
        return listOfReducedClouds;
    }

    /**
     * reduce a gameboard to a ReducedGameboard so it can be sent through the network
     * @param gb the gameboard you want to send to the client
     * @return a reducedGameboard
     */
    public ReducedGameBoard reduceGameBoard(GameBoard gb) {
        //get parameters ready
        String nick = gb.getPlayer().getNickname();
        HashMap<PieceColor,Integer> entrance = gb.getEntrance();
        HashMap<PieceColor,Integer> dining = gb.getDiningRoom();
        ArrayList<PieceColor> profs = gb.getProfessor().getProfessorsByPlayer(gb.getPlayer());
        int towers = gb.getNumOfTowers();
        return new ReducedGameBoard(nick, entrance, dining, profs, towers);
    }

    /**
     * reduce a list of gameboards to a list of ReducedGameboards so it can be sent through the network
     * @param map of player - gameboards
     * @return a list of reducedGameboards
     */
    public List<ReducedGameBoard> reducedGameBoard(Map<Player, GameBoard> map) {
        List<ReducedGameBoard> listOfReducedGameBoards = new ArrayList<>();
        for(Player p : map.keySet())
            listOfReducedGameBoards.add(reduceGameBoard(map.get(p)));
        return listOfReducedGameBoards;
    }

    /**
     * reduce an island to a ReducedIsland so it can be sent through the network
     * @param isle the island that needs to be reduced
     * @return the reducedIsland
     */
    public ReducedIsland reduceIsland(Island isle) {
        //get parameters ready
        Map<PieceColor, Integer> students = isle.getNumOfStudents();
        TowerColor towerColor = isle.getTowerColor();
        boolean presence = isle.isMotherNaturePresent();
        int num = isle.getNumOfIslands();
        return new ReducedIsland(students, towerColor, presence, num);
    }

    /**
     * reduce a list of islands to a list of ReducedIslands so it can be sent through the network
     * @param islandList a list of islands
     * @return a list of reducedIslands
     */
    public List<ReducedIsland> reduceIsland(List<Island> islandList) {
        List<ReducedIsland> listOfReducedIslands = new ArrayList<>();
        for(Island i : islandList)
            listOfReducedIslands.add(reduceIsland(i));
        return listOfReducedIslands;
    }

    /**
     * Takes all HelperCards of each players
     * puts them into a map correlating the player's nickname and its helperDeck
     * @param players the players in the match
     * @return a map correlating the player's nickname and its helperDeck
     */
    public Map<String, List<HelperCard>> reduceHelperCards(List<Player> players) {
        Map<String, List<HelperCard>> mapOfDecks = new HashMap<>();
        for(Player p : players)
            mapOfDecks.put(p.getNickname(), p.getHelperDeck());
        return mapOfDecks;
    }

    /**
     * takes all CharacterCards and reduced them into a list of Characters
     * @param cards the cards in the match
     * @return a list of Characters
     */
    public List<Character> reduceCharacterCards(List<AbstractCharacterCard> cards) {
        List<Character> characterList = new ArrayList<>();
        for(AbstractCharacterCard c : cards)
            characterList.add(c.getId());
        return characterList;
    }
}
