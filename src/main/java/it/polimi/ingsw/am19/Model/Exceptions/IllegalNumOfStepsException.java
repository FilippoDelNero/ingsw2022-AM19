package it.polimi.ingsw.am19.Model.Exceptions;

/**
 * Exception thrown when trying to move mother Nature counter clock wise or when trying to make it not move at all
 */
public class IllegalNumOfStepsException extends Exception{
    private int numOfSteps;
    public IllegalNumOfStepsException() {
    }

    public IllegalNumOfStepsException(String message) {
        super(message);
    }

    /**
     * @param message represents a description of the problem that caused the exception
     * @param numOfSteps represents the illegal number of steps that caused the exception
     */
    public IllegalNumOfStepsException(String message, int numOfSteps) {
        super(message);
        this.numOfSteps = numOfSteps;
    }

    public IllegalNumOfStepsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNumOfStepsException(Throwable cause) {
        super(cause);
    }
    /**
     * Returns the illegal number of steps that caused the exception
     * @return the illegal number of steps that caused the exception
     */
    int getNumOfSteps(){
        return this.numOfSteps;
    }
}
