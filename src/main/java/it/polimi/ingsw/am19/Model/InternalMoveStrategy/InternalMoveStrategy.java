package it.polimi.ingsw.am19.Model.InternalMoveStrategy;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.PieceColor;

import java.util.HashMap;

/**
 * Interface for the strategy of moving student from entrance to dining room and vice versa
 */
public interface InternalMoveStrategy {
    void moveStudentToDiningRoom(HashMap<PieceColor,Integer> entrance, HashMap<PieceColor,Integer> diningRoom, PieceColor color, int maxStudentInEntrance, int maxStudentInDiningRoom) throws NoSuchColorException, TooManyStudentsException;
}
