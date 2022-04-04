package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.ChangeIfEqualCheckProfessor;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.CheckProfessorStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoEntryTileInfluence;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for testing IslandManager class
 */
class IslandManagerTest {
    /**
     *
     */
    @Test
    void testGetIslands() {
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager manager = new IslandManager(professorManager);
        assertEquals(12, manager.getIslands().size());
    }

    /**
     *
     */
    @Test
    void testCalculateInfluence() {
        //--ISLAND PART--
        ProfessorManager manager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(manager);
        islandManager.getIslands().get(0).addStudent(PieceColor.RED);
        islandManager.getIslands().get(1).addStudent(PieceColor.GREEN);

        //--PROFESSOR MANAGER PART--
        Map<Player, GameBoard> GB = new HashMap<>();
        CheckProfessorStrategy strategy = new ChangeIfEqualCheckProfessor();
        //create 3 players
        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);
        Player player3 = new Player("Laura", TowerColor.GREY, WizardFamily.WARRIOR);
        //create 3 gameboard, one for each player
        GameBoard gameBoard1 = new GameBoard(player1, 8, manager, 7);
        GameBoard gameBoard2 = new GameBoard(player2, 8, manager, 7);
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
        islandManager.calculateInfluence(islandManager.getIslands().get(0));
        assertEquals(TowerColor.BLACK, islandManager.getIslands().get(0).getTowerColor());
        assertEquals(7, gameBoard1.getNumOfTowers());
        islandManager.calculateInfluence(islandManager.getIslands().get(1));
        assertEquals(11, islandManager.getIslands().size());
        assertEquals(6, gameBoard1.getNumOfTowers());
    }

    /**
     *
     */
    @Test
    void testCalculateInfluenceWithNoEntryTile() {
        NoEntryTileInfluence netInfluence = new NoEntryTileInfluence();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager manager = new IslandManager(professorManager);
        manager.getIslands().get(0).setInfluenceStrategy(netInfluence);
        assertTrue(manager.getIslands().get(0).getInfluenceStrategy() instanceof NoEntryTileInfluence);
        manager.calculateInfluence(manager.getIslands().get(0));
        assertTrue(manager.getIslands().get(0).getInfluenceStrategy() instanceof StandardInfluence);
    }
}