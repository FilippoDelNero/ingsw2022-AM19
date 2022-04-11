package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InternalMoveStrategy.InternalMoveStrategy;
import it.polimi.ingsw.am19.Model.InternalMoveStrategy.StandardMove;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.HashMap;

/**
 * Class for manage the single GameBoard of each player
 */
public class GameBoard implements MoveStudent {
    /**
     * References to the player of this GameBoard
     */
    private final Player player;

    /**
     * Map for the student in DiningRoom
     */
    private final HashMap<PieceColor,Integer> entrance;

    /**
     * Map for the student in DiningRoom
     */
    private final HashMap<PieceColor,Integer> diningRoom;

    /**
     * Num of the towers available and not assigned to any Island
     */
    private int numOfTowers;

    /**
     * Max num of Tower for this match(6 or 8)
     */
    private final int maxNumOfTowers;

    /**
     * Max num of student for each color in DiningRoom
     */
    private static final int maxDiningRoomStudent = 10;

    /**
     * Num max of student at the Entrance
     */
    private final int maxEntranceStudent;
    /**
     * Strategy for manage the CharacterCard that allow the move from DiningRoom to Entrance
     */
    private InternalMoveStrategy moveStrategy;

    /**
     * References to ProfessorManager
     */
    private final ProfessorManager professor;

    /**
     * Constructor for a GameBoard
     * @param player the player owner of this GameBoard
     * @param numOfTowers set the max numOfTowers for this player (6 or 8)
     * @param professor references to Professor Manager to change the owner of the various Professor when needed
     */
    public GameBoard(Player player, int numOfTowers, ProfessorManager professor, int maxEntranceStudent) {
        this.player = player;
        this.numOfTowers = numOfTowers;
        this.maxNumOfTowers=numOfTowers;
        this.professor = professor;
        this.moveStrategy = new StandardMove();
        this.maxEntranceStudent = maxEntranceStudent;
        HashMap<PieceColor,Integer> entrance = new HashMap<>();
        HashMap<PieceColor,Integer> diningRoom = new HashMap<>();
        for(PieceColor color: PieceColor.values()){
            entrance.put(color,0);
            diningRoom.put(color,0);
        }
        this.entrance=entrance;
        this.diningRoom=diningRoom;
    }

    /**
     * Getter for the entrance
     * @return HashMap with the num of student for each color
     */
    public HashMap<PieceColor, Integer> getEntrance() {
        return entrance;
    }

    /**
     * Getter for the DiningRoom
     * @return HashMap with the num of student for each color
     */
    public HashMap<PieceColor, Integer> getDiningRoom() {
        return diningRoom;
    }

    /**
     * Getter for the num of Towers available
     * @return the num of Towers in the GameBoard
     */
    public int getNumOfTowers() {
        return numOfTowers;
    }

    /**
     * Getter for the player, used to add coins from standard Strategy of InternalMove
     * @return the player instance associated with the gameBoard
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for the professor manager
     * @return the professor manager instance
     */
    public ProfessorManager getProfessor() {
        return professor;
    }

    /**
     * Setter to add or subtract the num of Towers available
     * @param numOfTowers num of tower to add (or subtract)
     */
    public void setNumOfTowers(int numOfTowers) {
        /*if(newValue>maxNumOfTowers)
            throw new TooManyTowersException("The number of tower cannot be over " + maxNumOfTowers);*/
        this.numOfTowers = this.numOfTowers + numOfTowers;
    }

    /**
     * Setter for the strategy to move student from Entrance to DiningRoom and vice versa
     * @param moveStrategy the strategy to set
     */
    public void setMoveStrategy(InternalMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    /**
     * Method to add student in the Entrance
     * @param color Piece color of the student that has to be added
     * @throws TooManyStudentsException exception if we try to exceed the MaxEntranceStudent
     * @throws IllegalArgumentException when trying to put null as PieceColor parameter
     */
    @Override
    public void addStudent(PieceColor color) throws TooManyStudentsException, IllegalArgumentException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                int numStudent=0;
                for(PieceColor color1:PieceColor.values())
                    numStudent+=getEntrance().get(color1);
                if (numStudent == maxEntranceStudent)
                    throw new TooManyStudentsException("GameBoard cannot host more than " + maxEntranceStudent);
                else {
                    Integer oldValue = entrance.get(color);
                    entrance.replace(color, oldValue + 1);
                }
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }

    /**
     * Method to remove Student in the Entrance
     * @param color Piece color of the student that has to be removed
     * @throws NoSuchColorException when trying to remove a student of a color not present on the Entrance
     * @throws IllegalArgumentException when trying to put null as PieceColor parameter
     */
    @Override
    public void removeStudent(PieceColor color) throws NoSuchColorException, IllegalArgumentException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                Integer oldValue = entrance.get(color);
                if (oldValue > 0) {
                    entrance.replace(color, oldValue - 1);
                } else
                    throw new NoSuchColorException("Unable to remove a " + color + " student from Entrance. There's no student of the specified color");
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }
    //TODO PERCHÈ TIRA LA INSUFFICIENT_COIN_EXCEPTION?
    /**
     * Method to move student from entrance to DiningHall, using a strategy pattern
     * @param color the student's color to move
     * @throws NoSuchColorException when we pass an unexpected color
     * @throws TooManyStudentsException when we try to add the 11th student of a color
     */
    public void moveStudentToDiningRoom(PieceColor color) throws NoSuchColorException, TooManyStudentsException{
        moveStrategy.moveStudentToDiningRoom(this, color, maxEntranceStudent,maxDiningRoomStudent);
    }

    public int getDiningRoomNumOfStud(){
        int tot = 0;
        for (PieceColor color: diningRoom.keySet()){
            tot += diningRoom.get(color);
        }
        return tot;
    }

    public int getEntranceNumOfStud(){
        int tot = 0;
        for (PieceColor color: entrance.keySet()){
            tot += entrance.get(color);
        }
        return tot;
    }
}
