package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.*;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.List;
import java.util.Map;

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
     * @throws TooManyStudentsException when trying to add more students than the actual capacity
     */
    void refillClouds() throws TooManyStudentsException;

    /**
     * Moves a student of a specified PieceColor from a component to another
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     * @throws NoSuchColorException when trying to move a student of a certain PieceColor from a place that does not contain any student of that PieceColor
     * @throws TooManyStudentsException when trying to move a student on an place tha is running out of space
     */
    void moveStudent(PieceColor color, MoveStudent from, MoveStudent to) throws NoSuchColorException, TooManyStudentsException;

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
     * @throws IllegalNumOfStepsException the number of steps in either less than 0 or more than than what allowed by the card
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

    /**
     * Removes all the HelperCards from the List of the already used ones in the previous turn
     */
    void resetAlreadyPlayedCards();

    /**
     * set the color's tower for a player
     * @param towerColor the color chosen by a player
     * @param player the player choosing said color
     */
    void setTowerColors(TowerColor towerColor, Player player);

    /**
     * set the wizard family for a player
     * @param wizardFamily the wizard chosen by a player
     * @param player the player choosing said wizard
     */
    void setWizardFamily(WizardFamily wizardFamily, Player player);

    /**
     * setter for the alreadyPlayedCards attribute
     * @param alreadyPlayedCards a list containing the helper cards played during the current turn
     */
    void setAlreadyPlayedCards(List<HelperCard> alreadyPlayedCards);

    /**
     * getter for number of players
     * @return the number of player currently playing
     */
    int getNumOfPlayers();

    /**
     * getter for planningPhaseOrder
     * @return the players in the order in which they'll play the next planning phase
     */
    List<Player> getPlanningPhaseOrder();

    /**
     * getter for actionPhaseOrder
     * @return the players in the order in which they'll play the next action phase
     */
    List<Player> getActionPhaseOrder();

    /**
     * getter for gameBoards attribute
     * @return a map that link each player to its game-board
     */
    Map<Player, GameBoard> getGameBoards();

    /**
     * getter for the clouds attribute
     * @return the list containing all the clouds in the game
     */
    List<Cloud> getClouds();

    /**
     * getter for the islandManager attribute
     * @return a reference to the islandManager
     */
    IslandManager getIslandManager();

    /**
     * getter for the bag attribute
     * @return a reference to the bag
     */
    Bag getBag();

    /**
     * getter for the motherNature attribute
     * @return a reference to the motherNature
     */
    MotherNature getMotherNature();

    /**
     * getter for the professorManager attribute
     * @return a reference to the professorManager
     */
    ProfessorManager getProfessorManager();

    /**
     * getter for the wizardFamilies attribute
     * @return a list containing the remaining wizard families in the game
     */
    List<WizardFamily> getWizardFamilies();

    /**
     * getter for the towerColors attribute
     * @return a list containing the remaining tower's colors in the game
     */
    List<TowerColor> getTowerColors();

    /**
     * getter for the alreadyPlayedCards attribute
     * @return the list containing the cards played in a given turn
     */
    List<HelperCard> getAlreadyPlayedCards();

    /**
     * Return true if final round conditions previously occurred
     * @return true if final round conditions previously occurred
     */
    boolean isFinalRound();

    /**
     * Determines who is the winner of the game and returns it
     * @return the winner of the game, null if a winner hasn't been chosen yet
     */
    List<Player> getWinner();

    Player getPlayerByNickname(String nickname);

    List<Integer> getNonEmptyClouds();

    void setAllObservers();
}
