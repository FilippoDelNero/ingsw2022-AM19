package it.polimi.ingsw.am19.Model.Exceptions;

/**
 * Exception thrown when a Player is trying to pay an amount of coins for playing a CharacterCard, but he has not enough coins to do it
 */
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
