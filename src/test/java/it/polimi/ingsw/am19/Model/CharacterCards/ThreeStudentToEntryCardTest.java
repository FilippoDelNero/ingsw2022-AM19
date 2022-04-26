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

import static org.junit.jupiter.api.Assertions.*;

class ThreeStudentToEntryCardTest {

    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    @Test
    void addStudent() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        match.setCurrPlayer(player1);

        ThreeStudentToEntryCard card = new ThreeStudentToEntryCard(match);
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->card.addStudent(PieceColor.YELLOW));
        assertDoesNotThrow(()->card.addStudent(PieceColor.RED));
        assertDoesNotThrow(()->card.addStudent(PieceColor.YELLOW));
        assertDoesNotThrow(()->card.addStudent(PieceColor.RED));
        assertThrows(TooManyStudentsException.class,()->card.addStudent(PieceColor.GREEN));
        assertEquals(2,card.getStudents().get(PieceColor.BLUE));
        assertEquals(2,card.getStudents().get(PieceColor.YELLOW));
        assertEquals(2,card.getStudents().get(PieceColor.RED));
    }

    @Test
    void removeStudent() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        match.setCurrPlayer(player1);

        ThreeStudentToEntryCard card = new ThreeStudentToEntryCard(match);
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertEquals(2,card.getStudents().get(PieceColor.BLUE));
        assertDoesNotThrow(()-> card.removeStudent(PieceColor.BLUE));
        assertEquals(1,card.getStudents().get(PieceColor.BLUE));
    }

    @Test
    void initialAction() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        match.setCurrPlayer(player1);

        AbstractCharacterCard card = new ThreeStudentToEntryCard(match);
        card.initialAction();
        int numOfStudent=0;
        for(PieceColor color: PieceColor.values())
            numOfStudent += card.getStudents().get(color);
        assertEquals(6, numOfStudent);
    }

    @Test
    void getStudents() {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        match.setCurrPlayer(player1);

        AbstractCharacterCard card = new ThreeStudentToEntryCard(match);
        card.initialAction();
        int sum = 0;
        assertEquals(0,sum);
        for(PieceColor color: PieceColor.values())
            sum += card.getStudents().get(color);
        assertEquals(6,sum);
    }

    @Test
    void activateEffect() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch match = new TwoPlayersMatch();
        Player player1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING);
        Player player2 = new Player("Laura",TowerColor.WHITE, WizardFamily.SHAMAN);
        match.addPlayer(player1);
        match.addPlayer(player2);
        match.initializeMatch();
        match.setCurrPlayer(player1);

        AbstractCharacterCard card = new ThreeStudentToEntryCard(match);
        card.initialAction();

        //replace entrance of the player with a custom map
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.BLUE,3);
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.GREEN,2);
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.YELLOW,2);
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.RED,0);
        match.getGameBoards().get(player1).getEntrance().replace(PieceColor.PINK,0);

        // replace student on the card
        card.getStudents().replace(PieceColor.BLUE,0);
        card.getStudents().replace(PieceColor.GREEN,0);
        card.getStudents().replace(PieceColor.YELLOW,0);
        card.getStudents().replace(PieceColor.RED,3);
        card.getStudents().replace(PieceColor.PINK,3);

        // list to change
        ArrayList<PieceColor> pieceColors= new ArrayList<>();
        pieceColors.add(PieceColor.PINK);
        pieceColors.add(PieceColor.BLUE);
        pieceColors.add(PieceColor.RED);
        pieceColors.add(PieceColor.GREEN);
        pieceColors.add(PieceColor.RED);
        pieceColors.add(PieceColor.YELLOW);

        card.activateEffect(null,null, pieceColors);

        // check player1 entrance
        assertEquals(2,match.getGameBoards().get(player1).getEntrance().get(PieceColor.BLUE));
        assertEquals(1,match.getGameBoards().get(player1).getEntrance().get(PieceColor.GREEN));
        assertEquals(1,match.getGameBoards().get(player1).getEntrance().get(PieceColor.YELLOW));
        assertEquals(2,match.getGameBoards().get(player1).getEntrance().get(PieceColor.RED));
        assertEquals(1,match.getGameBoards().get(player1).getEntrance().get(PieceColor.PINK));


        //check students on card
        assertEquals(1,card.getStudents().get(PieceColor.BLUE));
        assertEquals(1,card.getStudents().get(PieceColor.GREEN));
        assertEquals(1,card.getStudents().get(PieceColor.YELLOW));
        assertEquals(1,card.getStudents().get(PieceColor.RED));
        assertEquals(2,card.getStudents().get(PieceColor.PINK));
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

        ThreeStudentToEntryCard card = new ThreeStudentToEntryCard(match);
        assertDoesNotThrow(() -> card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(() -> card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(() -> card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(() -> card.addStudent(PieceColor.YELLOW));
        assertDoesNotThrow(() -> card.addStudent(PieceColor.RED));
        assertDoesNotThrow(() -> card.addStudent(PieceColor.PINK));

        // match.getGameBoards().get(player1).getEntrance().replace(PieceColor.BLUE,0);
       // match.getGameBoards().get(player1).getEntrance().replace(PieceColor.GREEN,3);
       // match.getGameBoards().get(player1).getEntrance().replace(PieceColor.RED,1);

        //put all students in the entrance in a list
        GameBoard gameBoard = match.getGameBoards().get(player1);
        ArrayList<PieceColor> pieceColorList = new ArrayList<>();
        for(PieceColor c : gameBoard.getEntrance().keySet()) {
            for(int i = 0; i < gameBoard.getEntrance().get(c); i++) {
                pieceColorList.add(c);
            }
        }

        ArrayList<PieceColor> errorList = new ArrayList<>();
        errorList.add(PieceColor.BLUE);
        errorList.add(pieceColorList.get(0));
        errorList.add(PieceColor.BLUE);
        errorList.add(pieceColorList.get(1));
        errorList.add(PieceColor.GREEN);
        errorList.add(pieceColorList.get(2));
        assertThrows(NoSuchColorException.class,()->card.activateEffect(null,null, errorList));

        //try a correct list
        ArrayList<PieceColor> rightList = new ArrayList<>();
        rightList.add(PieceColor.BLUE);
        rightList.add(pieceColorList.get(0));
        rightList.add(PieceColor.BLUE);
        rightList.add(pieceColorList.get(1));
        rightList.add(PieceColor.BLUE);
        rightList.add(pieceColorList.get(2));
        assertDoesNotThrow(()->card.activateEffect(null,null, rightList));
    }
}