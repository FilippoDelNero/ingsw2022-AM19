package it.polimi.ingsw.am19.Model.InternalMoveStrategy;

import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.GameBoard;
import it.polimi.ingsw.am19.Model.PieceColor;

import java.util.HashMap;

/**
 * Interface for the strategy of moving student from entrance to dining room and vice versa
 */
public interface InternalMoveStrategy {
    void moveStudentToDiningRoom(GameBoard gameBoard, PieceColor color, int maxStudentInEntrance, int maxStudentInDiningRoom) throws NoSuchColorException, TooManyStudentsException, InsufficientCoinException;
}
