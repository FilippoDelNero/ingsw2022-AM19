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
    Player calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIslands, ProfessorManager manager);
}