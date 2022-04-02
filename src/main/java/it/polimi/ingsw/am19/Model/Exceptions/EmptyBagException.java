package it.polimi.ingsw.am19.Model.Exceptions;

/**
 * Exception thrown when trying to draw from an empty Bag
 */
public class EmptyBagException extends Exception{
    public EmptyBagException() {
    }

    public EmptyBagException(String message) {
        super(message);
    }

    public EmptyBagException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBagException(Throwable cause) {
        super(cause);
    }
}
