package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods from StandardMovement Class
 */
class StandardMovementTest {
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
     * Tests the default Strategy for moving
     */
    @Test
    void testStandardMovement() {
        MovementStrategy movementStrategy = new StandardMovement();
        int steps = 3;
        int maxSteps = 5;
        assertTrue(movementStrategy.check(steps, maxSteps));
    }
}