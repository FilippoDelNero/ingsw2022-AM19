package it.polimi.ingsw.am19.Model.Utilities;

public class CoinManager {
    private int availableCoins;
    private final int maxCoins = 20;

    public CoinManager() {
        this.availableCoins = maxCoins;
    }

    public int getAvailableCoins() {
        return availableCoins;
    }

    public boolean spendCoins(int amount) {
        int newValue = this.availableCoins + amount;
        if(newValue<=maxCoins){
            this.availableCoins = newValue;
            return true;
        }
        return false;
    }

    public boolean receiveCoins(int amount) {
        int newValue = this.availableCoins - amount;
        if(newValue>=0){
            this.availableCoins = newValue;
            return true;
        }
        return false;
    }
}
