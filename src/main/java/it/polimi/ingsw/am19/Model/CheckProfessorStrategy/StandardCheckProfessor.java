package it.polimi.ingsw.am19.Model.CheckProfessorStrategy;

public class StandardCheckProfessor implements CheckProfessorStrategy{
    public boolean checkIfChangeIsNeeded(int numberOfChallenger, int numberToBeat) {
        return numberOfChallenger > numberToBeat;
    }
}
