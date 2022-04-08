package it.polimi.ingsw.am19.Model.Exceptions;

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
