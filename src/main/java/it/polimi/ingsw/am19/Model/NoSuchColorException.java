package it.polimi.ingsw.am19.Model;

/**
 * Exception thrown when a student of a certain PieceColor is not available
 */
public class NoSuchColorException extends Exception{
    public NoSuchColorException() {
    }

    public NoSuchColorException(String message) {
        super(message);
    }

    public NoSuchColorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchColorException(Throwable cause) {
        super(cause);
    }
}
