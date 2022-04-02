package it.polimi.ingsw.am19.Model.Exceptions;

import it.polimi.ingsw.am19.Model.PieceColor;

/**
 * Exception thrown when trying to add an extra student on an already full object
 */
public class TooManyStudentsException extends Exception{

    public TooManyStudentsException() {
    }

    public TooManyStudentsException(String message) {
        super(message);
    }

    public TooManyStudentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyStudentsException(Throwable cause) {
        super(cause);
    }
}
