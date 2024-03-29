package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
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

import static org.junit.jupiter.api.Assertions.*;

class StudentToHallCardTest {

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

        StudentToHallCard card = new StudentToHallCard(match);
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(()->card.addStudent(PieceColor.YELLOW));
        assertDoesNotThrow(()->card.addStudent(PieceColor.RED));
        assertThrows(TooManyStudentsException.class,()->card.addStudent(PieceColor.GREEN));
        assertEquals(2,card.getStudents().get(PieceColor.BLUE));
        assertEquals(1,card.getStudents().get(PieceColor.YELLOW));
        assertEquals(1,card.getStudents().get(PieceColor.RED));
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

        StudentToHallCard card = new StudentToHallCard(match);
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

        AbstractCharacterCard card = new StudentToHallCard(match);
        card.initialAction();
        int numOfStudent=0;
        for(PieceColor color: PieceColor.values())
            numOfStudent += card.getStudents().get(color);
        assertEquals(4, numOfStudent);
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

        StudentToHallCard card = new StudentToHallCard(match);
        assertDoesNotThrow(()->card.addStudent(PieceColor.BLUE));
        assertEquals(0,match.getGameBoards().get(player1).getDiningRoom().get(PieceColor.BLUE));
        card.activateEffect(null,PieceColor.BLUE,null);
        assertEquals(1,match.getGameBoards().get(player1).getDiningRoom().get(PieceColor.BLUE));
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

        StudentToHallCard card = new StudentToHallCard(match);

        //add a BLUE student
        try {
            card.addStudent(PieceColor.BLUE);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }

        //check student on the card
        assertEquals(1,card.getStudents().get(PieceColor.BLUE));
        assertEquals(0,card.getStudents().get(PieceColor.RED));

        int oldValue = match.getGameBoards().get(match.getCurrPlayer()).getDiningRoom().get(PieceColor.BLUE);
        assertThrows(NoSuchColorException.class,()->card.activateEffect(null,PieceColor.RED,null));
        assertDoesNotThrow(()->card.activateEffect(null,PieceColor.BLUE,null));

        // check TooManyStudent exception
        match.getGameBoards().get(match.getCurrPlayer()).getDiningRoom().replace(PieceColor.BLUE,10);
        try {
            card.addStudent(PieceColor.BLUE);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        assertThrows(TooManyStudentsException.class,()->card.activateEffect(null,PieceColor.BLUE,null));
    }
}