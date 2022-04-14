package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the ThreeToBagCard
 */
class ThreeToBagCardTest {

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
     * Check the correct functioning of the card's effect
     */
    @Test
    @DisplayName("Testing ThreeToBagCard")
    void activateEffect() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();

        match.getGameBoards().get(player1).getDiningRoom().replace(PieceColor.BLUE,4);
        match.getGameBoards().get(player2).getDiningRoom().replace(PieceColor.BLUE,2);

        AbstractCharacterCard card = new ThreeToBagCard(match);
        card.activateEffect(null,PieceColor.BLUE,null);
        assertEquals(1,match.getGameBoards().get(player1).getDiningRoom().get(PieceColor.BLUE));
        assertEquals(1,match.getGameBoards().get(player1).getDiningRoom().get(PieceColor.BLUE));
    }
}