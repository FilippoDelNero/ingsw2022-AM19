package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoColorInfluence;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoColorInfluenceCardTest {

    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * Testing NoColorInfluenceCard effect
     */
    @Test
    @DisplayName("Testing NoColorInfluenceCard")
    void activateEffect() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();

        assertTrue(match.getIslandManager().getInfluenceStrategy() instanceof StandardInfluence);
        NoColorInfluenceCard card = new NoColorInfluenceCard(match);
        card.activateEffect(null, PieceColor.BLUE,null);
        assertTrue(match.getIslandManager().getInfluenceStrategy() instanceof NoColorInfluence);
    }

    @Test
    void equals() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();

        AbstractCharacterCard card = new NoColorInfluenceCard(match);
        AbstractCharacterCard card1 = new MotherNaturePlusTwoCard(match);
        assertNotEquals(card, card1);
        AbstractCharacterCard card2 = new NoColorInfluenceCard(match);
        assertEquals(card2, card);
    }
}