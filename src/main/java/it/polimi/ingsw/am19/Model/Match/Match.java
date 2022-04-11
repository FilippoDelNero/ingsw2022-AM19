package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.Exceptions.*;
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
    void initializeMatch();

    /**
     * Adds a Player to the current Match
     * @param player represents the Player to add
     */
    void addPlayer(Player player);

    /**
     * Returns the current Player for the current Match
     * @return the current Player
     */
    Player getCurrPlayer();

    /**
     * Updates the current Player for the current Match
     * @param player the player that will perform its turn
     */
    void setCurrPlayer(Player player);

    /**
     * Updates the order of Players according to their played HelperCard
     */
    void updatePlanningPhaseOrder();

    /**
     * Refills the Clouds with their maximum capacity
     * @throws EmptyBagException when no more students are available in the Bag
     * @throws TooManyStudentsException when trying to add more students than the actual capacity
     */
    void refillClouds() throws EmptyBagException, TooManyStudentsException;

    /**
     * Moves a student of a specified PieceColor from a component to another
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     */
    void moveStudent(PieceColor color, MoveStudent from, MoveStudent to);

    /**
     * Updates the current player's played HelperCard
     * @param helperCard represents the chosen HelperCard
     * @throws UnavailableCardException when trying to choose an HelperCard that is not available in the deck
     * @throws IllegalCardOptionException thrown when an HelperCard is chosen but it has been played by another Player and could have been replaced by another unused HelperCard
     */
    void useHelperCard(HelperCard helperCard) throws UnavailableCardException, IllegalCardOptionException;

    /**
     * Moves Mother Nature
     * @param steps the numbers of step you want to move Mother Nature of
     * @throws IllegalNumOfStepsException the number of steps in either < 0 or > than what allowed by the card
     */
    void moveMotherNature(int steps) throws IllegalNumOfStepsException;

    /**
     * method to allow the current player to move a student from his game-board's entrance to the dining room
     * @param color the student's color you want to move
     * @throws NoSuchColorException when we pass an unexpected color
     * @throws InsufficientCoinException when we try to add the 11th student of a color
     * @throws TooManyStudentsException ?
     */
    void moveStudentToDiningRoom(PieceColor color) throws NoSuchColorException, InsufficientCoinException, TooManyStudentsException;
}
