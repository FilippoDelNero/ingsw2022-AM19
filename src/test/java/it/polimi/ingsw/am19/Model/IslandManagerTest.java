package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoEntryTileInfluence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandManagerTest {

    @Test
    void testGetIslands() {
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager manager = new IslandManager(professorManager);
        assertEquals(12, manager.getIslands().size());
    }

    @Test
    void testCalculateInfluence() {
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager manager = new IslandManager(professorManager);
        manager.getIslands().get(0).setTowerColor(TowerColor.BLACK);
        manager.getIslands().get(1).setTowerColor(TowerColor.BLACK);
        manager.getIslands().get(0).addStudent(PieceColor.RED);
        manager.getIslands().get(1).addStudent(PieceColor.BLUE);
        manager.calculateInfluence(manager.getIslands().get(0));
    }

    @Test
    void testCalculateInfluenceWithNoEntryTile() {
        NoEntryTileInfluence netInfluence = new NoEntryTileInfluence();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager manager = new IslandManager(professorManager);
        manager.getIslands().get(0).setInfluenceStrategy(netInfluence);
        manager.calculateInfluence(manager.getIslands().get(0));
    }
}