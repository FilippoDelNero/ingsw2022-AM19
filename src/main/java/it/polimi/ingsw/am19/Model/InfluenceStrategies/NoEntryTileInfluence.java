package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import java.util.Map;

/**
 * just a place holder for the No-Entry tiles
 */
public class NoEntryTileInfluence implements InfluenceStrategy {

    /**
     * this method doesn't calculate any influence as per effect of the No-Entry Tile
     * @param numOfStudents the map of students present on the island
     * @param towerColor the color of the tower if present on the island, null otherwise
     * @param numOfIslands the number of islands that makes up this group
     * @param manager the professor manager in order to assign the influence of each color to the player owning the corresponding professor
     * @return null
     */
    @Override
    public Player calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIslands, ProfessorManager manager) {
        return null;
    }
}
