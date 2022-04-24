package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods from StandardMovement Class
 */
class StandardMovementTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
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