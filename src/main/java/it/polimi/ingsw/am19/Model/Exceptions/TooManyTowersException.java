package it.polimi.ingsw.am19.Model.Exceptions;

public class TooManyTowersException extends Exception{
    public TooManyTowersException() {
    }

    public TooManyTowersException(String message) {
        super(message);
    }

    public TooManyTowersException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyTowersException(Throwable cause) {
        super(cause);
    }

    public TooManyTowersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
