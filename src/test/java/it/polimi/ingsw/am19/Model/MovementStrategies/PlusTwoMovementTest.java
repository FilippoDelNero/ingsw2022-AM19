package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.Island;
import it.polimi.ingsw.am19.Model.IslandManager;
import it.polimi.ingsw.am19.Model.ProfessorManager;
import org.junit.jupiter.api.Test;

import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods from PlusTwoMovement Class
 */
class PlusTwoMovementTest {
    /**
     * Tests the Strategy for moving two extra steps
     */
    @Test
    void testPlusTwoMovement() {
        MovementStrategy movementStrategy = new PlusTwoMovement();
        int steps = 3;
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);
        ListIterator<Island> iterator = islandManager.getIterator();

        Island start = iterator.next();
        iterator.next();
        iterator.next();
        Island end = iterator.next();
        iterator.next();
        Island endPlusTwoSteps = iterator.next();

        assertSame(endPlusTwoSteps, movementStrategy.move(steps,start,iterator));
    }
}