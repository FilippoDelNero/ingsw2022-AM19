package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a cloud tile. It can host a prefixed num of students of various colors that can be moved in and out
 */
public class Cloud implements MoveStudent, Serializable {
    /**
     * Keeps track of the number of students of each color on the cloud
     */
    private final Map<PieceColor,Integer> numOfStudents;

    /**
     * Keeps track of each cloud capacity (maximum number of students that can be host in every moment)
     */
    private final int hostableStudents;

    /**
     * Keeps track of the number of students currently populating the cloud
     */
    private int currNumOfStudents;

    /**
     * Constructs a Cloud with an associated capacity and no students on it
     * @param hostableStudents represents the capacity of the Cloud
     */
    public Cloud(int hostableStudents){
        this.hostableStudents = hostableStudents;
        this.numOfStudents = new HashMap<>();
        this.currNumOfStudents = 0;

        for (PieceColor color: PieceColor.values())
            numOfStudents.put(color, 0);

    }

    /**
     * Method for adding a student of a specified color on a CLoud
     * @param color represents the color of the student that has to be added
     * @throws TooManyStudentsException when trying to add a student on a full Cloud
     * @throws IllegalArgumentException when trying to put null in place of the PieceColor of the student that has to be added
     */
    @Override
    public void addStudent (PieceColor color) throws TooManyStudentsException, IllegalArgumentException{
        switch (color) {
            case GREEN:
            case RED:
            case YELLOW:
            case PINK:
            case BLUE:{
                if (currNumOfStudents == hostableStudents)
                    throw new TooManyStudentsException("Cloud cannot host more than " + getNumOfHostableStudents());
                else {
                    this.currNumOfStudents ++;
                    Integer oldValue = numOfStudents.get(color);
                    numOfStudents.replace(color, oldValue + 1);
                }
            }
            break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + color);
        }

    }

    /**
     * Removes a student from the Cloud
     * @param color represents the color of the student that has to be removed
     * @throws NoSuchColorException when trying to remove a student of a color not present on the Cloud
     * @throws IllegalArgumentException en trying to put null in place of the PieceColor of the student that has to be removed
     */
    @Override
    public void removeStudent(PieceColor color) throws NoSuchColorException, IllegalArgumentException{
        switch (color) {
            case GREEN:
            case RED:
            case YELLOW:
            case PINK:
            case BLUE:{
                Integer oldValue = numOfStudents.get(color);
                if (oldValue > 0){
                    numOfStudents.replace(color, oldValue - 1);
                    currNumOfStudents --;
                }
                else
                    throw new NoSuchColorException("Unable to remove a " + color + " student from Cloud. There's no student of the specified color");
            }
            break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }

    /**
     * Gets the current number of students populating the Cloud
     * @return the current number of students populating the Cloud
     */
    public int getCurrNumOfStudents() {
        return this.currNumOfStudents;
    }

    /**
     * Gets a copy of the Map containing the number of students of each possible color
     * @return a copy of the Map containing the number of students of each possible color
     */
    public Map<PieceColor,Integer> getStudents(){
        return Map.copyOf(numOfStudents);
    }

    /**
     * Gets the capacity associated with the CLoud (the maximum number of students that the CLoud can host)
     * @return the maximum number of students that the CLoud can host
     */
    public int getNumOfHostableStudents(){
        return this.hostableStudents;
    }
}
