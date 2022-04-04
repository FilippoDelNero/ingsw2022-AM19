package it.polimi.ingsw.am19.Model.Exceptions;

/**
 * Exception thrown when trying to put MotherNature on an Island that does not take part into the archipelago of Islands any more
 */
public class IllegalIslandException extends Exception{
    public IllegalIslandException() {
    }

    public IllegalIslandException(String message) {
        super(message);
    }

    public IllegalIslandException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalIslandException(Throwable cause) {
        super(cause);
    }
}
