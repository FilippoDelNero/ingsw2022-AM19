package it.polimi.ingsw.am19.Model.MovementStrategies;

import java.io.Serializable;

/**
 * Class for managing the StandardMovement Strategy
 * checks the validity of the number of steps the player wants to move motherNature of
 */
public class StandardMovement implements MovementStrategy, Serializable {
    /**
     * Defines the check to pass before moving motherNature
     * @param numOfSteps represents the number of steps to take
     * @param maxNumOfSteps the number of steps permitted by the helper card played
     * @return true if numOfSteps is lower than the max number of steps permitted by the helper card played
     */
    @Override
    public boolean check(int numOfSteps, int maxNumOfSteps) {
        return numOfSteps <= (maxNumOfSteps);
    }
}
