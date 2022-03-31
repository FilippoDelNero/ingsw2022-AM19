package it.polimi.ingsw.am19.Model;

/**
 * Interface declaring methods fot adding and removing a students of a specified color
 */
public interface MoveStudent {
    /**
     * Adds a student of a specified color
     * @param color Piece color of the student that has to be added
     * @throws TooManyStudentsException when the trying to add an extra student on an already full object
     */
    void addStudent(PieceColor color) throws TooManyStudentsException;

    /**
     * Removes a student of a specified color
     * @param color Piece color of the student that has to be removed
     * @throws NoSuchColorException when trying to remove a student of a color that is not actually available on a certain object
     */
    void removeStudent(PieceColor color) throws NoSuchColorException;
}
