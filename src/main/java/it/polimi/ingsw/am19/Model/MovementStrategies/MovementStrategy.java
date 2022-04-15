package it.polimi.ingsw.am19.Model.MovementStrategies;

/**
 * Interface of the XMovement Strategy
 * checks the validity of the number of steps the player wants to move motherNature of
 */
public interface MovementStrategy {
    /**
     * Defines the check to pass before moving motherNature
     * @param numOfSteps represents the number of steps to take
     * @param maxNumOfSteps the number of steps permitted by the helper card played
     * @return true if numOfSteps is lower than the max number of steps permitted by the helper card played
     */
    boolean check(int numOfSteps, int maxNumOfSteps);
}
