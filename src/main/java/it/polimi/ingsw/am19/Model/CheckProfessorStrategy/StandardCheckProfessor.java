package it.polimi.ingsw.am19.Model.CheckProfessorStrategy;

import java.io.Serializable;

public class StandardCheckProfessor implements CheckProfessorStrategy, Serializable {
    public boolean checkIfChangeIsNeeded(int numberOfChallenger, int numberToBeat) {
        return numberOfChallenger > numberToBeat;
    }
}
