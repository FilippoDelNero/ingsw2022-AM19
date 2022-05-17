package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.MoveStudent;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentToIslandCard extends AbstractCharacterCard implements MoveStudent {

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
    private final int maxNumOFStudents = 4;

    /**
     * Current num of student on the card
     */
    private int currNumOfStudents;

    public StudentToIslandCard(AbstractMatch match) {
        super(Character.MONK);
        this.bag = match.getBag();
        this.students= new HashMap<>();
        for(PieceColor color: PieceColor.values())
            students.put(color,0);
    }



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

    @Override
    public Map<PieceColor, Integer> getStudents() {
        return this.students;
    }

    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);

        //check the presence of a student of the color passed on the card
        if (students.get(color)==0)
            throw new NoSuchColorException("No "+ color + " student present on the card");

        try {
            removeStudent(color);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        island.addStudent(color);

        // refill if bag is not empty
        if(!bag.isEmpty()){
            try {
                addStudent(bag.drawStudent());
            } catch (TooManyStudentsException e) {
                e.printStackTrace();
            }
        }

        active = false;
    }

    @Override
    public void addStudent(PieceColor color) throws TooManyStudentsException, IllegalArgumentException {
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                if (currNumOfStudents == maxNumOFStudents)
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
}
