package it.polimi.ingsw.am19.Model.Utilities;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinManagerTest {

    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing getter for the num of available coins
     */
    @Test
    @DisplayName("Getting available coins")
    void getAvailableCoins() {
        CoinManager coinManager = new CoinManager();
        assertEquals(20,coinManager.getAvailableCoins());
    }

    /**
     * Testing spendCoins
     */
    @Test
    @DisplayName("Testing spendCoins")
    void spendCoins() {
        CoinManager coinManager = new CoinManager();
        assertEquals(20,coinManager.getAvailableCoins());
        coinManager.spendCoins(2);
        assertEquals(20,coinManager.getAvailableCoins());
        coinManager.receiveCoins(1);
        assertEquals(19,coinManager.getAvailableCoins());
        coinManager.spendCoins(1);
        assertEquals(20,coinManager.getAvailableCoins());
    }

    /**
     * Testing receiveCoins
     */
    @Test
    @DisplayName("Testing receiveCoins")
    void receiveCoins() {
        CoinManager coinManager = new CoinManager();
        assertEquals(20,coinManager.getAvailableCoins());
        coinManager.receiveCoins(1);
        assertEquals(19,coinManager.getAvailableCoins());
        coinManager.receiveCoins(19);
        assertEquals(0,coinManager.getAvailableCoins());
        coinManager.receiveCoins(1);
        assertEquals(0,coinManager.getAvailableCoins());
    }
}