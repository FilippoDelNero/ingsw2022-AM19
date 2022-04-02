package it.polimi.ingsw.am19.Model.Exceptions;

public class InsufficientCoinException extends Exception{
    public InsufficientCoinException() {
    }

    public InsufficientCoinException(String message) {
        super(message);
    }

    public InsufficientCoinException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientCoinException(Throwable cause) {
        super(cause);
    }

    public InsufficientCoinException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
