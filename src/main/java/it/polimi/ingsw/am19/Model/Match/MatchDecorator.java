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
     * @throws EmptyBagException @throws EmptyBagException when no more students are available in the Bag
     * @throws TooManyStudentsException when trying to add more students than the actual capacity
     */
    @Override
    public void refillClouds() throws EmptyBagException, TooManyStudentsException {
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

    @Override
    public void resetAlreadyPlayedCards() {
        wrappedMatch.resetAlreadyPlayedCards();
    }

    @Override
    public void setTowerColors(TowerColor towerColor, Player player) {
        wrappedMatch.setTowerColors(towerColor,player);
    }

    @Override
    public void setWizardFamily(WizardFamily wizardFamily, Player player) {
        wrappedMatch.setWizardFamily(wizardFamily,player);
    }

    @Override
    public void setAlreadyPlayedCards(List<HelperCard> alreadyPlayedCards) {
        wrappedMatch.setAlreadyPlayedCards(alreadyPlayedCards);
    }

    @Override
    public int getNumOfPlayers() {
        return wrappedMatch.getNumOfPlayers();
    }

    @Override
    public List<Player> getPlanningPhaseOrder() {
        return wrappedMatch.getPlanningPhaseOrder();
    }

    @Override
    public List<Player> getActionPhaseOrder() {
        return wrappedMatch.getActionPhaseOrder();
    }

    @Override
    public Map<Player, GameBoard> getGameBoards() {
        return wrappedMatch.getGameBoards();
    }

    @Override
    public List<Cloud> getClouds() {
        return wrappedMatch.getClouds();
    }

    @Override
    public IslandManager getIslandManager() {
        return wrappedMatch.getIslandManager();
    }

    @Override
    public Bag getBag() {
        return wrappedMatch.getBag();
    }

    @Override
    public MotherNature getMotherNature() {
        return wrappedMatch.getMotherNature();
    }

    @Override
    public ProfessorManager getProfessorManager() {
        return wrappedMatch.getProfessorManager();
    }

    @Override
    public List<WizardFamily> getWizardFamilies() {
        return wrappedMatch.getWizardFamilies();
    }

    @Override
    public List<TowerColor> getTowerColors() {
        return wrappedMatch.getTowerColors();
    }

    @Override
    public List<HelperCard> getAlreadyPlayedCards() {
        return wrappedMatch.getAlreadyPlayedCards();
    }

    public AbstractMatch getWrappedMatch() {
        return wrappedMatch;
    }
}
