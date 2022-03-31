package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for testing Island class
 */
public class IslandTest {
    /**
     * Test the addStudent and getStudent method
     */
    @Test
    @DisplayName("Test Add- & Get- Student")
    void testAddAndGetStudent() {
        StandardInfluence standardInfluence = new StandardInfluence();
        Island island = new Island(standardInfluence);
        island.addStudent(PieceColor.RED);
        assertEquals(1, island.getNumOfStudent().get(PieceColor.RED));
    }

    /**
     * Test the parametric constructor
     */
    @Test
    @DisplayName("Test parametric constructor")
    void testParamConstructor() {
        StandardInfluence standardInfluence = new StandardInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        //fill the map with some students
        for(PieceColor color: PieceColor.values())
            map.put(color, 2);

        Island island = new Island(map, TowerColor.BLACK, false, standardInfluence, 2);

        assertEquals(2, island.getNumOfStudent().get(PieceColor.RED));
        assertEquals(2, island.getNumOfStudent().get(PieceColor.GREEN));
        assertEquals(2, island.getNumOfStudent().get(PieceColor.BLUE));
        assertEquals(2, island.getNumOfStudent().get(PieceColor.YELLOW));
        assertEquals(2, island.getNumOfStudent().get(PieceColor.PINK));
        assert(island.getTowerColor() == TowerColor.BLACK);
        assert(!island.isMotherNaturePresent());
        assertEquals(2, island.getNumOfIslands());
    }
}