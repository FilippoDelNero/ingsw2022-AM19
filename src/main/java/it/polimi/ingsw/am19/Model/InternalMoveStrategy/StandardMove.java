package it.polimi.ingsw.am19.Model.InternalMoveStrategy;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.PieceColor;

import java.util.HashMap;

/**
 * Standard move strategy to move student from entrance to diningHall
 */
public class StandardMove implements InternalMoveStrategy{
    /**
     * Standard strategy
     * @param entrance the gameBoard entrance
     * @param diningRoom the gameBoard DiningHall
     * @param color the student's color to move
     * @param maxStudentInEntrance max num of Student hospitable in the entrance
     * @param maxStudentInDiningRoom max num of Student hospitable in the diningHall
     * @throws NoSuchColorException if there isn't student of this color in the entrance
     * @throws TooManyStudentsException if the diningHall has max number of the color if we try to add
     */
    @Override
    public void moveStudentToDiningRoom(HashMap<PieceColor, Integer> entrance, HashMap<PieceColor, Integer> diningRoom, PieceColor color, int maxStudentInEntrance, int maxStudentInDiningRoom) throws NoSuchColorException, TooManyStudentsException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                Integer oldValue = entrance.get(color);
                if (oldValue > 0) {
                    entrance.replace(color, oldValue - 1);
                    int studentInDiningRoom = diningRoom.get(color);
                    if (studentInDiningRoom < maxStudentInDiningRoom)
                        diningRoom.replace(color,studentInDiningRoom + 1);
                    else
                        throw new TooManyStudentsException("You already have 10 student of " + color + " in your DiningRoom");
                } else
                    throw new NoSuchColorException("Unable to remove a " + color + " student from Entrance. There's no student of the specified color");
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }
}
