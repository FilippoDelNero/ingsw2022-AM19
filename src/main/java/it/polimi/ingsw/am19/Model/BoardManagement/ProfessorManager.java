package it.polimi.ingsw.am19.Model.BoardManagement;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.*;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class ProfessorManager implements Serializable {
    /**
     * a map where a professor for each color is associated with its owner
     */
    private final Map<PieceColor, Player> professors;

    /**
     * a map where each player is associated with its own game-board
     */
    private Map<Player, GameBoard> gameboards;

    /**
     * the current strategy set
     * the strategy will be used to check the ownership of a professor
     */
    private CheckProfessorStrategy currentStrategy;

    /**
     * the default strategy used by the professor manager to check the ownership of a professor
     */
    private final CheckProfessorStrategy defaultStrategy;

    /**
     * the player the played the Character Card
     * this value is used to determine if we are in a new turn or not
     */
    private Player whoUsedTheCard;

    /**
     * the value of the nextRoundOrder of the card played by the first player
     * this value is used to determine if we are in a new turn or not
     */
    private int HelperCardOfWhoUsedTheCard;

    /**
     * constructs a new, empty ProfessorManager entity
     */
    public ProfessorManager() {
        professors = new HashMap<>();
        gameboards = new HashMap<>();
        defaultStrategy = new StandardCheckProfessor();
        currentStrategy = defaultStrategy;
    }

    /**
     * setter for the Gameboards attribute
     * @param map the map you want to add to the ProfessorManager class
     */
    public void setGameboards(Map<Player, GameBoard> map) {
        this.gameboards = map;
    }

    /**
     * getter for the gameboards attribute
     * @return a map player->gameboard
     */
    public Map<Player, GameBoard> getGameboards() {
        return gameboards;
    }

    /**
     * setter for the currentStrategy attribute
     * @param strategy the strategy that will be used by the professorManager to assign a professor
     */
    public void setCurrentStrategy(CheckProfessorStrategy strategy) {
        this.currentStrategy = strategy;
    }

    /**
     * getter for the currentStrategy attribute
     * @return the current strategy in use
     */
    public CheckProfessorStrategy getCurrentStrategy() {
        return currentStrategy;
    }

    /**
     * returns the player that owns the professor corresponding to a certain pieceColor
     * @param color the color of the professor you want to know the owner of
     * @return the player that owns the professor of the specified color
     */
    public Player getOwner(PieceColor color) {
        return professors.get(color);
    }

    /**
     * setter for the whoUsedTheCard attribute
     * @param value the value of the card played by the first player in a given turn
     */
    public void setWhoUsedTheCard(Player player, int value) {
        whoUsedTheCard = player;
        HelperCardOfWhoUsedTheCard = value;
    }

    /**
     * check if a professor is still owned by a player
     * it will be called by the gameBoard after moving students from entrance to dining hall
     * @param color the color of the student added to the dining hall
     * @param player the player that has moved the student
     */
    public void checkProfessor(PieceColor color, Player player) {
        changeStrategyIfNeeded(player);
        if(professors.get(color) == null) {
            updateProfessor(color, player);
        }
        else {
            //get the number of students of the current owner of the professor
            Player playerToBeat = professors.get(color);
            Map<PieceColor, Integer> mapToBeat = gameboards.get(playerToBeat).getDiningRoom();
            int numberToBeat = mapToBeat.get(color);

            //get the number of students of the player passed as a parameter
            Map<PieceColor, Integer> mapOfChallenger = gameboards.get(player).getDiningRoom();
            int numberOfChallenger = mapOfChallenger.get(color);

            if(currentStrategy.checkIfChangeIsNeeded(numberOfChallenger, numberToBeat)) {
                updateProfessor(color, player);
            }
        }
    }

    /**
     * change a professor ownership
     * it will be called, if necessary, by the checkProfessor method
     * @param color the professor's color i want to change ownership of
     * @param player the player that has moved the student which will get the ownership of the professor
     */
    private void updateProfessor(PieceColor color, Player player) {
        professors.put(color, player);
    }

    /**
     * checks if a different strategy was set, then checks if we are still in the same turn, if not it changes it back to default
     */
    private void changeStrategyIfNeeded(Player player) {
        if(currentStrategy != defaultStrategy && isNewTurn(player))
            setCurrentStrategy(defaultStrategy);
    }

    /**
     * check if we are still in the same turn as when a given event happened
     * @return true if we are in the same turn, false otherwise
     */
    private boolean isNewTurn(Player player) {
        return whoUsedTheCard != player || HelperCardOfWhoUsedTheCard != player.getCurrentCard().getNextRoundOrder();
    }

    /**
     * Returns the number of professors owned by the selected player
     * @param player is the player whose number of professors owned needs to be returned
     * @return number of professors owned by the selected player
     */
    public int getNumProfessorsByPlayer(Player player){
        int numProfessors = 0;
        for(PieceColor color: professors.keySet()){
            if(getOwner(color).equals(player))
                numProfessors++;
        }
        return numProfessors;
    }

    /**
     * Returns a map containing the association between PieceColor and Player owner
     * @return a map containing the association between PieceColor and Player owner
     */
    public Map<PieceColor, Player> getProfessors() {
        return professors;
    }

    /**
     * method to get all professors owned by a given player
     * @param player the player we want to know which professors he owns
     * @return the colors of the professors owned by the given player
     */
    public ArrayList<PieceColor> getProfessorsByPlayer(Player player) {
        ArrayList<PieceColor> list = new ArrayList<>();
        for(PieceColor p : professors.keySet()){
            if(professors.get(p) == player)
                list.add(p);
        }
        return list;
    }
}
