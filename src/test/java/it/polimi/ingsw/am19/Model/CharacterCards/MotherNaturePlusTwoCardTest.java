package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.MotherNature;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.MovementStrategies.PlusTwoMovement;
import it.polimi.ingsw.am19.Model.MovementStrategies.StandardMovement;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotherNaturePlusTwoCardTest {

    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();

        MotherNature motherNature = MotherNature.getInstance();
        motherNature.setCurrMovementStrategy(motherNature.getDefaultMovement());
    }

    /**
     * Testing MotherNaturePlusTwo effect
     */
    @Test
    @DisplayName("Testing MotherNaturePlusTwo effect")
    void activateEffect() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        //match.getMotherNature().setCurrMovementStrategy(new StandardMovement());

        match.setCurrPlayer(player1);
        assertDoesNotThrow(() -> player1.useHelperCard(player1.getHelperDeck().get(5)));

        assertTrue(match.getMotherNature().getCurrMovementStrategy() instanceof StandardMovement);
        MotherNaturePlusTwoCard card = new MotherNaturePlusTwoCard(match);
        card.activateEffect(null,null, null);
        assertTrue(match.getMotherNature().getCurrMovementStrategy() instanceof PlusTwoMovement);
        assertDoesNotThrow(()->match.moveMotherNature(1));
        assertTrue(match.getMotherNature().getCurrMovementStrategy() instanceof StandardMovement);
    }
}