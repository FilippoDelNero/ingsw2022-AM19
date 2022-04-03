package it.polimi.ingsw.am19.Model.Exceptions;

public class UnavailableCardException extends Exception{
    public UnavailableCardException() {
    }

    public UnavailableCardException(String message) {
        super(message);
    }

    public UnavailableCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableCardException(Throwable cause) {
        super(cause);
    }

    public UnavailableCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
