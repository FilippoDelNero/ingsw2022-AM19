package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.MotherNature;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
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
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
        MotherNature motherNature = MotherNature.getInstance();
        motherNature.setCurrMovementStrategy(motherNature.getDefaultMovement());
    }

    /**
     * Testing MotherNaturePlusTwo effect
     */
    @Test
    @DisplayName("Testing MotherNaturePlusTwo effect")
    void activateEffect() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        //match.getMotherNature().setCurrMovementStrategy(new StandardMovement());

        assertTrue(match.getMotherNature().getCurrMovementStrategy() instanceof StandardMovement);
        MotherNaturePlusTwoCard card = new MotherNaturePlusTwoCard(match);
        card.activateEffect(null,null, null);
        assertTrue(match.getMotherNature().getCurrMovementStrategy() instanceof PlusTwoMovement);
        assertDoesNotThrow(()->match.getMotherNature().move(1));
        assertTrue(match.getMotherNature().getCurrMovementStrategy() instanceof StandardMovement);
    }
}