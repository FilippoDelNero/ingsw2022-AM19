package it.polimi.ingsw.am19.Model.Match;
import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.*;
import it.polimi.ingsw.am19.Utilities.Notification;
import it.polimi.ingsw.am19.Utilities.Observable;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Utilities.Observer;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.am19.Utilities.Notification.END_MATCH;

/**
 * Abstract Class that implements all methods shared by different kinds of Matches
 */
public abstract class AbstractMatch extends Observable implements Match, Observer, Serializable {
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
    List<HelperCard> alreadyPlayedCards;

    /**
     * Is true if the final round conditions are met
     */
    boolean finalRound;

    /**
     * Is the winner Player, null if there's no winner
     */
    List<Player> winners;

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
        bag = Bag.getBagInstance();
        this.motherNature = MotherNature.getInstance();
        this.wizardFamilies = new ArrayList<>(Arrays.asList(WizardFamily.values()));
        this.towerColors = new ArrayList<>(Arrays.asList(TowerColor.values()));
        this.alreadyPlayedCards = new ArrayList<>();
        this.finalRound = false;
        this.winners = new ArrayList<>();
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
        if(planningPhaseOrder.size() < numOfPlayers) {
            planningPhaseOrder.add(player);
        }
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
        int firstPlayerIndex = planningPhaseOrder.lastIndexOf(actionPhaseOrder.get(0));
        int whereToInsert = 0;
        for(int i = firstPlayerIndex; i < planningPhaseOrder.size(); i++) {
            Player playerToShift = planningPhaseOrder.remove(i);
            planningPhaseOrder.add(whereToInsert, playerToShift);
            whereToInsert++;
        }
        actionPhaseOrder = new ArrayList<>();
    }

    /**
     * Refills all the Clouds
     * @throws TooManyStudentsException when trying to add more students than the actual Cloud capacity
     */
    @Override
    public void refillClouds() throws TooManyStudentsException {
        for (Cloud cloud: clouds){
            for (int i = 0; i < cloud.getNumOfHostableStudents(); i++){
                if (!bag.isEmpty()) {
                    PieceColor color = bag.drawStudent();
                    cloud.addStudent(color);
                }
            }
        }
        if(bag.isEmpty())
            notifyObservers(Notification.UPDATE_CLOUDS);
    }

    /**
     * Moves a student of a specified PieceColor from a place to another
     * @param color represents the PieceColor of the student to move
     * @param from represents the place where the movement starts
     * @param to represents the final destination of the movement
     * @throws NoSuchColorException when trying to move a student of a certain PieceColor from a place that does not contain any student of that PieceColor
     * @throws TooManyStudentsException when trying to move a student on an place tha is running out of space
     */
    @Override
    public void moveStudent(PieceColor color, MoveStudent from, MoveStudent to) throws NoSuchColorException, TooManyStudentsException {
        try {
            from.removeStudent(color);
        } catch (NoSuchColorException e) {
            throw new NoSuchColorException(e.getMessage(),e.getCause());
        }
        try {
            to.addStudent(color);
        } catch (TooManyStudentsException e) {
            from.addStudent(color);
            throw new TooManyStudentsException(e.getMessage(),e.getCause());
        }
    }

    /**
     * method to allow the current player to move a student from his GameBoard's entrance to the dining room
     * @param color the student's color you want to move
     * @throws NoSuchColorException when we pass an unexpected color
     * @throws TooManyStudentsException when trying to add an extra student to an already full dining room
     */
    public void moveStudentToDiningRoom(PieceColor color) throws NoSuchColorException, TooManyStudentsException {
        GameBoard currPlayerGameBoard = gameBoards.get(currPlayer);
        currPlayerGameBoard.moveStudentToDiningRoom(color);
    }

    /**
     * Makes a player play a new HelperCard
     * @param card is the card to play
     * @throws UnavailableCardException when the specified card is not available in the Player's deck
     * @throws IllegalCardOptionException when a card is chosen but it has been played by another Player and could have been replaced by another unused card
     */
    @Override
    public void useHelperCard(HelperCard card) throws UnavailableCardException, IllegalCardOptionException {
        if (alreadyPlayedCards.contains(card)){
            for (HelperCard helperCard: currPlayer.getHelperDeck()) {
                if (!alreadyPlayedCards.contains(helperCard))
                    throw new IllegalCardOptionException("Cannot play a card that has already been played by another player");
            }
        }
        currPlayer.useHelperCard(card);
        alreadyPlayedCards.add(card);

        int target = card.getNextRoundOrder();
        ListIterator<Player> iterator = actionPhaseOrder.listIterator();
        if(!actionPhaseOrder.isEmpty()) {
            int index = 0;
            while (iterator.hasNext()) {
                Player currPlayer = iterator.next();
                if (currPlayer.getCurrentCard().getNextRoundOrder() <= target) {
                    index = iterator.nextIndex();
                }
            }
            actionPhaseOrder.add(index, currPlayer);
        }
        else
            actionPhaseOrder.add(0,currPlayer);

    }

    /**
     * Removes all the HelperCards from the List of the already used ones in the previous turn
     */
    @Override
    public void resetAlreadyPlayedCards(){
        int size = alreadyPlayedCards.size();
        if (size > 0) {
            alreadyPlayedCards.subList(0, size).clear();
        }
    }

    /**
     * Moves Mother Nature
     * @param steps the numbers of step you want to move Mother Nature of
     * @throws IllegalNumOfStepsException when trying to move mother Nature counter clock wise or when trying to make it not move at all
     */
    @Override
    public void moveMotherNature(int steps) throws IllegalNumOfStepsException {
        int maxNumOfSteps = currPlayer.getCurrentCard().getMaxNumOfSteps();
        motherNature.move(steps, maxNumOfSteps);
    }

    /**
     * set the color's tower for a player
     * @param towerColor the color chosen by a player
     * @param player the player choosing said color
     */
    @Override
    public void setTowerColors(TowerColor towerColor, Player player) {
        player.setTowerColor(towerColor);
        this.towerColors.remove(towerColor);
    }

    /**
     * set the wizard family for a player
     * @param wizardFamily the wizard chosen by a player
     * @param player the player choosing said wizard
     */
    @Override
    public void setWizardFamily(WizardFamily wizardFamily,Player player){
        player.setWizardFamily(wizardFamily);
        this.wizardFamilies.remove(wizardFamily);
    }

    /**
     * setter for the alreadyPlayedCards attribute
     * @param alreadyPlayedCards a list containing the helper cards played during the current turn
     */
    @Override
    public void setAlreadyPlayedCards(List<HelperCard> alreadyPlayedCards) {
        this.alreadyPlayedCards = alreadyPlayedCards;
    }

    /**
     * getter for number of players
     * @return the number of player currently playing
     */
    @Override
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * getter for planningPhaseOrder
     * @return the players in the order in which they'll play the next planning phase
     */
    @Override
    public List<Player> getPlanningPhaseOrder() {
        return planningPhaseOrder;
    }

    /**
     * getter for actionPhaseOrder
     * @return the players in the order in which they'll play the next action phase
     */
    @Override
    public List<Player> getActionPhaseOrder() {
        return actionPhaseOrder;
    }

    /**
     * getter for gameBoards attribute
     * @return a map that link each player to its game-board
     */
    @Override
    public Map<Player, GameBoard> getGameBoards() {
        return gameBoards;
    }


    /**
     * getter for the clouds attribute
     * @return the list containing all the clouds in the game
     */
    @Override
    public List<Cloud> getClouds() {
        return clouds;
    }

    /**
     * getter for the islandManager attribute
     * @return a reference to the islandManager
     */
    @Override
    public IslandManager getIslandManager() {
        return islandManager;
    }

    /**
     * getter for the bag attribute
     * @return a reference to the bag
     */
    @Override
    public Bag getBag() {
        return bag;
    }

    /**
     * getter for the motherNature attribute
     * @return a reference to the motherNature
     */
    @Override
    public MotherNature getMotherNature() {
        return motherNature;
    }

    /**
     * getter for the professorManager attribute
     * @return a reference to the professorManager
     */
    @Override
    public ProfessorManager getProfessorManager() {
        return professorManager;
    }

    /**
     * getter for the wizardFamilies attribute
     * @return a list containing the remaining wizard families in the game
     */
    @Override
    public List<WizardFamily> getWizardFamilies(){
        return this.wizardFamilies;
    }

    /**
     * getter for the towerColors attribute
     * @return a list containing the remaining tower's colors in the game
     */
    @Override
    public List<TowerColor> getTowerColors() {
        return this.towerColors;
    }

    /**
     * getter for the alreadyPlayedCards attribute
     * @return the list containing the cards played in a given turn
     */
    @Override
    public List<HelperCard> getAlreadyPlayedCards() {
        return this.alreadyPlayedCards;
    }

    /**
     * Return true if final round conditions previously occurred
     * @return true if final round conditions previously occurred
     */
    @Override
    public boolean isFinalRound() {
        return finalRound;
    }

    @Override
    public void setAllObservers(){
        islandManager.registerObserverToIslands();
        islandManager.addObserver(this);

        bag.addObserver(this);



        for (Cloud c : clouds)
            c.addObserver(this);


        for (Player player: getGameBoards().keySet()) {
            player.addObserver(this);
            getGameBoards().get(player).addObserver(this);
        }

        motherNature.addObserver(this);
    }

    /**
     * Reacts to the receiving of a Notification from the observed classes
     * @param notification is the notification type provided by the observed class
     */
    @Override
    public void notify(Notification notification){
        switch (notification){
            case END_MATCH -> {
                notifyObservers(END_MATCH);
                computeWinners();
            }
            case FINAL_ROUND -> finalRound = true;
            case UPDATE_CLOUDS -> notifyObservers(Notification.UPDATE_CLOUDS);
            case UPDATE_ISLANDS -> notifyObservers(Notification.UPDATE_ISLANDS);
            case UPDATE_GAMEBOARDS -> notifyObservers(Notification.UPDATE_GAMEBOARDS);
            case UPDATE_CARDS -> notifyObservers(Notification.UPDATE_CARDS);
        }
    }

    /**
     * Computes the winner, if there was no winner
     * @return a list of winners. The list contains more than one Player, in case the match ended in a draw, otherwise it contains a single winning Player
     */
    @Override
    public List<Player> getWinner(){
        if (winners.isEmpty())
            computeWinners();
        return winners;
    }

    private void computeWinners(){
        //maps a number of towers to a list of players that own that same number of towers
        Map<Integer,List<Player>> playersPerTowerNum = new HashMap<>();

        //maps a number of professors to a list of players that own that same number of professors
        Map<Integer,List<Player>> playersPerProfsNum = new HashMap<>();

        for (Player player : getGameBoards().keySet()){
            int myTowersNum = getGameBoards().get(player).getNumOfTowers();

            //if nobody owns this towers' number, add it to the map
            if (playersPerTowerNum.containsKey(myTowersNum))
                playersPerTowerNum.get(myTowersNum).add(player);
            else { //otherwise add this player to the list of those who share his same number of towers
                List<Player> p = new ArrayList<>();
                p.add(player);
                playersPerTowerNum.put(myTowersNum,p);
            }
        }

        //the minimum number of towers registered among players
        final Integer minTowersNum = playersPerTowerNum.keySet()
                .stream()
                .min(Comparator.naturalOrder())
                .orElse(-1); //can never happen

        //the list of potential winners (with the overall minimum number of towers)
        List<Player> winners = playersPerTowerNum.get(minTowersNum);

        if (winners.size() == 1)
            this.winners = winners;
        else {
            //in case of multiple players sharing the same number of towers
            // find the maximum number of profs among all potential winners
            for (Player winner : winners){
                int myProfsNum = getProfessorManager().getNumProfessorsByPlayer(winner);

                //if nobody owns this profs' number, add it to the map
                if (playersPerProfsNum.containsKey(myProfsNum))
                    playersPerProfsNum.get(myProfsNum).add(winner);
                else {//otherwise add this player to the list of those who share his same number of professors
                    List<Player> p = new ArrayList<>();
                    p.add(winner);
                    playersPerProfsNum.put(myProfsNum,p);
                }
            }

            final Integer maxProfsNum = playersPerProfsNum.keySet()
                    .stream()
                    .max(Comparator.naturalOrder())
                    .orElse(-1); //can never happen

            //the player with the maximum number of profs wins.
            //In case multiple players have the same number of professors, they all win
            this.winners = playersPerProfsNum.get(maxProfsNum);
        }
    }

    /**
     * Returns a reference to a Player given its nickname
     * @param nickname the nickname to turn into a Player's reference
     * @return returns  reference to a Player given its nickname
     */
    @Override
    public Player getPlayerByNickname(String nickname){
        for(Player p : planningPhaseOrder){
            if(p.getNickname().equals(nickname))
                return p;
        }
        return null; //if it happens it's a bug
    }

    /**
     * Returns all non-empty clouds
     * @return all non-empty clouds
     */
    @Override
    public List<Integer> getNonEmptyClouds(){
        List<Integer> cloudIndexes = new ArrayList<>();
        for (Cloud cloud : getClouds()){
            if (!cloud.isEmpty()){
                cloudIndexes.add(getClouds().indexOf(cloud));
            }
        }
        return cloudIndexes;
    }
}
