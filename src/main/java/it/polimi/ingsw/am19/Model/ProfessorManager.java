package it.polimi.ingsw.am19.Model;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.*;

import java.util.HashMap;
import java.util.Map;

public class ProfessorManager {
    /**
     * a map where a professor for each color is associated with its owner
     */
    private final Map<PieceColor, Player> professors;

    /**
     * a map where each player is associated with its own game-board
     */
    private Map<Player, GameBoard> gameboards;

    /**
     *
     */
    private CheckProfessorStrategy currentStrategy;

    /**
     * constructs a new, empty ProfessorManager entity
     */
    public ProfessorManager() {
        professors = new HashMap<>();
        gameboards = new HashMap<>();
        currentStrategy = new StandardCheckProfessor();
    }

    /**
     * setter for the Gameboards attribute
     * @param map the map you want to add to the ProfessorManager class
     */
    public void setGameboards(Map<Player, GameBoard> map) {
        this.gameboards = map;
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
     * check if a professor is still owned by a player
     * it will be called by the gameBoard after moving students from entrance to dining hall
     * @param color the color of the student added to the dining hall
     * @param player the player that has moved the student
     */
    public void checkProfessor(PieceColor color, Player player) {
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

}
