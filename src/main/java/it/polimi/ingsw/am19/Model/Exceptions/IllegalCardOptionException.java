package it.polimi.ingsw.am19.Model.Exceptions;

/**
 * Exception thrown when an HelperCard is chosen but it has been played by another Player and could have been replaced by another unused HelperCard
 */
public class IllegalCardOptionException extends Exception{
    public IllegalCardOptionException() {
    }

    public IllegalCardOptionException(String message) {
        super(message);
    }

    public IllegalCardOptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCardOptionException(Throwable cause) {
        super(cause);
    }
}
