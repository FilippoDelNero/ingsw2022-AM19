package it.polimi.ingsw.am19.Model.InternalMoveStrategy;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.io.Serializable;

/**
 * Standard move strategy to move student from entrance to diningHall
 */
public class StandardMove implements InternalMoveStrategy, Serializable {
    /**
     * Standard strategy
     * @param gameBoard the gameBoard to manage
     * @param color the student's color to move
     * @param maxStudentInEntrance max num of Student hospitable in the entrance
     * @param maxStudentInDiningRoom max num of Student hospitable in the diningHall
     * @throws NoSuchColorException if there isn't student of this color in the entrance
     * @throws TooManyStudentsException if the diningHall has max number of the color if we try to add
     */
    @Override
    public void moveStudentToDiningRoom(GameBoard gameBoard, PieceColor color, int maxStudentInEntrance, int maxStudentInDiningRoom) throws NoSuchColorException, TooManyStudentsException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                Integer oldValue = gameBoard.getEntrance().get(color);
                Integer newValue;
                if (oldValue > 0) {
                    gameBoard.getEntrance().replace(color, oldValue - 1);
                    int studentInDiningRoom = gameBoard.getDiningRoom().get(color);
                    if (studentInDiningRoom < maxStudentInDiningRoom){
                        gameBoard.getDiningRoom().replace(color,studentInDiningRoom + 1);
                        newValue=gameBoard.getDiningRoom().get(color);
                        gameBoard.getProfessor().checkProfessor(color, gameBoard.getPlayer());
                        if(newValue % 3 == 0 && gameBoard.getPlayer().getCoins()!=null)
                            gameBoard.getPlayer().addCoins(1);
                    }
                    else
                        throw new TooManyStudentsException("You already have 10 student of " + color + " in your DiningRoom");
                } else
                    throw new NoSuchColorException("Unable to remove a " + color + " student from Entrance. There's no student of the specified color");
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }
}
