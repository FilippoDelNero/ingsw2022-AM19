package it.polimi.ingsw.am19.Model.InfluenceStrategies;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import java.util.Map;

/**
 * strategy to calculate influence on a island, the influence given by towers is NOT taken into account
 */
public class NoTowersInfluence extends AbstractInfluenceStrategy implements InfluenceStrategy {
    /**
     * NON-standard way of calculating influence, the tower's influence is not calculated
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
        changeOwner();
        return returnOwner(numOfIslands, manager);
    }
}
