package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
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
    void testAddAndGetStudent() {
        StandardInfluence standardInfluence = new StandardInfluence();
        Island island = new Island(standardInfluence);
        island.addStudent(PieceColor.RED);
        assertEquals(1, island.getNumOfStudents().get(PieceColor.RED));
    }

    /**
     * Test the parametric constructor
     */
    @Test
    void testParamConstructor() {
        StandardInfluence standardInfluence = new StandardInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        //fill the map with some students
        for(PieceColor color: PieceColor.values())
            map.put(color, 2);

        Island island = new Island(map, TowerColor.BLACK, false, standardInfluence, 2);

        assertEquals(2, island.getNumOfStudents().get(PieceColor.RED));
        assertEquals(2, island.getNumOfStudents().get(PieceColor.GREEN));
        assertEquals(2, island.getNumOfStudents().get(PieceColor.BLUE));
        assertEquals(2, island.getNumOfStudents().get(PieceColor.YELLOW));
        assertEquals(2, island.getNumOfStudents().get(PieceColor.PINK));
        assert(island.getTowerColor() == TowerColor.BLACK);
        assert(!island.isMotherNaturePresent());
        assertEquals(2, island.getNumOfIslands());
    }

    /**
     * test the setter for the towerColor attribute
     */
    @Test
    void testSetTowerColor() {
        StandardInfluence standardInfluence = new StandardInfluence();
        Island island = new Island(standardInfluence);
        island.setTowerColor(TowerColor.BLACK);
        assert(island.getTowerColor() == TowerColor.BLACK);
    }

    /**
     * test the influence calculation of a previously owned island
     */
    @Test
    void testCalculateInfluence() {
        //--ISLAND PART--
        StandardInfluence standardInfluence = new StandardInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        map.put(PieceColor.RED, 3);
        map.put(PieceColor.GREEN, 2);
        map.put(PieceColor.BLUE, 2);
        map.put(PieceColor.YELLOW, 1);
        map.put(PieceColor.PINK, 0);

        Island island = new Island(map, TowerColor.WHITE, true, standardInfluence, 1);

        //--PROFESSOR MANAGER PART--
        ProfessorManager manager = new ProfessorManager();
        Map<Player, GameBoard> GB = new HashMap<>();
        //create 3 players
        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);
        Player player3 = new Player("Laura", TowerColor.GREY, WizardFamily.WARRIOR);
        //create 3 gameboard, one for each player
        GameBoard gameBoard1 = new GameBoard(player1, 8, manager, 7);
        GameBoard gameBoard2 = new GameBoard(player2, 7, manager, 7);
        GameBoard gameBoard3 = new GameBoard(player3, 8, manager, 7);
        //associate a board to a player
        GB.put(player1, gameBoard1);
        GB.put(player2, gameBoard2);
        GB.put(player3, gameBoard3);
        //set the Gameboards attribute inside the professor manager
        manager.setGameboards(GB);
        //Set the professors of each player
        assertDoesNotThrow(() -> gameBoard1.addStudent(PieceColor.RED));
        assertDoesNotThrow(() -> gameBoard1.moveStudentToDiningRoom(PieceColor.RED));
        manager.checkProfessor(PieceColor.RED, player1);
        assertDoesNotThrow(() -> gameBoard1.addStudent(PieceColor.GREEN));
        assertDoesNotThrow(() -> gameBoard1.moveStudentToDiningRoom(PieceColor.GREEN));
        manager.checkProfessor(PieceColor.GREEN, player1);
        assertDoesNotThrow(() -> gameBoard2.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(() -> gameBoard2.moveStudentToDiningRoom(PieceColor.BLUE));
        manager.checkProfessor(PieceColor.BLUE, player2);
        assertDoesNotThrow(() -> gameBoard3.addStudent(PieceColor.YELLOW));
        assertDoesNotThrow(() -> gameBoard3.moveStudentToDiningRoom(PieceColor.YELLOW));
        manager.checkProfessor(PieceColor.YELLOW, player3);
        assertDoesNotThrow(() -> gameBoard2.addStudent(PieceColor.PINK));
        assertDoesNotThrow(() -> gameBoard2.moveStudentToDiningRoom(PieceColor.PINK));
        manager.checkProfessor(PieceColor.PINK, player2);

        //--CHECK IF EVERYTHING IS OK PART
        assertTrue(island.calculateInfluence(manager));
        assertEquals(TowerColor.BLACK, island.getTowerColor());
        assertEquals(8, gameBoard2.getNumOfTowers());
        assertEquals(7, gameBoard1.getNumOfTowers());
    }
}