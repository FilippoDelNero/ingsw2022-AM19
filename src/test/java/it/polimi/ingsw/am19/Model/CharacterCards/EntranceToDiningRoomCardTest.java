package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testing class for the EntranceToDiningRoomCard
 */
public class EntranceToDiningRoomCardTest {
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
    void testActivateEffect() {
        AbstractMatch m = new TwoPlayersMatch();
        AbstractCharacterCard card = new EntranceToDiningRoomCard(m);

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
        //again find a color
        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        //again move a student of that color
        PieceColor finalColor2 = color;
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor2));

        //find two more colors from the player's entrance
        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        PieceColor finalColor3 = color;

        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        PieceColor finalColor4 = color;

        List<PieceColor> list = new ArrayList<>();
        list.add(finalColor3);
        list.add(finalColor);
        list.add(finalColor4);
        list.add(finalColor2);

        assertEquals(2, gameBoard.getDiningRoomNumOfStud());
        assertTrue(gameBoard.getDiningRoom().get(finalColor) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor2) > 0);

        card.activateEffect(null, null, list);

        assertEquals(2, gameBoard.getDiningRoomNumOfStud());
        assertTrue(gameBoard.getDiningRoom().get(finalColor3) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor4) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor2) > 0);
    }

    /**
     *
     */
    @Test
    void initialActionTooManyTimes() {
        AbstractMatch m = new TwoPlayersMatch();
        AbstractCharacterCard card = new EntranceToDiningRoomCard(m);

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

        //again find a color
        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        //again move a student of that color
        PieceColor finalColor2 = color;
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor2));

        //again find a color
        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        //again move a student of that color
        PieceColor finalColor3 = color;
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor3));

        //find three more colors from the player's entrance
        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        PieceColor finalColor4 = color;

        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        PieceColor finalColor5 = color;

        color = null;
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            if(gameBoard.getEntrance().get(c) != 0)
                color = c;
        }
        PieceColor finalColor6 = color;

        List<PieceColor> list = new ArrayList<>();
        list.add(finalColor4);
        list.add(finalColor);
        list.add(finalColor5);
        list.add(finalColor2);
        list.add(finalColor6);
        list.add(finalColor3);


        assertEquals(3, gameBoard.getDiningRoomNumOfStud());
        assertTrue(gameBoard.getDiningRoom().get(finalColor) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor2) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor3) > 0);

        card.activateEffect(null, null, list);

        assertEquals(3, gameBoard.getDiningRoomNumOfStud());
        assertTrue(gameBoard.getDiningRoom().get(finalColor4) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor5) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor6) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor2) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor3) > 0);
    }
}
