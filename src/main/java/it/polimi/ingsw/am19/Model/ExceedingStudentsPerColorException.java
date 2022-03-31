package it.polimi.ingsw.am19.Model;

/**
 * Exception thrown when attempting to increase the number of students of a certain color, but the maximum number has been already reached
 */
public class ExceedingStudentsPerColorException extends Exception{
    public ExceedingStudentsPerColorException() {
    }

    public ExceedingStudentsPerColorException(String message) {
        super(message);
    }

    public ExceedingStudentsPerColorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceedingStudentsPerColorException(Throwable cause) {
        super(cause);
    }

    public ExceedingStudentsPerColorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
