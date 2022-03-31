package it.polimi.ingsw.am19.Model.InfluenceStrategies;
import it.polimi.ingsw.am19.Model.PieceColor;
import it.polimi.ingsw.am19.Model.ProfessorManager;
import it.polimi.ingsw.am19.Model.TowerColor;

import java.util.Map;

public class StandardInfluence implements InfluenceStrategy {

    @Override
    public void calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIsland, ProfessorManager manager) {
        System.out.println("Standard influence will be calculated");
    }
}
