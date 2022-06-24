package it.polimi.ingsw.am19.Controller.PhaseManagement;

import it.polimi.ingsw.am19.Network.Message.Message;

/**
 * An Interface for those methods common between all different phases in which a round can be
 */
public interface Phase {
    /**
     * Inspects messages passed from MatchController class
     * @param msg  message passed from MatchController class
     */
    void inspectMessage(Message msg);

    /**
     * Makes the current player perform his phase
     * @param currPlayer is the player that needs to perform the pahse
     */
    void performPhase(String currPlayer);

    /**
     * Initialises a phase
     */
    void initPhase();
}
