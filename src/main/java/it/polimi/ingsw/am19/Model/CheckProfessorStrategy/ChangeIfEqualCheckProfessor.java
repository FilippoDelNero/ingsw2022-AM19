package it.polimi.ingsw.am19.Model.CheckProfessorStrategy;

import java.io.Serializable;

/**
 * strategy used by the professorManager to check if a professor should change hand
 */
public class ChangeIfEqualCheckProfessor implements CheckProfessorStrategy, Serializable {
    /**
     * method used to check if a professor should be owned by a new player, this will happen if the number of students
     * of the new owner is EQUAL or greater than the one the old owner
     * @param numberOfChallenger the number of students of the professor's color owned by the potential new owner
     * @param numberToBeat the number of students of the professor's color owned by the current professor's owner
     * @return true if the professor should change hand
     */
    public boolean checkIfChangeIsNeeded(int numberOfChallenger, int numberToBeat) {
        return numberOfChallenger >= numberToBeat;
    }
}
