package it.polimi.ingsw.am19.Model.MovementStrategies;

/**
 * Class for managing the PlusTwoMovement Strategy
 * checks the validity of the number of steps the player wants to move motherNature of
 */
public class PlusTwoMovement implements MovementStrategy{
    /**
     * Defines the check to pass before moving motherNature, two extra steps are allowed
     * @param numOfSteps represents the number of steps to take
     * @param maxNumOfSteps the number of steps permitted by the helper card played
     * @return true if numOfSteps is lower than the max number of steps permitted by the helper card played + 2
     */
    @Override
    public boolean check(int numOfSteps, int maxNumOfSteps) {
        return numOfSteps <= (maxNumOfSteps + 2);
    }
}
