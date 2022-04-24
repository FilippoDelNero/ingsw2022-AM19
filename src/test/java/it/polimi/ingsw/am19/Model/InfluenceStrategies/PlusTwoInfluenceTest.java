package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlusTwoInfluenceTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }
    /**
     * testing the +2 influence points to the current player
     */
    @Test
    void testCalculateInfluenceWithPlusTwo() {
        //--ISLAND PART--
        PlusTwoInfluence plusTwoInfluence = new PlusTwoInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        map.put(PieceColor.RED, 2);
        map.put(PieceColor.GREEN, 1);
        map.put(PieceColor.BLUE, 3);
        map.put(PieceColor.YELLOW, 1);
        map.put(PieceColor.PINK, 0);

        Island island = new Island(map, TowerColor.WHITE, true, plusTwoInfluence, 1);

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

        plusTwoInfluence.setCurrentPlayer(player1);

        //--CHECK IF EVERYTHING IS OK PART
        assertTrue(island.calculateInfluence(manager));
        assertEquals(TowerColor.BLACK, island.getTowerColor());
        assertEquals(8, gameBoard2.getNumOfTowers());
        assertEquals(7, gameBoard1.getNumOfTowers());
    }

    /**
     * test the influence calculation of a previously NON-owned island
     */
    @Test
    void testCalculateInfluenceWithNoPreviousOwner() {
        //--ISLAND PART--
        PlusTwoInfluence plusTwoInfluence = new PlusTwoInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        map.put(PieceColor.RED, 1);
        map.put(PieceColor.GREEN, 0);
        map.put(PieceColor.BLUE, 2);
        map.put(PieceColor.YELLOW, 0);
        map.put(PieceColor.PINK, 0);

        Island island = new Island(map, null, true, plusTwoInfluence, 1);

        //--PROFESSOR MANAGER PART--
        ProfessorManager manager = new ProfessorManager();
        Map<Player, GameBoard> GB = new HashMap<>();
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

        plusTwoInfluence.setCurrentPlayer(player1);

        //--CHECK IF EVERYTHING IS OK PART
        assertTrue(island.calculateInfluence(manager));
        assertEquals(TowerColor.BLACK, island.getTowerColor());
        assertEquals(7, gameBoard1.getNumOfTowers());
    }

    /**
     * test the influence calculation of a previously NON-owned island and two players with same influence
     */
    @Test
    void testCalculateInfluenceWithNoPreviousOwnerWithTwist() {
        //--ISLAND PART--
        PlusTwoInfluence plusTwoInfluence = new PlusTwoInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        map.put(PieceColor.RED, 1);
        map.put(PieceColor.GREEN, 0);
        map.put(PieceColor.BLUE, 3);
        map.put(PieceColor.YELLOW, 0);
        map.put(PieceColor.PINK, 0);

        Island island = new Island(map, null, true, plusTwoInfluence, 1);

        //--PROFESSOR MANAGER PART--
        ProfessorManager manager = new ProfessorManager();
        Map<Player, GameBoard> GB = new HashMap<>();
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

        plusTwoInfluence.setCurrentPlayer(player1);

        //--CHECK IF EVERYTHING IS OK PART
        assertFalse(island.calculateInfluence(manager));
        assertNull(island.getTowerColor());
    }

    /**
     * test the influence calculation of a previously owned island, with no change in ownership
     */
    @Test
    void testCalculateInfluenceNoOwnershipChange() {
        //--ISLAND PART--
        PlusTwoInfluence plusTwoInfluence = new PlusTwoInfluence();
        Map<PieceColor, Integer> map = new HashMap<>();
        map.put(PieceColor.RED, 3);
        map.put(PieceColor.GREEN, 2);
        map.put(PieceColor.BLUE, 2);
        map.put(PieceColor.YELLOW, 1);
        map.put(PieceColor.PINK, 0);

        Island island = new Island(map, TowerColor.BLACK, true, plusTwoInfluence, 1);

        //--PROFESSOR MANAGER PART--
        ProfessorManager manager = new ProfessorManager();
        Map<Player, GameBoard> GB = new HashMap<>();
        //create 3 players
        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);
        Player player3 = new Player("Laura", TowerColor.GREY, WizardFamily.WARRIOR);
        //create 3 gameboard, one for each player
        GameBoard gameBoard1 = new GameBoard(player1, 7, manager, 7);
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

        plusTwoInfluence.setCurrentPlayer(player1);

        //--CHECK IF EVERYTHING IS OK PART
        assertFalse(island.calculateInfluence(manager));
        assertEquals(TowerColor.BLACK, island.getTowerColor());
        assertEquals(8, gameBoard2.getNumOfTowers());
        assertEquals(7, gameBoard1.getNumOfTowers());
    }
}
