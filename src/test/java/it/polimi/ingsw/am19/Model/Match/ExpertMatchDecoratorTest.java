package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertMatchDecoratorTest {
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

    @Test
    public void createExpertMatch(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        assertEquals(20,decorator.getCoinManager().getAvailableCoins());
        assertEquals(2,decorator.getWrappedMatch().numOfPlayers);
        assertEquals(12,decorator.getCharacterCards().size());
    }

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

    @Test
    public void testPlayAffordableCard(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);

        AbstractCharacterCard card = decorator.getCharacterCards().get(0);
        int cardPrice = card.getPrice();
        Island island = decorator.getIslandManager().getIslands().get(0);

        p1.addCoins(cardPrice);
        switch(card.getId()){
            case LADRO ->
                    //up to 3 students of the chosen color need to be removed from everybody's dining room
                    assertDoesNotThrow(() -> decorator.playCard(card, PieceColor.YELLOW,null, null));

            case ARALDO ->
                    //calculates influence on the island chosen
                    assertDoesNotThrow(() ->decorator.playCard(card,null,island, null));

            case MONACO -> {
                    //is one of the colors fof the students one the card
                    PieceColor color = (new ArrayList<>(card.getStudents().keySet())).get(0);
                    assertDoesNotThrow(() -> decorator.playCard(card,PieceColor.BLUE,null, null));
            }
            case FUNGARO ->
                    //the color chosen will not be taken into account while computing influence
                    assertDoesNotThrow(() -> decorator.playCard(card,PieceColor.GREEN,null, null));

            case CENTAURO ->
                    //towers will not be taken into account while computing influence on the chosen island
                    assertDoesNotThrow(() -> decorator.playCard(card,null,island, null));

            case GIULLARE ->{
                    //replace the entrance of the currentPlayer with a custom map
                    decorator.getGameBoards().get(p1).getEntrance().replace(PieceColor.BLUE,3);
                    decorator.getGameBoards().get(p1).getEntrance().replace(PieceColor.GREEN,2);
                    decorator.getGameBoards().get(p1).getEntrance().replace(PieceColor.YELLOW,2);
                    decorator.getGameBoards().get(p1).getEntrance().replace(PieceColor.RED,0);
                    decorator.getGameBoards().get(p1).getEntrance().replace(PieceColor.PINK,0);

                    //replace students on the card
                    card.getStudents().replace(PieceColor.BLUE,0);
                    card.getStudents().replace(PieceColor.GREEN,0);
                    card.getStudents().replace(PieceColor.YELLOW,0);
                    card.getStudents().replace(PieceColor.RED,3);
                    card.getStudents().replace(PieceColor.PINK,3);

                    //swapping list:
                    ArrayList<PieceColor> swapStud = new ArrayList<>();
                    swapStud.add(PieceColor.PINK);
                    swapStud.add(PieceColor.BLUE);
                    swapStud.add(PieceColor.RED);
                    swapStud.add(PieceColor.GREEN);
                    swapStud.add(PieceColor.RED);
                    swapStud.add(PieceColor.YELLOW);

                    assertDoesNotThrow(() -> decorator.playCard(card,null,null, swapStud));
            }
            case CAVALIERE ->
                    //additional 2 points while calculating influence
                    assertDoesNotThrow(() -> decorator.playCard(card, null,null, null));

            case CONTADINO ->
                    //takes the control over a professor under new conditions
                    assertDoesNotThrow(() -> decorator.playCard(card, null,null, null));

            case NONNA_ERBE ->
                    //needs an island to put a no entry tile on
                    assertDoesNotThrow(() -> decorator.playCard(card, null,island, null));

            case MENESTRELLO -> {
                    //put all students in the entrance in a list
                    GameBoard gameBoard = decorator.getGameBoards().get(p1);
                    List<PieceColor> studentToSwap = new ArrayList<>();
                    for(PieceColor c : gameBoard.getEntrance().keySet()) {
                        for(int i = 0; i < gameBoard.getEntrance().get(c); i++) {
                            studentToSwap.add(c);
                        }
                    }

                    //find a color
                    PieceColor finalColor = studentToSwap.get(0);
                    //move a student of that color
                    assertDoesNotThrow(() -> decorator.moveStudentToDiningRoom(finalColor));
                    //again find a color
                    PieceColor finalColor2 = studentToSwap.get(1);
                    //again move a student of that color
                    assertDoesNotThrow(() -> decorator.moveStudentToDiningRoom(finalColor2));

                    //find two more colors from the player's entrance
                    PieceColor finalColor3 = studentToSwap.get(2);

                    PieceColor finalColor4 = studentToSwap.get(3);

                    List<PieceColor> list = new ArrayList<>();
                    list.add(finalColor3);
                    list.add(finalColor);
                    list.add(finalColor4);
                    list.add(finalColor2);

                    assertDoesNotThrow(() -> decorator.playCard(card, null,null, list));
            }

            case POSTINO_MAGICO ->
                    //makes mother nature able to move an additional number of steps
                    assertDoesNotThrow(() -> decorator.playCard(card, null,null, null));

            case PRINCIPESSA_VIZIATA ->{
                    //puts a student of the chosen color in the dining room
                    PieceColor color = (new ArrayList<>(card.getStudents().keySet())).get(0);
                    assertDoesNotThrow(() -> decorator.playCard(card, color,null, null));
            }
        }

        assertEquals(1,p1.getCoins());
    }

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