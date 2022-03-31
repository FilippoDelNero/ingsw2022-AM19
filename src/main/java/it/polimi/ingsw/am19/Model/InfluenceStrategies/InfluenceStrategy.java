package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.PieceColor;
import it.polimi.ingsw.am19.Model.ProfessorManager;
import it.polimi.ingsw.am19.Model.TowerColor;

import java.util.Map;

/**
 * Interface implemented by different strategies used to execute the calculateInfluence method in classes Island and Island manager
 */
public interface InfluenceStrategy {
    void calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIsland, ProfessorManager manager);
}