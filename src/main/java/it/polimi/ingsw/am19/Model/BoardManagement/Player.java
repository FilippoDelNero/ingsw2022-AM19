package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Utilities.Notification;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Model.Utilities.CoinManager;
import it.polimi.ingsw.am19.Utilities.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for managing Player
 */
public class Player extends Observable implements Serializable {
    /**
     * String for the nickname of a Player
     */
    private final String nickname;

    /**
     * List of HelperCard still available to use
     */
    private List<HelperCard> helperDeck;

    /**
     * Last HelperCard used
     */
    private HelperCard currentCard;

    /**
     * The TowerColor associated to the player
     */
    private TowerColor towerColor;

    /**
     * Balance coins for the Expert Matches
     */
    private Integer coins;

    /**
     * The Wizard Family of HelperCard associated to the player
     */
    private WizardFamily wizardFamily;

    /**
     * CoinManager for an expert Match (it's null in the standard Game)
     */
    private CoinManager coinManager;

    /**
     * Constructor for a player of a normal Match
     * @param nickname name of the player
     * @param towerColor color of the tower of this player
     * @param wizardFamily family of HelperCard of this player
     */
    public Player(String nickname, TowerColor towerColor, WizardFamily wizardFamily) {
        this.nickname = nickname;
        this.towerColor = towerColor;
        this.wizardFamily = wizardFamily;
        this.currentCard = null;
        this.coins = null;
        this.coinManager = null;

        this.helperDeck = new ArrayList<>();
        int numOfStep;
        int nextRoundOrder=0;
        for(numOfStep=1;numOfStep<6;numOfStep++){
            nextRoundOrder+=1;
            helperDeck.add(new HelperCard(wizardFamily,nextRoundOrder,numOfStep));
            nextRoundOrder+=1;
            helperDeck.add(new HelperCard(wizardFamily,nextRoundOrder,numOfStep));
        }
    }

    /**
     * Constructor for a player of an expert match
     * @param nickname Name of the player
     * @param towerColor color of the tower of this player
     * @param wizardFamily family of HelperCard of this player
     * @param coins setting the initial balance of coins
     */
    public Player(String nickname, TowerColor towerColor, WizardFamily wizardFamily, int coins) {
        this.nickname = nickname;
        this.towerColor = towerColor;
        this.wizardFamily = wizardFamily;
        this.coins = coins;
        this.currentCard = null;
        this.coinManager = null;

        ArrayList<HelperCard> helperDeck = new ArrayList<>();
        int numOfStep;
        int nextRoundOrder=0;
        for(numOfStep=1;numOfStep<6;numOfStep++){
            nextRoundOrder+=1;
            helperDeck.add(new HelperCard(wizardFamily,nextRoundOrder,numOfStep));
            nextRoundOrder+=1;
            helperDeck.add(new HelperCard(wizardFamily,nextRoundOrder,numOfStep));
        }

        this.helperDeck = helperDeck;
    }

    /**
     * Constructor for a player of a normal Match
     * @param nickname Name of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter of the nickname of the players
     * @return a string with the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter of the towerColor of the player
     * @return a TowerColor
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * Setter of the towerColor of the player
     * @param color the tower's color
     */
    public void setTowerColor(TowerColor color) {
        this.towerColor = color;
    }


    /**
     * Getter the list of the HelperCard not used yet
     * @return the ArrayList of the HelperCard not used yet
     */
    public List<HelperCard> getHelperDeck() {
        return helperDeck;
    }

    /**
     * Getter for the last HelperCard used
     * @return the last HelperCard used
     */
    public HelperCard getCurrentCard() {
        return currentCard;
    }

    /**
     * Getter for the WizardFamily of this player
     * @return the WizardFamily associated to him
     */
    public WizardFamily getWizardFamily() {
        return wizardFamily;
    }

    /**
     * set the wizard family and create a deck
     * @param family the wizardFamily chosen by the player
     */
    public void setWizardFamily(WizardFamily family) {
        this.wizardFamily = family;
        ArrayList<HelperCard> helperDeck = new ArrayList<>();
        int numOfStep;
        int nextRoundOrder=0;
        for(numOfStep=1;numOfStep<6;numOfStep++){
            nextRoundOrder+=1;
            helperDeck.add(new HelperCard(wizardFamily,nextRoundOrder,numOfStep));
            nextRoundOrder+=1;
            helperDeck.add(new HelperCard(wizardFamily,nextRoundOrder,numOfStep));
        }

        this.helperDeck = helperDeck;
    }

    /**
     * Getter for the balance of coins
     * @return the coins available
     */
    public Integer getCoins() {
        return coins;
    }

    /**
     * Setter for the Helper Card just used
     * @param currentCard the HelperCard used
     */
    private void setCurrentCard(HelperCard currentCard) {
        this.currentCard = currentCard;
        notifyObservers(Notification.UPDATE_CARDS);
    }

    /**
     * Setter for the expert match CoinManager
     * @param coinManager the coin manager of the match
     */
    public void setCoinManager(CoinManager coinManager) {
        this.coinManager = coinManager;
    }

    /**
     * Initializes Player's balance in case of an expert match
     */
    public void initializeCoins(){
        this.coins = 0;
    }

    /**
     * Adds the specified amount of coins to the Player
     * @param amount is the amount of coins to add
     */
    public void addCoins(int amount){ //TODO OBSERVER -> NOTIFY -> UH NEW COIN, DRAW IT
        if(coinManager.receiveCoins(amount))
            this.coins += amount;
    }

    /**
     * Removes the specified amount of coins from the Player's balance
     * @param amount the amount of coins to subtract
     * @throws InsufficientCoinException when trying to remove more coins than the available ones
     */
    public void removeCoins(int amount) throws InsufficientCoinException { //TODO OBSERVER -> NOTIFY -> UH LESS COIN, DELETE THEM
        int newValue = this.coins - amount;
        if(newValue < 0)
            throw new InsufficientCoinException("You haven't enough coins");
        if(coinManager.spendCoins(amount))
            this.coins = newValue;
    }

    /**
     * Method for use an HelperCard. Checks if it's in the HelperDeck, then remove it and calls setCurrentCard
     * After playing the card, a check is made to the number of HelperCards left in the deck
     * If the deck size is 0, a notification is sent to the observers to say that the final round started
     * @param helperCard the Card we want to use
     */
    public void useHelperCard(HelperCard helperCard) throws UnavailableCardException {
        if(!this.helperDeck.contains(helperCard))
            throw new UnavailableCardException("HelperCard not available on your Helper Deck");
        setCurrentCard(helperCard);
        this.helperDeck.remove(helperCard);
        if (helperDeck.size() == 0)
            notifyObservers(Notification.FINAL_ROUND);
    }

    /**
     * Decides if two Players have the same nickname
     * @param o the Player to compare with this
     * @return true if the nickname is the same, false when it is not
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Player p))
            return false;
        return (Objects.equals(nickname, p.nickname));
    }

    /**
     * Replaces the current helperDeck with the one passed as argument
     * @param helperDeck is an helper deck
     */
    public void setHelperDeck(List<HelperCard> helperDeck) {
        this.helperDeck = helperDeck;
    }
}
