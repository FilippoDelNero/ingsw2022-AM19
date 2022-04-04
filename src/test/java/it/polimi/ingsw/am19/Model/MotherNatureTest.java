package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.Exceptions.IllegalIslandException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalNumOfStepsException;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.MovementStrategies.MovementStrategy;
import it.polimi.ingsw.am19.Model.MovementStrategies.PlusTwoMovement;
import it.polimi.ingsw.am19.Model.MovementStrategies.StandardMovement;
import org.junit.jupiter.api.Test;

import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {
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

        //position not assigned yet
        assertNull(motherNature.getCurrPosition());

        try {
            //setting the initial position
            motherNature.setCurrPosition(island1);
        } catch (IllegalIslandException e) {
            e.printStackTrace();
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
                () -> motherNature.move(0));

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
                () -> motherNature.move(-3));

        //MotherNature did not move from its initial position
        assertTrue(islandManager.getIslands().get(0).isMotherNaturePresent());
        assertFalse(islandManager.getIslands().get(10).isMotherNaturePresent());
    }

    /**
     * Testing MotherNature legal movement
     */
    @Test
    void clockWiseMovement() {
        MotherNature motherNature = MotherNature.getInstance();
        ProfessorManager professorManager= new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);
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
            motherNature.move(3);
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