package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import java.util.Map;

/**
 * strategy to calculate influence on a island, gives a +2 to the current player
 */
public class PlusTwoInfluence extends AbstractInfluenceStrategy implements InfluenceStrategy {
    /**
     * the current player, the one who played the card
     */
    private Player currentPlayer;

    /**
     * setter for the current player attribute
     * @param player the player that played the card
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * NON-standard way of calculating influence, a +2 modifiers have been applied to the currentPlayer
     * @param numOfStudents the map of students present on the island
     * @param towerColor the color of the tower if present on the island, null otherwise
     * @param numOfIslands the number of islands that makes up this group
     * @param manager the professor manager in order to assign the influence of each color to the player owning the corresponding professor
     * @return null if the owner has stayed the same or the new player who owns the island now
     */
    @Override
    public Player calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIslands, ProfessorManager manager) {
        initialize(towerColor, manager);
        influenceFromStudent(numOfStudents, manager);
        influenceFromTower(numOfIslands);

        //add the extra influence to the player that played the card
        oldInfluenceValue = influenceMap.get(currentPlayer);
        newInfluenceValue = oldInfluenceValue + 2;
        influenceMap.put(currentPlayer, newInfluenceValue);

        changeOwner();
        return returnOwner(numOfIslands, manager);
    }
}
