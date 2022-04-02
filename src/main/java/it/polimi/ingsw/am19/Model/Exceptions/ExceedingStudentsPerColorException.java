package it.polimi.ingsw.am19.Model.Exceptions;

import it.polimi.ingsw.am19.Model.PieceColor;

/**
 * Exception thrown when attempting to increase the number of students of a certain color, but the maximum number has been already reached
 */
public class ExceedingStudentsPerColorException extends Exception{
    /**
     * Keeps track of the color of the students that caused the Exception
     */
    private PieceColor color;

    public ExceedingStudentsPerColorException() {
    }

    public ExceedingStudentsPerColorException(String message) {
        super(message);
    }

    public ExceedingStudentsPerColorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new Exception with a message and a color
     * @param message describes the problem associated with the exception
     * @param color represents the color that caused the exception
     */
    public ExceedingStudentsPerColorException(String message, PieceColor color) {
        super(message);
        this.color = color;
    }

    public ExceedingStudentsPerColorException(Throwable cause) {
        super(cause);
    }

    public PieceColor getColor() {
        return color;
    }
}
