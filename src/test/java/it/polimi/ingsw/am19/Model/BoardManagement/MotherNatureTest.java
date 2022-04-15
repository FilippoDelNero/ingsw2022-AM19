package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalIslandException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalNumOfStepsException;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.MovementStrategies.MovementStrategy;
import it.polimi.ingsw.am19.Model.MovementStrategies.PlusTwoMovement;
import it.polimi.ingsw.am19.Model.MovementStrategies.StandardMovement;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    /**
     * reset the strategy of motherNature at end of this test class
     */
    @AfterAll
    static void resetMNStrategy() {
        MotherNature motherNature = MotherNature.getInstance();
        motherNature.setCurrMovementStrategy(motherNature.getDefaultMovement());
    }

    /**
     * Tests the impossibility of generating multiple MotherNature instances
     */
    @Test
    void getMultipleInstances() {
        MotherNature motherN1 = MotherNature.getInstance();
        MotherNature motherN2 = MotherNature.getInstance();

        assertSame(motherN1,motherN2);
    }

    /**
     * Tests placing MotherNature on an Island currently taking part into the archipelago of Islands
     */
    @Test
    void setLegalCurrPosition() {
        MotherNature motherNature = MotherNature.getInstance();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);

        motherNature.setIslandManager(islandManager);
        Island island1 = islandManager.getIterator().next();

        try {
            //setting the initial position
            motherNature.setCurrPosition(island1);
        } catch (IllegalIslandException e) {
            e.printStackTrace();
            fail();
        }



        assertSame(motherNature.getCurrPosition(),island1);
    }

    /**
     * Tests placing MotherNature on an Island that does not take part into the archipelago of Islands
     */
    @Test
    void setIllegalCurrPosition() {
        MotherNature motherNature = MotherNature.getInstance();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);
        InfluenceStrategy influence = new StandardInfluence();

        motherNature.setIslandManager(islandManager);

        //island not part of the archipelago
        Island illegalIsland = new Island(influence);

        //trying to put MotherNature on an illegal island
        assertThrows(IllegalIslandException.class,
                () ->motherNature.setCurrPosition(illegalIsland));
    }

    /**
     * Tests trying to make MotherNature move two steps
     */
    @Test
    void NoMovement() {
        MotherNature motherNature = MotherNature.getInstance();
        ProfessorManager professorManager= new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);
        motherNature.setIslandManager(islandManager);

        //MotherNature not on the first Island
        assertFalse(islandManager.getIslands().get(0).isMotherNaturePresent());
        try {
            //placing MotherNature on the first Island
            motherNature.setCurrPosition(islandManager.getIterator().next());
        } catch (IllegalIslandException e) {
            e.printStackTrace();
        }
        //MotherNature now on the first Island
        assertTrue(islandManager.getIslands().get(0).isMotherNaturePresent());

        //Trying to move zero steps
        assertThrows(IllegalNumOfStepsException.class,
                () -> motherNature.move(0, 3));

        //MotherNature did not move from its initial position
        assertTrue(islandManager.getIslands().get(0).isMotherNaturePresent());
    }

    /**
     * Tests making mother nature move counter clockwise
     */
    @Test
    void counterClockWiseMovement() {
        MotherNature motherNature = MotherNature.getInstance();
        ProfessorManager professorManager= new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);
        motherNature.setIslandManager(islandManager);

        //MotherNature not on the first Island
        assertFalse(islandManager.getIslands().get(0).isMotherNaturePresent());

        try {
            //placing MotherNature on the first Island
            motherNature.setCurrPosition(islandManager.getIterator().next());
        } catch (IllegalIslandException e) {
            e.printStackTrace();
        }

        //MotherNature now on the first Island
        assertTrue(islandManager.getIslands().get(0).isMotherNaturePresent());

        //Trying to move counter clockwise
        assertThrows(IllegalNumOfStepsException.class,
                () -> motherNature.move(-3, 3));

        //MotherNature did not move from its initial position
        assertTrue(islandManager.getIslands().get(0).isMotherNaturePresent());
        assertFalse(islandManager.getIslands().get(10).isMotherNaturePresent());
    }

    /**
     * Testing MotherNature legal movement
     */
    @Test
    void clockWiseMovement() {

        //--ISLAND PART--
        ProfessorManager manager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(manager);
        islandManager.getIslands().get(0).addStudent(PieceColor.RED);
        islandManager.getIslands().get(1).addStudent(PieceColor.GREEN);

        //--PROFESSOR MANAGER PART--
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

        //--MOTHER NATURE PART
        MotherNature motherNature = MotherNature.getInstance();
        motherNature.setIslandManager(islandManager);
        ListIterator<Island> iterator = motherNature.getIslandManager().getIterator();

        Island initialIsland = iterator.next();

        //MotherNature not on the first Island
        assertFalse(islandManager.getIslands().get(0).isMotherNaturePresent());
        try {
            //placing MotherNature on the first Island
            motherNature.setCurrPosition(initialIsland);
        } catch (IllegalIslandException e) {
            e.printStackTrace();
        }

        //MotherNature now on the first Island
        assertTrue(islandManager.getIslands().get(0).isMotherNaturePresent());

        try {
            //Making MotherNature move 3 steps
            motherNature.move(3, 5);
        } catch (IllegalNumOfStepsException e) {
            e.printStackTrace();
        }

        //MotherNature not on first island any more
        assertFalse(islandManager.getIslands().get(0).isMotherNaturePresent());

        //she is now 3 steps away from the start
        assertTrue(islandManager.getIslands().get(3).isMotherNaturePresent());

        ListIterator<Island> newIterator = motherNature.getIslandManager().getNewIterator();
        newIterator.next();
        newIterator.next();
        newIterator.next();
        assertSame(newIterator.next(), motherNature.getPosition());
    }

    /**
     * Tests getting MotherNature default MovementStrategy
     */
    @Test
    void getDefaultMovement() {
        MotherNature motherNature = MotherNature.getInstance();
        assertTrue(motherNature.getDefaultMovement() instanceof StandardMovement);
    }

    /**
     * Tests getting and setting the current movement strategy
     */
    @Test
    void getCurrMovementStrategy() {
        MotherNature motherNature = MotherNature.getInstance();
        assertTrue(motherNature.getCurrMovementStrategy() instanceof StandardMovement);

        MovementStrategy plusTwoStrategy = new PlusTwoMovement();
        motherNature.setCurrMovementStrategy(plusTwoStrategy);

        assertTrue(motherNature.getCurrMovementStrategy() instanceof PlusTwoMovement);
    }

    /**
     * Tests getting and setting MotherNature's island manager
     */
    @Test
    void getAndSetIslandManager() {
        MotherNature motherNature = MotherNature.getInstance();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);

        motherNature.setIslandManager(islandManager);
        assertSame(motherNature.getIslandManager(),islandManager);
    }
}