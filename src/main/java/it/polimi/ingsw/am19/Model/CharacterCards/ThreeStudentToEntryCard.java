package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Card with 6 students. You can change 3 student from entrance with the student on the card
 */
public class ThreeStudentToEntryCard extends AbstractCharacterCard implements MoveStudent {

    /**
     * References to the bag
     */
    private final Bag bag;

    /**
     * References to the match
     */
    private final AbstractMatch match;

    /**
     * Map with the student on this card
     */
    private final HashMap<PieceColor,Integer> students;

    /**
     * Max num of student hospitable
     */
    private final int maxNumOFStudents = 6;

    /**
     * Current num of student on the card
     */
    private int currNumOfStudents;

    public ThreeStudentToEntryCard(AbstractMatch match) {
        super(Character.JESTER);
        this.match = match;
        this.bag = match.getBag();
        this.students= new HashMap<>();
        for(PieceColor color: PieceColor.values())
            students.put(color,0);
    }

    @Override
    public void addStudent(PieceColor color) throws TooManyStudentsException, IllegalArgumentException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                if (currNumOfStudents == maxNumOFStudents)
                    throw new TooManyStudentsException("This card cannot host more than 6 student");
                else {
                    this.currNumOfStudents++;
                    Integer oldValue = students.get(color);
                    students.replace(color, oldValue + 1);
                }
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }

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

    @Override
    public void initialAction() {
        for(int i =0;i<6;i++) {
            if(!bag.isEmpty()){
                try {
                    addStudent(bag.drawStudent());
                } catch (TooManyStudentsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Map<PieceColor, Integer> getStudents() {
        return this.students;
    }

    /**
     *
     * @param island null in this card
     * @param color null in this card
     * @param pieceColorList 0, 2 and 4 are the color of the card. 1,3,5 the index of color of the entrance
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);

        Player currentPlayer = match.getCurrPlayer();
        GameBoard gameboard = match.getGameBoards().get(currentPlayer);

        //check parameters
        // check the presence of the color
        HashMap<PieceColor,Integer> studentOnCard = new HashMap<>();
        HashMap<PieceColor,Integer> studentInEntrance = new HashMap<>();
        for(PieceColor color1: PieceColor.values()){
            studentOnCard.put(color1,0);
            studentInEntrance.put(color1,0);
        }
        for(int i=0; i<pieceColorList.size()/2 && i<3 ;i++){
            studentOnCard.put(pieceColorList.get(2*i), studentOnCard.get(pieceColorList.get(2*i))+1);
            studentInEntrance.put(pieceColorList.get(2*i+1), studentInEntrance.get(pieceColorList.get(2*i+1))+1);
        }
        for(PieceColor color1: PieceColor.values()){
            if(studentInEntrance.get(color1) > gameboard.getEntrance().get(color1) || studentOnCard.get(color1) > students.get(color1))
                throw new NoSuchColorException("There isn't a " + color1 +" student in your entry or on the card");
        }


        for(int i=0; i < pieceColorList.size()/2;i++){
            //remove the color from the card
            try {
                removeStudent(pieceColorList.get(2*i));
            } catch (NoSuchColorException e) {
                e.printStackTrace();
            }

            // remove the student from the entrance
            try {
                gameboard.removeStudent(pieceColorList.get(2*i +1));
            } catch (NoSuchColorException e) {
                e.printStackTrace();
            }

            // add the student to the card
            try {
                addStudent(pieceColorList.get(2*i +1));
            } catch (TooManyStudentsException e) {
                e.printStackTrace();
            }

            // add student to GameBoard
            try {
                gameboard.addStudent(pieceColorList.get(2*i));
            } catch (TooManyStudentsException e) {
                e.printStackTrace();
            }
        }
        active = false;
    }
}
