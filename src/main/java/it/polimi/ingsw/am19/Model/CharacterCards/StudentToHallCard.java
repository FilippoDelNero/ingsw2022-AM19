package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.Match;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Card with 4 student. You can take one of this and put it in your diningRoom
 */
public class StudentToHallCard extends AbstractCharacterCard implements MoveStudent {
    /**
     * References to the match
     */
    private final AbstractMatch match;

    /**
     * The player that use card in this round
     */
    private Player currentPlayer;

    /**
     * The gameBoard of the current player
     */
    private GameBoard gameboard;

    /**
     * References to the bag
     */
    private final Bag bag;

    /**
     * Map with the student on this card
     */
    private final HashMap<PieceColor,Integer> students;

    /**
     * Max num of student hospitable
     */
    private final int maxNumOfStudents = 4;

    /**
     * Current num of student on the card
     */
    private int currNumOfStudents;

    public StudentToHallCard(AbstractMatch match) {
        super(Character.PRINCESS);
        this.match = match;
        this.currentPlayer = null;
        this.gameboard = null;
        this.bag = match.getBag();
        this.students= new HashMap<>();
        for(PieceColor color: PieceColor.values())
            students.put(color,0);
    }

    /**
     * Getter Match
     * @return the match references
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Add student on the student map of the card
     * @param color Piece color of the student that has to be added
     * @throws TooManyStudentsException if we exceed the MaxNumOfStudent
     * @throws IllegalArgumentException if we pass an unexpected color
     */
    @Override
    public void addStudent(PieceColor color) throws TooManyStudentsException, IllegalArgumentException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                if (currNumOfStudents == maxNumOfStudents)
                    throw new TooManyStudentsException("This card cannot host more than 4 student");
                else {
                    this.currNumOfStudents++;
                    Integer oldValue = students.get(color);
                    students.replace(color, oldValue + 1);
                }
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }

    /**
     * Remove student from the map of this card
     * @param color Piece color of the student that has to be removed
     * @throws NoSuchColorException if there isn't a student of tis color on the card
     * @throws IllegalArgumentException if we pass an unexpected color
     */
    @Override
    public void removeStudent(PieceColor color) throws NoSuchColorException, IllegalArgumentException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                Integer oldValue = students.get(color);
                if (oldValue > 0) {
                    students.replace(color, oldValue - 1);
                    currNumOfStudents--;
                } else
                    throw new NoSuchColorException("Unable to remove a " + color + " student from this Card. There's no student of the specified color");
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }

    }

    /**
     * Fills the student map withdrawing from the bag
     */
    @Override
    public void initialAction() {
        for(int i =0;i<4;i++) {
            if(!bag.isEmpty()){
                try {
                    addStudent(bag.drawStudent());
                } catch (TooManyStudentsException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Move the student to the diningRoom of the current player. After, draw a new student to put on the card
     * @param island null in this case
     * @param color the color of student to move from card to the dining room
     * @param pieceColorList null in this case
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);
        this.currentPlayer = match.getCurrPlayer();
        this.gameboard = match.getGameBoards().get(currentPlayer);

        //check parameter
        if (students.get(color)==0)
            throw new NoSuchColorException("No "+ color + " student present on the card");

        // check if there's a free slot of this color in the dining room
        int oldValue = gameboard.getDiningRoom().get(color);
        if(oldValue==10)
            throw new TooManyStudentsException("You cannot add a " + color + "student. You already have 10 " + color + " students in your hall");

        removeStudent(color);
        gameboard.getDiningRoom().replace(color,oldValue+1);

        //add a student only if bag isn't empty
        if(!bag.isEmpty()){
            try {
                addStudent(bag.drawStudent());
            } catch (TooManyStudentsException e) {
                e.printStackTrace();
            }
        }

        active = false;
    }

    /**
     * Getter for the student map
     * @return the student map of this card
     */
    @Override
    public Map<PieceColor, Integer> getStudents() {
        return this.students;
    }
}
