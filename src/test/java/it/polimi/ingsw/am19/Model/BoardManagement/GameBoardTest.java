package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InternalMoveStrategy.ReverseMove;
import it.polimi.ingsw.am19.Model.Utilities.CoinManager;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the class GameBoard
 */
class GameBoardTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }
    /**
     * Testing getter for entrance
     */
    @Test
    @DisplayName("Testing getter for entrance")
    void getEntrance() {
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameboard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameboard);
        professorManager.setGameboards(map);


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
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameBoard);
        professorManager.setGameboards(map);

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
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameBoard);
        professorManager.setGameboards(map);

        assertEquals(8,gameBoard.getNumOfTowers());
        assertFalse(gameBoard.areTowersFinished());
    }

    /**
     * Tests removing a tower from the GameBoard without exceptions
     */
    @Test
    void removeTower(){
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);

        assertFalse(gameBoard.areTowersFinished());
        gameBoard.removeTower();
        assertEquals(7,gameBoard.getNumOfTowers());
    }

    /**
     * Tests removing a tower when there are no towers in the GameBoard
     */
    @Test
    void removeTowerFromEmptyGB(){
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);

        assertFalse(gameBoard.areTowersFinished());
        for(int i = 0; i < 8; i++)
            gameBoard.removeTower();
        assertTrue(gameBoard.areTowersFinished());

        //Removing towers when there are no towers available at all doesn't affect the GameBoard state
        gameBoard.removeTower();
        assertEquals(0,gameBoard.getNumOfTowers());
        //Still having 0 towers

        gameBoard.removeTower();
        assertEquals(0,gameBoard.getNumOfTowers());
        //Still having 0 towers

    }

    /**
     * Tests adding a tower when the GameBoard is running out of space for towers
     */
    @Test
    void addingTooManyTowers() {
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);

        gameBoard.addTower();
        assertEquals(8, gameBoard.getNumOfTowers());

        gameBoard.addTower();
        assertEquals(8, gameBoard.getNumOfTowers());
    }

    /**
     * Tests adding a tower to the GameBoard when there space for new towers
     */
    @Test
    public void addTower(){
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);

        gameBoard.removeTower();
        assertEquals(7, gameBoard.getNumOfTowers());
        gameBoard.addTower();
        assertEquals(8, gameBoard.getNumOfTowers());
    }

    /**
     * Testing the change of InternalMoveStrategy
     */
    @Test
    @DisplayName("Testing setter of moveStrategy")
    void setMoveStrategy() {
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameBoard);
        professorManager.setGameboards(map);

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
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameboard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameboard);
        professorManager.setGameboards(map);


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
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameBoard);
        professorManager.setGameboards(map);

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
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameBoard);
        professorManager.setGameboards(map);

        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertEquals(1,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(0,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertEquals(1,gameBoard.getDiningRoom().get(PieceColor.BLUE));
    }

    @Test
    @DisplayName("Testing add coins when move student to DiningRoom")
    void addCoins() {
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING,0);
        player.setCoinManager(new CoinManager());
        ProfessorManager professorManager = new ProfessorManager();
        HashMap<Player,GameBoard> map = new HashMap<>();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);
        map.put(player,gameBoard);
        professorManager.setGameboards(map);

        assertEquals(0,player.getCoins());
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()-> gameBoard.addStudent(PieceColor.BLUE));
        assertEquals(3,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(0,gameBoard.getEntrance().get(PieceColor.BLUE));
        assertEquals(3,gameBoard.getDiningRoom().get(PieceColor.BLUE));
        assertEquals(1,player.getCoins());
    }

    @Test
    @DisplayName("Testing set professor")
    void checkProfessor(){
        Player player1 = new Player("Dennis",TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard1 = new GameBoard(player1,8,professorManager,7);
        GameBoard gameBoard2 = new GameBoard(player2,8, professorManager,7);
        HashMap<Player,GameBoard> map = new HashMap<>();
        map.put(player1,gameBoard1);
        map.put(player2,gameBoard2);
        professorManager.setGameboards(map);

        assertDoesNotThrow(()-> gameBoard1.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard1.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(player1,professorManager.getOwner(PieceColor.BLUE));

        assertDoesNotThrow(()-> gameBoard2.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard2.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(player1,professorManager.getOwner(PieceColor.BLUE));

        assertDoesNotThrow(()-> gameBoard2.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->gameBoard2.moveStudentToDiningRoom(PieceColor.BLUE));
        assertEquals(player2,professorManager.getOwner(PieceColor.BLUE));
    }

    /**
     * Tests getting the number of students from the dining room
     */
    @Test
    public void getDiningRoomNumOfStud(){
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);

        assertEquals(0, gameBoard.getDiningRoomNumOfStud());

        gameBoard.getDiningRoom().replace(PieceColor.PINK,3);
        assertEquals(3,gameBoard.getDiningRoomNumOfStud());
    }

    @Test
    public void getEntranceNumOfStud(){
        Player player = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
        ProfessorManager professorManager = new ProfessorManager();
        GameBoard gameBoard = new GameBoard(player,8,professorManager,7);

        assertEquals(0, gameBoard.getEntranceNumOfStud());

        gameBoard.getEntrance().replace(PieceColor.YELLOW,2);
        assertEquals(2,gameBoard.getEntranceNumOfStud());
    }

}