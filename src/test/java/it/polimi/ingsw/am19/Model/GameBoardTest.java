package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyTowersException;
import it.polimi.ingsw.am19.Model.InternalMoveStrategy.ReverseMove;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the class GameBoard
 */
class GameBoardTest {

    /**
     * Testing getter for entrance
     */
    @Test
    @DisplayName("Testing getter for entrance")
    void getEntrance() {
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        GameBoard gameboard = new GameBoard(player,8,null,7);
        for(PieceColor color: PieceColor.values())
            assertEquals(0,gameboard.getEntrance().get(color));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertEquals(1,gameboard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertEquals(2,gameboard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.GREEN));
        assertEquals(1,gameboard.getEntrance().get(PieceColor.GREEN));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertEquals(3,gameboard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertEquals(4,gameboard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.PINK));
        assertEquals(1,gameboard.getEntrance().get(PieceColor.PINK));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertEquals(5,gameboard.getEntrance().get(PieceColor.BLUE));
        assertThrows(TooManyStudentsException.class,()->gameboard.addStudent(PieceColor.YELLOW));
    }

    /**
     * Testing getter for DiningRoom
     */
    @Test
    @DisplayName("Testing getter of DiningRoom")
    void getDiningRoom() {
        GameBoard gameBoard = new GameBoard(null,8,null, 7);
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertEquals(1,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(0,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertEquals(1,gameBoard.getDiningRoom().get(PieceColor.BLUE));
    }

    /**
     * Testing getter for NumOfTowers
     */
    @Test
    @DisplayName("Testing getNumOfTowers")
    void getNumOfTowers() {
        GameBoard gameBoard = new GameBoard(null,8,null,8);
        assertEquals(8,gameBoard.getNumOfTowers());
        assertDoesNotThrow(()->gameBoard.setNumOfTowers(-3));
        assertEquals(5,gameBoard.getNumOfTowers());
    }

    /**
     * Testing the setter for the NumOfTowers
     */
    @Test
    @DisplayName("Testing setter of NumTowers")
    void setNumOfTowers() {
        GameBoard gameBoard = new GameBoard(null,8,null,8);
        assertEquals(8,gameBoard.getNumOfTowers());
        /*assertDoesNotThrow(()->gameBoard.setNumOfTowers(-3));
        assertEquals(5,gameBoard.getNumOfTowers());
        assertThrows(TooManyTowersException.class,()->gameBoard.setNumOfTowers(4));*/
    }

    /**
     * Testing the change of InternalMoveStrategy
     */
    @Test
    @DisplayName("Testing setter of moveStrategy")
    void setMoveStrategy() {
        GameBoard gameBoard = new GameBoard(null,8,null, 7);
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertEquals(1,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(0,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertEquals(1,gameBoard.getDiningRoom().get(PieceColor.BLUE));
        gameBoard.setMoveStrategy(new ReverseMove());
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(1,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertEquals(0,gameBoard.getDiningRoom().get(PieceColor.BLUE));
    }

    /**
     * Testing addStudent to entrance
     */
    @Test
    @DisplayName("Testing addStudent")
    void addStudent() {
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        GameBoard gameboard = new GameBoard(player,8,null,7);
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.GREEN));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.PINK));
        assertDoesNotThrow(()-> gameboard.addStudent(PieceColor.BLUE));
        int numStudent=0;
        for(PieceColor color: PieceColor.values())
            numStudent += gameboard.getEntrance().get(color);
        assertEquals(7,numStudent);
        assertThrows(TooManyStudentsException.class,()->gameboard.addStudent(PieceColor.YELLOW));
    }

    /**
     * Testing the remove student from entrance
     */
    @Test
    @DisplayName("Testing remove student from entrance")
    void removeStudent() {
        GameBoard gameBoard = new GameBoard(null,8,null,7);
        assertThrows(NoSuchColorException.class,()->gameBoard.removeStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertEquals(1,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.removeStudent(PieceColor.BLUE));
        assertThrows(NoSuchColorException.class,()->gameBoard.removeStudent(PieceColor.BLUE));

    }

    /**
     * Testing move student from entrance to DiningHall and vice versa
     */
    @Test
    @DisplayName("Testing move student")
    void moveStudentToDiningRoom() {
        GameBoard gameBoard = new GameBoard(null,8,null, 7);
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertEquals(1,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(0,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertEquals(1,gameBoard.getDiningRoom().get(PieceColor.BLUE));
    }
}