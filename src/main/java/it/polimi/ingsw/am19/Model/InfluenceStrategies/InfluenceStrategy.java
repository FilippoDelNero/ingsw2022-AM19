package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.PieceColor;
import it.polimi.ingsw.am19.Model.Player;
import it.polimi.ingsw.am19.Model.ProfessorManager;
import it.polimi.ingsw.am19.Model.TowerColor;

import java.util.Map;

/**
 * Interface implemented by different strategies used to execute the calculateInfluence method in classes Island and Island manager
 */
public interface InfluenceStrategy {
    /**
     * calculate the influence of each player on the island, returns the new owner if it has changed
     * @param numOfStudents the map of students present on the island
     * @param towerColor the color of the tower if present on the island, null otherwise
     * @param numOfIslands the number of islands that makes up this group
     * @param manager the professor manager in order to assign the influence of each color to the player owning the corresponding professor
     * @return null if the owner has stayed the same or the player who owns the island now
     */
    Player calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIslands, ProfessorManager manager);
}