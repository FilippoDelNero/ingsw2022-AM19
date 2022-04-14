package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertMatchDecoratorTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createExpertMatch(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        assertEquals(20,decorator.getCoinManager().getAvailableCoins());
        assertEquals(2,decorator.getWrappedMatch().numOfPlayers);
        assertEquals(12,decorator.getCharacterCards().size());
    }

    @Test
    public void addExpertPlayer(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);
        assertEquals(p1,decorator.getPlanningPhaseOrder().get(0));

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);
        assertEquals(p2,decorator.getPlanningPhaseOrder().get(1));
    }

    @Test
    public void testInitializeMatch(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        assertEquals(20,decorator.getCoinManager().getAvailableCoins());
        decorator.initializeMatch();
        assertEquals(1,p1.getCoins());
        assertEquals(1,p2.getCoins());
        assertEquals(18,decorator.getCoinManager().getAvailableCoins());
        assertEquals(3,decorator.getCharacterCards().size());

        AbstractCharacterCard c1 = decorator.getCharacterCards().get(0);
        AbstractCharacterCard c2 = decorator.getCharacterCards().get(1);
        AbstractCharacterCard c3 = decorator.getCharacterCards().get(2);
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c2.equals(c3));
    }

    @Test
    public void testPlayAffordableCard(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);

        AbstractCharacterCard card = decorator.getCharacterCards().get(0);
        int cardPrice = card.getPrice();
        p1.addCoins(cardPrice);

        assertDoesNotThrow(() -> decorator.playCard(card,null,null));

        assertEquals(1,p1.getCoins());
    }

    @Test
    public void testPlayNotAffordableCard(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);

        AbstractCharacterCard card = decorator.getCharacterCards().get(0);

        assertDoesNotThrow(() -> p1.removeCoins(1));
        assertEquals(0,p1.getCoins());

        assertThrows(InsufficientCoinException.class,
                () -> decorator.playCard(card,null,null));

        assertEquals(0,p1.getCoins());
    }
}