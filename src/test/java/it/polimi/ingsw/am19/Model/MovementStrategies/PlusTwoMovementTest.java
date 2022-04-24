package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods from PlusTwoMovement Class
 */
class PlusTwoMovementTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * Tests the Strategy for moving two extra steps
     */
    @Test
    void testPlusTwoMovement() {
        MovementStrategy movementStrategy = new PlusTwoMovement();
        int steps = 3;
        int maxSteps = 2;

        assertTrue(movementStrategy.check(steps, maxSteps));
    }
}