package it.polimi.ingsw.am19.Model.Utilities;

/**
 * Class to manage the max amount of coins for a single match
 */
public class CoinManager {
    /**
     * Num of coins available
     */
    private int availableCoins;

    /**
     * Max amount of coins in o single match
     */
    private final int maxCoins = 20;

    /**
     * Constructor: create a CoinManager with 20 coins available
     */
    public CoinManager() {
        this.availableCoins = maxCoins;
    }

    /**
     * Getter for the num of coins available
     * @return the coins available to distribute
     */
    public int getAvailableCoins() {
        return availableCoins;
    }

    /**
     * Method invoked by player when he wants to spend coin
     * @param amount amount of coins to be made available
     * @return if it was successful (new available coins doesn't exceed the max available coins)
     */
    public boolean spendCoins(int amount) {
        int newValue = this.availableCoins + amount;
        if(newValue<=maxCoins){
            this.availableCoins = newValue;
            return true;
        }
        return false;
    }

    /**
     * Method invoked by player when he has to receive some coins
     * @param amount num of coins to no longer be made available
     * @return if it was successful (new available coins doesn't go into negative)
     */
    public boolean receiveCoins(int amount) {
        int newValue = this.availableCoins - amount;
        if(newValue>=0){
            this.availableCoins = newValue;
            return true;
        }
        return false;
    }
}
