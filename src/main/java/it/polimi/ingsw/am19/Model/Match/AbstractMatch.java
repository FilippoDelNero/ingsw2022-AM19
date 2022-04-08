package it.polimi.ingsw.am19.Model.Match;
import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.*;
import it.polimi.ingsw.am19.Model.Utilities.Observable;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.*;

/**
 * Abstract Class that implements all methods shared by different kinds of Matches
 */
public abstract class AbstractMatch extends Observable implements Match {
    /**
     * Number of Players expected
     */
    int numOfPlayers;

    /**
     * Takes a reference to the current player
     */
    Player currPlayer;

    /**
     * Saves all players in the planning phase order
     */
    List<Player> planningPhaseOrder;

    /**
     * Saves all players in the action phase order
     */
    List<Player> actionPhaseOrder;

    /**
     * Maps each Player to his GameBoard
     */
    Map<Player, GameBoard> gameBoards;

    /**
     * Is a List of Clouds
     */
    List<Cloud> clouds;

    /**
     * Saves a reference to the IslandManager
     */
    IslandManager islandManager;

    /**
     * Saves Bag's instance
     */
    Bag bag;

    /**
     * Saves a reference to MotherNature instance
     */
    MotherNature motherNature;

    /**
     *  Saves a reference to the ProfessorManager
     */
    ProfessorManager professorManager;

    /**
     * Is a List of the available WizardFamilies
     */
    final List<WizardFamily> wizardFamilies;
    /**
     * Is a List of the available TowerColors
     */
    final List<TowerColor> towerColors;
    /**
     * Saves the already played HelperCards
     */
    List<HelperCard> alreadyPlayedCards ;

    /**
     * Builds a new Match with the specified number of Players
     * @param num represents the number of Players that will play tha game
     */
    public AbstractMatch(int num) {
        this.numOfPlayers = num;
        this.currPlayer = null;
        this.planningPhaseOrder = new ArrayList<>();
        this.actionPhaseOrder = new ArrayList<>();
        this.gameBoards = new HashMap<>();
        this.clouds = new ArrayList<>();
        this.professorManager = new ProfessorManager();
        this.islandManager = new IslandManager(professorManager);
        this.bag = Bag.getBagInstance();
        this.motherNature = MotherNature.getInstance();
        this.wizardFamilies = new ArrayList<>(Arrays.asList(WizardFamily.values()));
        this.towerColors = new ArrayList<>(Arrays.asList(TowerColor.values()));
        this.alreadyPlayedCards = new ArrayList<>();
    }

    /**
     * Initializes all the game components in order to get ready for the firs planning phase
     */
    @Override
    public abstract void initializeMatch();

    /**
     * Adds a  player to the game
     * @param player represents the Player to add
     */
    @Override
    public void addPlayer(Player player) {
        //TODO THE CONTROLLER SHOULD CHECK THE NICKNAMES TO AVOID DOUBLES
        planningPhaseOrder.add(player);
    }

    /**
     * Returns the current player
     * @return the current player
     */
    @Override
    public Player getCurrPlayer(){
        return currPlayer;
    }

    /**
     * Updates the current player
     * @param player the newest current player to set
     */
    @Override
    public void setCurrPlayer(Player player) {
        this.currPlayer = player;
    }

    /**
     * Reorders the order of the player for the next planning phase
     */
    @Override
    public void updatePlanningPhaseOrder(){
    }

    /**
     * Refills all the Clouds
     * @throws EmptyBagException when there are no students available to put on the Clouds
     * @throws TooManyStudentsException when trying to add more students than the actual Cloud capacity
     */
    @Override
    public void refillClouds() throws EmptyBagException, TooManyStudentsException {
        for (Cloud cloud: clouds){
            for (int i = 0; i < cloud.getNumOfHostableStudents(); i++){
                PieceColor color = bag.drawStudent();
                cloud.addStudent(color);
            }
        }
    }

    /**
     * Moves a student of a specified PieceColor from a place to another
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     */
    //TODO Handle Exception -> idea: if the destination is full -> rollback
    @Override
    public void moveStudent(PieceColor color, MoveStudent from, MoveStudent to) {
        try {
            from.removeStudent(color);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        try {
            to.addStudent(color);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes a player play a new HelperCard
     * @param card is the card to play
     * @param player is the player that will play the card
     * @throws UnavailableCardException when the specified card is not available in the Player's deck
     * @throws IllegalCardOptionException when a card is chosen but it has been played by another Player and could have been replaced by another unused card
     */
    @Override
    public void useHelperCard(HelperCard card, Player player) throws UnavailableCardException, IllegalCardOptionException {
        if (alreadyPlayedCards.contains(card)){
            for (HelperCard helperCard: player.getHelperDeck()) {
                if (!alreadyPlayedCards.contains(helperCard))
                    throw new IllegalCardOptionException("Cannot play a card that has already been played by another player");
            }
        }
        player.useHelperCard(card);
        alreadyPlayedCards.add(card);

        int target = card.getNextRoundOrder();
        ListIterator<Player> iterator = actionPhaseOrder.listIterator();
        if(!actionPhaseOrder.isEmpty()) {
            int index = actionPhaseOrder.size() - 1;
            while (iterator.hasNext()) {
                Player currPlayer = iterator.next();
                if (currPlayer.getCurrentCard().getNextRoundOrder() <= target) {
                    index = iterator.nextIndex();
                }
            }
            actionPhaseOrder.add(index, player);
        }
        else
            actionPhaseOrder.add(0,player);

    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public List<Player> getPlanningPhaseOrder() {
        return planningPhaseOrder;
    }

    public List<Player> getActionPhaseOrder() {
        return actionPhaseOrder;
    }

    public Map<Player, GameBoard> getGameBoards() {
        return gameBoards;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }

    public Bag getBag() {
        return bag;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public ProfessorManager getProfessorManager() {
        return professorManager;
    }

    public void setWizardFamily(WizardFamily wizardFamily,Player player){
        for (Player p: planningPhaseOrder){
            if (p.equals(player)){
                p.setWizardFamily(wizardFamily);
            }
        }
        this.wizardFamilies.remove(wizardFamily);
    }

    public List<WizardFamily> getWizardFamilies(){
        return this.wizardFamilies;
    }

    public List<TowerColor> getTowerColors() {
        return this.towerColors;
    }

    public void setTowerColors(TowerColor towerColor, Player player) {
        for (Player p: planningPhaseOrder){
            if (p.equals(player)){
                p.setTowerColor(towerColor);
            }
        }
        this.towerColors.remove(towerColor);
    }

    public List<HelperCard> getAlreadyPlayedCards() {
        return this.alreadyPlayedCards;
    }

    public void setAlreadyPlayedCards(List<HelperCard> alreadyPlayedCards) {
        this.alreadyPlayedCards = alreadyPlayedCards;
    }

    /**
     * Removes all the HelperCards from the List of the already used ones in the previous turn
     */
    public void resetAlreadyPlayedCards(){
        for (HelperCard card : alreadyPlayedCards){
            alreadyPlayedCards.remove(card);
        }
    }
}