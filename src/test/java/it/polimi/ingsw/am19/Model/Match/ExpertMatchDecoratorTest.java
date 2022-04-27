package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.MotherNaturePlusTwoCard;
import it.polimi.ingsw.am19.Model.CharacterCards.StudentToHallCard;
import it.polimi.ingsw.am19.Model.CharacterCards.StudentToIslandCard;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExpertMatchDecorator
 */
public class ExpertMatchDecoratorTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();

        MotherNature motherNature = MotherNature.getInstance();
        motherNature.setCurrMovementStrategy(motherNature.getDefaultMovement());
    }

    /**
     * Tests the process of wrapping an AbstractMatch, adding some expert features to it
     */
    @Test
    public void createExpertMatch(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        assertEquals(20,decorator.getCoinManager().getAvailableCoins());
        assertEquals(2,decorator.getWrappedMatch().numOfPlayers);
        assertEquals(12,decorator.getCharacterCards().size());
    }

    /**
     * Tests adding a student to an expert match
     */
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

    /**
     * Tests the additional initializations required by an expert match: adding coins to players and drawing some CharacterCards
     */
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
        assertEquals(1,p1.getCoins(), "p1 coins failure");
        assertEquals(1,p2.getCoins(), "p2 coins failure");
        assertEquals(18,decorator.getCoinManager().getAvailableCoins(), "coin manager failure");
        assertEquals(3,decorator.getCharacterCards().size(),"draw cards failure");

        AbstractCharacterCard c1 = decorator.getCharacterCards().get(0);
        AbstractCharacterCard c2 = decorator.getCharacterCards().get(1);
        AbstractCharacterCard c3 = decorator.getCharacterCards().get(2);
        assertNotEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertNotEquals(c2, c3);
    }

    /**
     * Tests playing successfully a character card during an expert game
     */
    @Test
    public void testPlayAffordableCard(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);
        AbstractCharacterCard card = new MotherNaturePlusTwoCard(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);
        int cardPrice = card.getPrice();
        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);
        p1.addCoins(cardPrice);

        assertDoesNotThrow(() -> decorator.playCard(card, null, null, null));
        assertEquals(1,p1.getCoins());
    }

    /**
     * Tests making the current player play a character card he can afford in case tha playCard() method throws an exception
     */
    @Test
    public void testPlayCardWithException(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);
        AbstractCharacterCard card = new StudentToHallCard(wrappedMatch);
        card.getStudents().replace(PieceColor.RED,1);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);

        int cardPrice = card.getPrice();
        p1.addCoins(cardPrice);
        int playerWallet = p1.getCoins();

        decorator.getGameBoards().get(p1).getDiningRoom().replace(PieceColor.RED,10);
        assertThrows(TooManyStudentsException.class,
                () -> decorator.playCard(card, PieceColor.RED, null, null));
        assertEquals(playerWallet,p1.getCoins());
    }

    /**
     * Tests making the current player play a character card he can afford in case tha playCard() method throws another type of exception
     */
    @Test
    public void testPlayCardWithAnotherException(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);
        AbstractCharacterCard card = new StudentToIslandCard(wrappedMatch);
        card.getStudents().replace(PieceColor.RED,0);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);

        int cardPrice = card.getPrice();
        p1.addCoins(cardPrice);
        int playerWallet = p1.getCoins();

        assertThrows(NoSuchColorException.class,
                () -> decorator.playCard(card, PieceColor.RED, null, null));
        assertEquals(playerWallet,p1.getCoins());
    }

    /**
     * Tests trying to make the current player play a card he cannot afford
     */
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
                () -> decorator.playCard(card,null,null, null));

        assertEquals(0,p1.getCoins());
    }
}