package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.Island;
import it.polimi.ingsw.am19.Model.IslandManager;
import it.polimi.ingsw.am19.Model.MotherNature;
import it.polimi.ingsw.am19.Model.ProfessorManager;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods from StandardMovement Class
 */
class StandardMovementTest {
    /**
     * Tests the default Strategy for moving
     */
    @Test
    void testStandardMovement() {
        MovementStrategy movementStrategy = new StandardMovement();
        int steps = 3;
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);
        ListIterator<Island> iterator = islandManager.getIterator();

        Island start = iterator.next();
        iterator.next();
        iterator.next();
        Island end = iterator.next();

        assertSame(end, movementStrategy.move(steps,start,iterator));
    }
}