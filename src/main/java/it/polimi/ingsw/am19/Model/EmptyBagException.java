package it.polimi.ingsw.am19.Model;

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

    public EmptyBagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
