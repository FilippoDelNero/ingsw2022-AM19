package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.ChangeIfEqualCheckProfessor;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.CheckProfessorStrategy;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorManagerTest {

    @Test
    void setGameboards() {
        ProfessorManager manager = new ProfessorManager();
        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);
        Player player3 = new Player("Laura", TowerColor.GREY, WizardFamily.WARRIOR);
        GameBoard gameBoard1 = new GameBoard(player1, 8, manager, 7);
        GameBoard gameBoard2 = new GameBoard(player2, 8, manager, 7);
        GameBoard gameBoard3 = new GameBoard(player3, 8, manager, 7);
        Map<Player, GameBoard> GB = new HashMap<>();
        GB.put(player1, gameBoard1);
        GB.put(player2, gameBoard2);
        GB.put(player3, gameBoard3);
        manager.setGameboards(GB);
    }

    @Test
    void checkProfessor() {
        ProfessorManager manager = new ProfessorManager();
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

        assertDoesNotThrow(() -> gameBoard1.addStudent(PieceColor.RED));
        assertDoesNotThrow(() -> gameBoard1.moveStudentToDiningRoom(PieceColor.RED));

        manager.checkProfessor(PieceColor.RED, player1);
        assertEquals(player1, manager.getOwner(PieceColor.RED));

        assertDoesNotThrow(() -> gameBoard2.addStudent(PieceColor.RED));
        assertDoesNotThrow(() -> gameBoard2.moveStudentToDiningRoom(PieceColor.RED));

        manager.checkProfessor(PieceColor.RED, player2);
        assertEquals(player1, manager.getOwner(PieceColor.RED));

        assertDoesNotThrow(() -> gameBoard2.addStudent(PieceColor.RED));
        assertDoesNotThrow(() -> gameBoard2.moveStudentToDiningRoom(PieceColor.RED));

        manager.checkProfessor(PieceColor.RED, player2);
        assertEquals(player2, manager.getOwner(PieceColor.RED));

        manager.setCurrentStrategy(strategy);
        assertTrue(manager.getCurrentStrategy() instanceof ChangeIfEqualCheckProfessor);

        assertDoesNotThrow(() -> gameBoard1.addStudent(PieceColor.RED));
        assertDoesNotThrow(() -> gameBoard1.moveStudentToDiningRoom(PieceColor.RED));

        manager.checkProfessor(PieceColor.RED, player1);
        assertEquals(player1, manager.getOwner(PieceColor.RED));
    }
}