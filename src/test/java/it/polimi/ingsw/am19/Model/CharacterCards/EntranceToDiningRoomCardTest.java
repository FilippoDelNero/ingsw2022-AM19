package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
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
        bag.removeAll();
    }

    /**
     * testing the activateEffect method
     */
    @Test
    void testActivateEffect() throws NoSuchColorException, TooManyStudentsException {
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

        //put all students in the entrance in a list
        GameBoard gameBoard = m.getGameBoards().get(player1);
        ArrayList<PieceColor> pieceColorList = new ArrayList<>();
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            for(int i = 0; i < gameBoard.getEntrance().get(c); i++) {
                pieceColorList.add(c);
            }
        }

        //find a color
        PieceColor finalColor = pieceColorList.get(0);
        //move a student of that color
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor));
        //again find a color
        PieceColor finalColor2 = pieceColorList.get(1);
        //again move a student of that color
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor2));

        //find two more colors from the player's entrance
        PieceColor finalColor3 = pieceColorList.get(2);

        PieceColor finalColor4 = pieceColorList.get(3);

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
     * testing the activateEffect method, trying to swap three student instead of the permitted two
     */
    @Test
    void initialActionTooManyTimes() throws NoSuchColorException, TooManyStudentsException {
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

        //put all students in the entrance in a list
        GameBoard gameBoard = m.getGameBoards().get(player1);
        ArrayList<PieceColor> pieceColorList = new ArrayList<>();
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            for(int i = 0; i < gameBoard.getEntrance().get(c); i++) {
                pieceColorList.add(c);
            }
        }

        //find a color
        PieceColor finalColor = pieceColorList.get(0);
        //move a student of that color
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor));
        //again find a color
        PieceColor finalColor2 = pieceColorList.get(1);
        //again move a student of that color
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor2));
        //again again find a color
        PieceColor finalColor3 = pieceColorList.get(2);
        //again again move a student of that color
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor3));

        //find three more colors from the player's entrance
        PieceColor finalColor4 = pieceColorList.get(3);

        PieceColor finalColor5 = pieceColorList.get(4);

        PieceColor finalColor6 = pieceColorList.get(5);

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

        //TODO controllare che l'ultimo scambio non sia avvenuto (assert equal prima e dopo effetto)
        assertEquals(3, gameBoard.getDiningRoomNumOfStud());
        assertTrue(gameBoard.getDiningRoom().get(finalColor4) > 0);
        assertTrue(gameBoard.getDiningRoom().get(finalColor5) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor) > 0);
        assertTrue(gameBoard.getEntrance().get(finalColor2) > 0);
    }

    @Test
    void checkParameter(){
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        match.setCurrPlayer(player1);

        AbstractCharacterCard card = new EntranceToDiningRoomCard(match);
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.BLUE,0);
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.RED,1);
        match.getGameBoards().get(player1).getDiningRoom().replace(PieceColor.BLUE,1);

        //check an error pieceColorList
        ArrayList<PieceColor> errorList = new ArrayList<>();
        errorList.add(PieceColor.BLUE);
        errorList.add(PieceColor.YELLOW);
        assertThrows(NoSuchColorException.class,()->card.activateEffect(null,null,errorList));

        // check a right PieceColorList
        ArrayList<PieceColor> rightList = new ArrayList<>();
        rightList.add(PieceColor.RED);
        rightList.add(PieceColor.BLUE);
        assertDoesNotThrow(()->card.activateEffect(null,null,rightList));

        //check the TooMany student exception
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.BLUE,1);
        match.getGameBoards().get(player1).getDiningRoom().replace(PieceColor.BLUE,10);
        match.getGameBoards().get(player1).getDiningRoom().replace(PieceColor.YELLOW,1);
        ArrayList<PieceColor> errorList2 = new ArrayList<>();
        errorList2.add(PieceColor.BLUE);
        errorList2.add(PieceColor.YELLOW);
        assertThrows(TooManyStudentsException.class,()->card.activateEffect(null,null,errorList2));
    }
}
