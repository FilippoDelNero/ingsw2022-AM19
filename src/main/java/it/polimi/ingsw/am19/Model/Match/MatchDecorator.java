package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.*;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.List;
import java.util.Map;

/**
 * Class for wrapping an AbstractMatch and adding some extra properties
 */
public class MatchDecorator implements Match{
    /**
     * Saves a reference to the wrapped AbstractMatch
     */
    AbstractMatch wrappedMatch;

    /**
     * Builds a new MatchDecorator with a wrapped AbstractMatch
     * @param match is the AbstractMatch to be wrapped
     */
    public MatchDecorator(AbstractMatch match){
        this.wrappedMatch = match;
    }

    /**
     * Calls the initializeMatch() method of the wrapped AbstractMatch
     */
    @Override
    public void initializeMatch() {
        wrappedMatch.initializeMatch();
    }

    /**
     * Adds a player to the wrapped AbstractMatch
     * @param player represents the Player to add
     */
    @Override
    public void addPlayer(Player player) {
        wrappedMatch.addPlayer(player);
    }

    /**
     * Calls the getCurrPayer() method of the wrapped AbstractMatch
     * @return the currentPlayer of the wrapped AbstractMatch
     */
    @Override
    public Player getCurrPlayer() {
        return wrappedMatch.getCurrPlayer();
    }

    /**
     * Calls the setCurrPayer() method of the wrapped AbstractMatch
     * @param player the player that will perform its turn
     */
    @Override
    public void setCurrPlayer(Player player) {
        wrappedMatch.setCurrPlayer(player);
    }

    /**
     * Calls the updatePlanningPhaseOrder() method of the wrapped AbstractMatch
     */
    @Override
    public void updatePlanningPhaseOrder() {
        wrappedMatch.updatePlanningPhaseOrder();
    }

    /**
     * Calls the refillClouds() method of the wrapped AbstractMatch
     * @throws TooManyStudentsException when trying to add more students than the actual capacity
     */
    @Override
    public void refillClouds() throws TooManyStudentsException {
        wrappedMatch.refillClouds();
    }

    /**
     * Calls the moveStudent() method of the wrapped AbstractMatch
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     */
    @Override
    public void moveStudent(PieceColor color, MoveStudent from, MoveStudent to) throws NoSuchColorException, TooManyStudentsException {
        wrappedMatch.moveStudent(color,from,to);
    }

    /**
     * Calls the useHelperCard() method of the wrapped AbstractMatch
     * @param helperCard represents the chosen HelperCard
     * @throws UnavailableCardException when trying to use an HelperCard that is not available in the deck
     * @throws IllegalCardOptionException when a card is chosen but it has been played by another Player and could have been replaced by another unused card
     */
    @Override
    public void useHelperCard(HelperCard helperCard) throws UnavailableCardException, IllegalCardOptionException {
        wrappedMatch.useHelperCard(helperCard);
    }

    /**
     * Calls the moveMotherNature() method of the wrapped AbstractMatch
     * @param steps the numbers of step you want to move Mother Nature of
     * @throws IllegalNumOfStepsException Exception thrown when trying to move mother Nature counter clock wise or when trying to make it not move at all
     */
    @Override
    public void moveMotherNature(int steps) throws IllegalNumOfStepsException {
        wrappedMatch.moveMotherNature(steps);
    }

    /**
     * Calls the moveStudentToDiningRoom() method of the wrapped AbstractMatch
     * @param color the student's color you want to move
     * @throws NoSuchColorException when a student of the specified PieceColor is not available
     * @throws TooManyStudentsException when trying to add an extra student on an already full object
     */
    @Override
    public void moveStudentToDiningRoom(PieceColor color) throws NoSuchColorException, TooManyStudentsException {
        wrappedMatch.moveStudentToDiningRoom(color);
    }

    /**
     * Removes all the HelperCards from the List of the already used ones in the previous turn
     */
    @Override
    public void resetAlreadyPlayedCards() {
        wrappedMatch.resetAlreadyPlayedCards();
    }

    /**
     * Sets the color's tower for a player of the wrapped AbstractMatch
     * @param towerColor the color chosen by a player
     * @param player the player choosing said color
     */
    @Override
    public void setTowerColors(TowerColor towerColor, Player player) {
        wrappedMatch.setTowerColors(towerColor,player);
    }

    /**
     * SetA the wizard family for a player of the wrapped AbstractMatch
     * @param wizardFamily the wizard chosen by a player
     * @param player the player choosing said wizard
     */
    @Override
    public void setWizardFamily(WizardFamily wizardFamily, Player player) {
        wrappedMatch.setWizardFamily(wizardFamily,player);
    }

    /**
     * Setter for the wrapped AbstractMatch's alreadyPlayedCards attribute
     * @param alreadyPlayedCards a list containing the helper cards played during the current turn
     */
    @Override
    public void setAlreadyPlayedCards(List<HelperCard> alreadyPlayedCards) {
        wrappedMatch.setAlreadyPlayedCards(alreadyPlayedCards);
    }

    /**
     * Getter for the number of players of the wrapped AbstractMatch
     * @return the number of player currently playing
     */
    @Override
    public int getNumOfPlayers() {
        return wrappedMatch.getNumOfPlayers();
    }

    /**
     * getter for planningPhaseOrder
     * @return the players in the order in which they'll play the next planning phase
     */
    @Override
    public List<Player> getPlanningPhaseOrder() {
        return wrappedMatch.getPlanningPhaseOrder();
    }

    /**
     * getter for wrapped AbstractMatch's actionPhaseOrder
     * @return the players in the order in which they'll play the next action phase
     */
    @Override
    public List<Player> getActionPhaseOrder() {
        return wrappedMatch.getActionPhaseOrder();
    }

    /**
     * getter for wrapped AbstractMatch's gameBoards attribute
     * @return a map that link each player to its game-board
     */
    @Override
    public Map<Player, GameBoard> getGameBoards() {
        return wrappedMatch.getGameBoards();
    }

    /**
     * getter for wrapped AbstractMatch's the clouds attribute
     * @return the list containing all the clouds in the game
     */
    @Override
    public List<Cloud> getClouds() {
        return wrappedMatch.getClouds();
    }

    /**
     * getter for wrapped AbstractMatch's the islandManager attribute
     * @return a reference to the islandManager
     */
    @Override
    public IslandManager getIslandManager() {
        return wrappedMatch.getIslandManager();
    }

    /**
     * getter for the wrapped AbstractMatch's bag attribute
     * @return a reference to the bag
     */
    @Override
    public Bag getBag() {
        return wrappedMatch.getBag();
    }

    /**
     * getter for the wrapped AbstractMatch's MotherNature attribute
     * @return a reference to the motherNature
     */
    @Override
    public MotherNature getMotherNature() {
        return wrappedMatch.getMotherNature();
    }

    /**
     * getter for the wrapped AbstractMatch's professorManager attribute
     * @return a reference to the professorManager
     */
    @Override
    public ProfessorManager getProfessorManager() {
        return wrappedMatch.getProfessorManager();
    }

    /**
     * getter for the wrapped AbstractMatch's wizardFamilies attribute
     * @return a list containing the remaining wizard families in the game
     */
    @Override
    public List<WizardFamily> getWizardFamilies() {
        return wrappedMatch.getWizardFamilies();
    }

    /**
     * getter for the wrapped AbstractMatch's towerColors attribute
     * @return a list containing the remaining tower's colors in the game
     */
    @Override
    public List<TowerColor> getTowerColors() {
        return wrappedMatch.getTowerColors();
    }

    /**
     * getter for the wrapped AbstractMatch's alreadyPlayedCards attribute
     * @return the list containing the cards played in a given turn
     */
    @Override
    public List<HelperCard> getAlreadyPlayedCards() {
        return wrappedMatch.getAlreadyPlayedCards();
    }

    /**
     * Return true if final round conditions previously occurred in the wrapped match
     * @return true if final round conditions previously occurred in the wrapped match
     */
    @Override
    public boolean isFinalRound() {
        return wrappedMatch.isFinalRound();
    }

    /**
     * Computes the winner, if there was no winner
     * @return a list of winners. The list contains more than one Player, in case the match ended in a draw, otherwise it contains a single winning Player
     */
    @Override
    public List<Player> getWinner() {
        return wrappedMatch.getWinner();
    }

    /**
     * Returns a reference to the wrapped AbstractMatch
     * @return a reference to the wrapped AbstractMatch
     */
    public AbstractMatch getWrappedMatch() {
        return wrappedMatch;
    }
}
