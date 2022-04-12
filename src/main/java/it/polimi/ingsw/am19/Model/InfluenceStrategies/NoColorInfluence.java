package it.polimi.ingsw.am19.Model.InfluenceStrategies;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import java.util.Map;

/**
 * strategy to calculate influence on a island, the influence given by the chosen color is NOT taken into account
 */
public class NoColorInfluence extends AbstractInfluenceStrategy implements InfluenceStrategy {
    /**
     * the color of the students that will not contribute to the amount of influence
     */
    private PieceColor colorWithNoInfluence;

    /**
     * setter for the colorWithNoInfluence attribute
     * @param color the color that we want to not calculate the influence of
     */
    public void setColor(PieceColor color) {
        this.colorWithNoInfluence = color;
    }

    /**
     * overrides the method to calculate the influence from students excluding the colorWithNoInfluence
     * @param numOfStudents the map containing the number of students for each color
     * @param manager the professor manager of the match
     */
    @Override
    void influenceFromStudent(Map<PieceColor, Integer> numOfStudents, ProfessorManager manager) {
        for(PieceColor color : numOfStudents.keySet()) {
            if(color != colorWithNoInfluence) {
                Player player = manager.getOwner(color);
                if(player != null) {
                    oldInfluenceValue = influenceMap.get(player);
                    newInfluenceValue = oldInfluenceValue + numOfStudents.get(color);
                    influenceMap.put(player, newInfluenceValue);
                }
            }
        }
    }

    /**
     * NON-standard way of calculating influence, the selected color does not contribute influence
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
        changeOwner();
        return returnOwner(numOfIslands, manager);
    }
}
