package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.ExceedingStudentsPerColorException;
import it.polimi.ingsw.am19.Utilities.Notification;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Observable;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for managing student drawings
 */
public class Bag extends Observable {
    /**
     * Keeps track of the number of existing students of each color in the bag
     */
    private final Map<PieceColor,Integer> numOfStudents;

    /**
     * Keeps a reference to the unique instance of the Bag Class
     */
    private static Bag bagInstance;

    /**
     * Tells how many students of each color should the bag host at maximum
     */
    private final int maxStudentsPerColor;

    /**
     * Initializes the Bag Class instance with no students of each color
     */
    private Bag(){
        numOfStudents = new HashMap<>();
        maxStudentsPerColor = 26;

         for (PieceColor color: PieceColor.values())
            numOfStudents.put(color, 0);
    }

    public boolean isEmpty(){
        boolean isEmpty;

        for (PieceColor color: numOfStudents.keySet()){
            isEmpty = numOfStudents.get(color) == 0;

            if (!isEmpty)
                return false;
        }
        return true;
    }

    private void removeStudent(PieceColor color){
        switch (color) {
            case GREEN, RED, YELLOW, PINK, BLUE -> {
                Integer oldValue = numOfStudents.get(color);
                numOfStudents.replace(color, oldValue - 1);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + color);
        }
    }

    public int getTotNumOfStudents(){
        int tot = 0;
        for (PieceColor color: numOfStudents.keySet()){
            tot += numOfStudents.get(color);
        }

        return tot;
    }

    private PieceColor randomColorGenerator(){
        Map<PieceColor,Double> weightsByColor = new HashMap<>(); //Keeps track of the weight associated with each color

        for (PieceColor color: numOfStudents.keySet()){
            double weight = (double) numOfStudents.get(color)/getTotNumOfStudents();
            if (weight > 0)
                weightsByColor.put(color, weight);
        }

        double sortedProb = Math.random(); //double between 0 and 1
        double currentProb = 0.0;
        PieceColor chosenColor = null;

        for (PieceColor color: weightsByColor.keySet()){
            currentProb += weightsByColor.get(color);
            if (sortedProb < currentProb){
                chosenColor = color;
                return chosenColor;
            }
        }
     return chosenColor;
    }

    /**
     * Returns the Bag Class instance, if previously missing
     * @return the Bag Class instance
     */
    public static Bag getBagInstance(){
        if (Bag.bagInstance == null)
            Bag.bagInstance = new Bag();

        return Bag.bagInstance;
    }

    /**
     * Draws and deletes a student of a random color from the the bag
     * After successfully removing a student, a check is made on the number of students left
     * If there's one student left, a notification is sent to say that this is the final round
     * @return the color of the student picked up
     */
    public PieceColor drawStudent() {
        if (isEmpty())
            return null;
        else{
            PieceColor chosenColor = randomColorGenerator();
            removeStudent(chosenColor);
            if (isEmpty())
                if (observer != null)
                    observer.notify(Notification.FINAL_ROUND);
            return chosenColor;
        }
    }

    /**
     * Refills the bag with the selected number of students of the specified color
     * @param color represents the color of the students used to fill the bag
     * @param num represents the number of students to fill the bag with
     * @throws ExceedingStudentsPerColorException when more than the fixed maximum number of students per color are added to the bag
     * @throws IllegalArgumentException when a negative number of students or a null PieceColor are passed as arguments
     */

    public void refillWith(PieceColor color, int num) throws ExceedingStudentsPerColorException, IllegalArgumentException {
        switch (color) {
            case GREEN:
            case RED:
            case YELLOW:
            case PINK:
            case BLUE:
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + color);
        }

        if (num < 0)
            throw new IllegalArgumentException("The number of students to add cannot be a negative");

        int newValue = numOfStudents.get(color) + num;
        if (newValue > this.maxStudentsPerColor)
            throw new ExceedingStudentsPerColorException("You added too many " + color + " students in the bag", color);
        else
            numOfStudents.replace(color,newValue);
    }

    public void removeAll() {
        while(!isEmpty())
            bagInstance.drawStudent();
    }
}
