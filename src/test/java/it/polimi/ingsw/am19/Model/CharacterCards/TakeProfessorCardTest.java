package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.ChangeIfEqualCheckProfessor;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.StandardCheckProfessor;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * testing class for the TakeProfessorCard
 */
public class TakeProfessorCardTest {
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
     * testing the activateEffect method
     */
    @Test
    void testActivateEffect() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch m = new TwoPlayersMatch();
        AbstractCharacterCard card = new TakeProfessorCard(m);

        Player player1 = new Player("Laura", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);

        m.addPlayer(player1);
        m.addPlayer(player2);

        m.initializeMatch();

        //--PLANNING PHASE--
        assertDoesNotThrow(m::refillClouds);

        m.setCurrPlayer(player1);
        assertDoesNotThrow(() -> m.useHelperCard(player1.getHelperDeck().get(0)));
        m.setCurrPlayer(player2);
        assertDoesNotThrow(() -> m.useHelperCard(player2.getHelperDeck().get(3)));

        //--ACTION PHASE--
        m.setCurrPlayer(player1);
        card.activateEffect(null, null, null);
        assertTrue(m.getProfessorManager().getCurrentStrategy() instanceof ChangeIfEqualCheckProfessor);
        //find a color
        PieceColor color = null;
        GameBoard gameBoard = m.getGameBoards().get(player1);
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        //move a student of that color
        PieceColor finalColor = color;
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor));
        assertTrue(m.getProfessorManager().getCurrentStrategy() instanceof ChangeIfEqualCheckProfessor);

        m.setCurrPlayer(player2);
        gameBoard = m.getGameBoards().get(player2);
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        //move a student of that color
        PieceColor finalColor2 = color;
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor2));
        assertTrue(m.getProfessorManager().getCurrentStrategy() instanceof StandardCheckProfessor);

        m.resetAlreadyPlayedCards();

    }
}
