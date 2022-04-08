package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for managing Player
 */
public class Player {
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
    private int coins = 0;

    /**
     * The Wizard Family of HelperCard associated to the player
     */
    private WizardFamily wizardFamily;

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
        this.coins=coins;
        this.currentCard = null;

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
     * Constructor for a player of a normal Match
     * @param nickname Name of the player
     */
    public Player(String nickname) {
        this.nickname = nickname;
        this.towerColor = null;
        this.wizardFamily = null;
        this.currentCard = null;
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
    public int getCoins() {
        return coins;
    }

    /**
     * Setter for the Helper Card just used
     * @param currentCard the HelperCard used
     */
    private void setCurrentCard(HelperCard currentCard) {
        this.currentCard = currentCard;
    }

    /**
     * Method to add(or subtract) coins
     * @param coins How many coins add (if positive) or subtract (if negative)
     * @throws InsufficientCoinException Exception if we could go in a negative balance
     */
    public void addCoins(int coins) throws InsufficientCoinException {
        int newValue;
        if(coins<0){
            newValue= this.coins + coins;
            if(newValue<0)
                throw new InsufficientCoinException("You haven't enough coins");
        }
        this.coins += coins;
    }

    /**
     * Method for use an HelperCard. Checks if it's in the HelperDeck, then remove it and calls setCurrentCard
     * @param helperCard the Card we want to use
     */
    public void useHelperCard(HelperCard helperCard) throws UnavailableCardException {
        if(!this.helperDeck.contains(helperCard))
            throw new UnavailableCardException("HelperCard not available on your Helper Deck");
        setCurrentCard(helperCard);
        this.helperDeck.remove(helperCard);
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Player))
            return false;
        Player p = (Player) o;
        return (Objects.equals(nickname, p.nickname));
    }
}
