package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the Player class
 */
class PlayerTest {
    /**
     * Testing the getter of player's nickname
     */
    @Test
    @DisplayName("Testing getter nickname")
    void getNickname() {
        Player players1 = new Player("Dennis",TowerColor.BLACK,WizardFamily.KING);
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
     * Testing addCoins, with positive and negative argument
     * @throws InsufficientCoinException exception launched if we try to subtract more coins than the balance
     */
    @Test
    @DisplayName("Testing addCoins")
    void addCoins() throws InsufficientCoinException {
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN,1);
        p.addCoins(10);
        assertEquals(11,p.getCoins());
        p.addCoins(-11);
        assertEquals(0,p.getCoins());
        assertThrows(InsufficientCoinException.class,()-> p.addCoins(-1));
    }

    /**
     * Testing if useHelperCard set the card as currentCard and launch the exception if needed
     * @throws UnavailableCardException exception if we try to use 2 or more times the same HelperCard
     */
    @Test
    @DisplayName("Testing useHelperCard and UnavailableCardException")
    void useHelperCard() throws UnavailableCardException {
        Player p = new Player("Dennis",TowerColor.WHITE,WizardFamily.SHAMAN);
        HelperCard cardToUse = p.getHelperDeck().get(3);
        assertNull(p.getCurrentCard());
        p.useHelperCard(cardToUse);
        assertFalse(p.getHelperDeck().contains(cardToUse));
        assertEquals(cardToUse,p.getCurrentCard());
        assertThrows(UnavailableCardException.class, () -> p.useHelperCard(cardToUse));
    }
}