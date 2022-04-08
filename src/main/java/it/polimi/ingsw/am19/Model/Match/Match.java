package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalCardOptionException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.BoardManagement.MoveStudent;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;

/**
 * Interface implemented by all Matches
 */
public interface Match {
    /**
     * Sets up all the game components for a new Match
     */
    public void initializeMatch();

    /**
     * Adds a Player to the current Match
     * @param player represents the Player to add
     */
    public void addPlayer(Player player);

    /**
     * Returns the current Player for the current Match
     * @return the current Player
     */
    public Player getCurrPlayer();

    /**
     * Updates the current Player  for the current Match
     * @param player
     */
    public void setCurrPlayer(Player player);

    /**
     * Updates the order of Players according to their played HelperCard
     */
    public void updatePlanningPhaseOrder();

    /**
     * Refills the Clouds with their maximum capacity
     */
    public void refillClouds() throws EmptyBagException, TooManyStudentsException;

    /**
     * Moves a student of a specified PieceColor from a component to another
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     */
    public void moveStudent(PieceColor color, MoveStudent from, MoveStudent to);

    /**
     * Updates the current player's played HelperCard
     * @param helperCard represents the chosen HelperCard
     */
    public void useHelperCard(HelperCard helperCard, Player player) throws UnavailableCardException, IllegalCardOptionException;
}
