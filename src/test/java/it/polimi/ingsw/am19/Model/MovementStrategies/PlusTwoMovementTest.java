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
 * Tests methods from PlusTwoMovement Class
 */
class PlusTwoMovementTest {
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