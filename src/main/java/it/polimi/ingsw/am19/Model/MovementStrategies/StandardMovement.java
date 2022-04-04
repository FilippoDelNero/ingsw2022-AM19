package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.Island;

import java.util.ListIterator;

/**
 * Class for managing the StandardMovement Strategy
 */
public class StandardMovement implements MovementStrategy{
    /**Defines the Strategy used for moving steps
     * @param numOfSteps represents the number of steps to take
     * @param currPosition represents MotherNature current position
     * @param islandsIterator represents an iterator for navigating the archipelago of Islands
     * @return the final position reached at the end of the movement
     */
    @Override
    public Island move(int numOfSteps, Island currPosition, ListIterator<Island> islandsIterator) {
        Island island = islandsIterator.next();

        while(island != currPosition){
            island = islandsIterator.next();
        }

        island.setPresenceOfMotherNature(false);
        Island finalPosition = null;
        for(int steps = 0; steps < numOfSteps; steps++)
            finalPosition = islandsIterator.next();

        finalPosition.setPresenceOfMotherNature(true);
        return finalPosition;
    }
}
