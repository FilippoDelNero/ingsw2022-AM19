package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the Player class
 */
class PlayerTest {
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
     * Testing the getter of player's nickname
     */
    @Test
    @DisplayName("Testing getter nickname")
    void getNickname() {
        Player players1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        assertEquals("Dennis", players1.getNickname());
    }

    /**
     * Testing getter HelperDeck after initializing
     */
    @Test
    @DisplayName("Testing getter HelperDeck")
    void getHelperDeck() {
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN);
        ArrayList<HelperCard> initialDeck = new ArrayList<>();
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,1,1));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,2,1));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,3,2));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,4,2));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,5,3));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,6,3));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,7,4));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,8,4));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,9,5));
        initialDeck.add(new HelperCard(WizardFamily.SHAMAN,10,5));
        int i;
        for(i=0;i<10;i++){
            assertEquals(initialDeck.get(i).getMaxNumOfSteps(),p.getHelperDeck().get(i).getMaxNumOfSteps());
            assertEquals(initialDeck.get(i).getNextRoundOrder(), p.getHelperDeck().get(i).getNextRoundOrder());
        }
    }

    /**
     * Testing currentCard and it's Exception
     * @throws UnavailableCardException exception launched if we try to use 2 or more times the same card
     */
    @Test
    @DisplayName("Testing getCurrentCard")
    void getCurrentCard() throws UnavailableCardException {
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN);
        HelperCard cardToUse;
        cardToUse = p.getHelperDeck().get(3);
        assertNull(p.getCurrentCard());
        p.useHelperCard(cardToUse);
        assertFalse(p.getHelperDeck().contains(cardToUse));
        assertEquals(cardToUse,p.getCurrentCard());
    }

    /**
     * Testing getter of WizardFamily
     */
    @Test
    @DisplayName("Testing getWizardFamily")
    void getWizardFamily() {
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN,1);
        assertEquals(WizardFamily.SHAMAN, p.getWizardFamily());
    }

    /**
     * Testing adding coins to the Player balance
     */
    @Test
    @DisplayName("Testing addCoins")
    void testAddCoins(){
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN,1);
        p.addCoins(10);
        assertEquals(11,p.getCoins());
    }

    /**
     * Tests removing coins when the balance has enough coins
     */
    @Test
    @DisplayName("Testing removeCoins")
    public void testRemoveCoins(){
        Player p = new Player("Laura", TowerColor.WHITE, WizardFamily.KING, 4);
        assertDoesNotThrow(() -> p.removeCoins(3));
        assertEquals(1, p.getCoins());
    }

    /**
     * Tests removing coins when the balance has not enough coins
     */
    @Test
    @DisplayName("Testing removeCoins with an exceeding amount of coins")
    public void testRemoveTooManyCoins(){
        Player p = new Player("Laura", TowerColor.WHITE, WizardFamily.KING, 4);
        assertThrows(InsufficientCoinException.class,
                () -> p.removeCoins(5));
        assertEquals(4, p.getCoins());
    }

    /**
     * Testing if useHelperCard set the card as currentCard correctly
     */
    @Test
    @DisplayName("Testing useHelperCard and UnavailableCardException")
    void useHelperCard(){
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN);
        HelperCard cardToUse = p.getHelperDeck().get(3);
        assertNull(p.getCurrentCard());
        assertDoesNotThrow(() -> p.useHelperCard(cardToUse));
        assertFalse(p.getHelperDeck().contains(cardToUse));
        assertEquals(cardToUse,p.getCurrentCard());
        assertThrows(UnavailableCardException.class, () -> p.useHelperCard(cardToUse));
    }

    /**
     * Tests if two players are the same player (have the same nickname)
     */
    @Test
    @DisplayName("Testing equals")
    public void testEquals(){
        Player p = new Player("Phil");
        Player p2 = new Player("Phil");
        assertEquals(p, p2);
    }

    /**
     * Tests if two players are not the same player (have the same nickname)
     */
    @Test
    @DisplayName("Testing equals")
    public void testNotEquals(){
        Player p = new Player("Phil");
        Player p2 = new Player("Dennis");
        assertNotEquals(p, p2);
    }

    /**
     * Tests trying to compare a Player with an instance of another Class
     */
    @Test
    @DisplayName("Testing equals")
    public void testWrongComparison(){
        Player p = new Player("Phil");
        Cloud c = new Cloud(3);
        assertNotEquals(p, c);
    }

    /**
     * Tests getting and setting the WizardFamily
     */
    @Test
    @DisplayName("Testing getter and setter for the WizardFamily")
    public void setWizardFamily(){
        Player p = new Player("Laura");
        assertNull(p.getWizardFamily());

        p.setWizardFamily(WizardFamily.WITCH);
        assertSame(WizardFamily.WITCH,p.getWizardFamily());
    }

    @Test
    public void setTowerColor(){
        Player p = new Player("Dennis");

        assertNull(p.getTowerColor());

        p.setTowerColor(TowerColor.WHITE);
        assertSame(TowerColor.WHITE, p.getTowerColor());
    }

}