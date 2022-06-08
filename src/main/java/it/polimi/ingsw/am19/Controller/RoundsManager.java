package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Controller.PhaseManagement.Phase;

/**
 * Class for managing match's rounds
 */
public class RoundsManager {
    /**
     * Keeps memory of the current Phase
     */
    private Phase currPhase;

    /**
     * Keeps memory of the previous Phase
     */
    private Phase prevPhase;

    /**
     * Is the round's progressive number
     */
    private int roundNum;

    /**
     * Is the maximum number of rounds allowed
     */
    private final int MAX_ROUND_NUM = 10;

    /**
     * Changes the current Phase and updates the previous one
     * @param nextPhase is the next phase
     */
    public void changePhase(Phase nextPhase) {
        this.prevPhase = currPhase;
        this.currPhase = nextPhase;
    }

    /**
     * Returns the previous phase
     * @return the previous phase
     */
    public Phase getPrevPhase() {
        return prevPhase;
    }

    /**
     * Sets the current round number
     * @param roundNum the current round number
     */
    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    /**
     * Increments by one the current round number
     */
    public void incrementRoundsNum(){
        roundNum++;
    }

    /**
     * Returns the current Phase
     * @return the current Phase
     */
    public Phase getCurrPhase() {
        return currPhase;
    }

    /** Returns the current round number
     * * @return the current round number*/
    public int getRoundNum() {
        return roundNum;
    }

    /**
     * Returns the maximum number of rounds allowed
     * @return the maximum number of rounds allowed
     */
    public int getMAX_ROUND_NUM() {
        return MAX_ROUND_NUM;
    }

    /**
     * Returns true if a new round can be played, depending on the current round number
     * @return true if a new round can be played, depending on the current round number
     */
    public boolean hasNextRound(){
        return roundNum < MAX_ROUND_NUM;
    }
}

