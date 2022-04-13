package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalCardOptionException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

public class MatchDecoratorTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wrappingMatch() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        assertSame(wrappedMatch,decorator.getWrappedMatch());
    }

    @Test
    public void addPlayer(){
        AbstractMatch wrappedMatch = new ThreePlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);
        decorator.addPlayer(p3);

        assertSame(p1,decorator.getPlanningPhaseOrder().get(0));
        assertSame(p2,decorator.getPlanningPhaseOrder().get(1));
        assertSame(p3,decorator.getPlanningPhaseOrder().get(2));
    }

    @Test
    public void initializeTowers(){
        AbstractMatch wrappedMatch = new ThreePlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        decorator.addPlayer(p1);
        decorator.setTowerColors(TowerColor.BLACK, p1);

        decorator.addPlayer(p2);
        decorator.setTowerColors(TowerColor.WHITE, p2);

        decorator.addPlayer(p3);
        decorator.setTowerColors(TowerColor.GREY, p3);

        assertEquals(0, decorator.getTowerColors().size());

        assertFalse(decorator.getTowerColors().contains(TowerColor.GREY));
        assertFalse(decorator.getTowerColors().contains(TowerColor.WHITE));
        assertFalse(decorator.getTowerColors().contains(TowerColor.BLACK));

        assertSame(TowerColor.BLACK,p1.getTowerColor());
        assertSame(TowerColor.WHITE,p2.getTowerColor());
        assertSame(TowerColor.GREY,p3.getTowerColor());
    }

    @Test
    public void initializeWizardFamilies(){
        AbstractMatch wrappedMatch = new ThreePlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        decorator.addPlayer(p1);
        decorator.setWizardFamily(WizardFamily.SHAMAN, p1);

        decorator.addPlayer(p2);
        decorator.setWizardFamily(WizardFamily.KING, p2);

        decorator.addPlayer(p3);
        decorator.setWizardFamily(WizardFamily.WARRIOR,p3);

        assertEquals(1, decorator.getWizardFamilies().size());
        assertFalse(decorator.getWizardFamilies().contains(WizardFamily.SHAMAN));
        assertFalse(decorator.getWizardFamilies().contains(WizardFamily.KING));
        assertFalse(decorator.getWizardFamilies().contains(WizardFamily.WARRIOR));
        assertTrue(decorator.getWizardFamilies().contains(WizardFamily.WITCH));
    }

    @Test
    public void getNumOfPlayers() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        assertEquals(2, decorator.getNumOfPlayers());
    }

    @Test
    public void testGetAndSetForAlreadyPlayedHC() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        List<HelperCard> list = new ArrayList<>();

        list.add(p1.getHelperDeck().get(0));
        list.add(p2.getHelperDeck().get(1));

        decorator.setAlreadyPlayedCards(list);

        assertArrayEquals(list.toArray(), decorator.getAlreadyPlayedCards().toArray());

        decorator.resetAlreadyPlayedCards();
        assertEquals(0, decorator.getAlreadyPlayedCards().size());
    }

    @Test
    public void testTooManyPlayer() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player pExtra = new Player("Phil");
        Player p1 = new Player("Laura");
        Player p2 = new Player ("Dennis");

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);
        assertEquals(2, decorator.getPlanningPhaseOrder().size());

        decorator.addPlayer(pExtra);
        assertEquals(2, decorator.getPlanningPhaseOrder().size());
        assertEquals("Laura", decorator.getPlanningPhaseOrder().get(0).getNickname());
        assertEquals("Dennis", decorator.getPlanningPhaseOrder().get(1).getNickname());
    }

    @Test
    public void testInitializeGameBoards(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        decorator.addPlayer(p1);
        decorator.setTowerColors(TowerColor.BLACK, p1);
        decorator.setWizardFamily(WizardFamily.SHAMAN, p1);

        decorator.addPlayer(p2);
        decorator.setTowerColors(TowerColor.WHITE, p2);
        decorator.setWizardFamily(WizardFamily.KING, p2);

        decorator.initializeMatch();
        assertEquals(106,decorator.getBag().getTotNumOfStudents());
        assertEquals(2,decorator.getClouds().size());
        assertEquals(2, decorator.getGameBoards().size());
        for (Player player: decorator.getPlanningPhaseOrder()){
            GameBoard gameBoard = decorator.getGameBoards().get(player);
            assertEquals(7,gameBoard.getEntranceNumOfStud());
            assertEquals(8,gameBoard.getNumOfTowers());
            assertEquals(0,gameBoard.getDiningRoomNumOfStud());
        }
    }

    @Test
    public void testInitializeClouds(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        decorator.addPlayer(p1);
        decorator.setTowerColors(TowerColor.BLACK, p1);
        decorator.setWizardFamily(WizardFamily.SHAMAN, p1);

        decorator.addPlayer(p2);
        decorator.setTowerColors(TowerColor.WHITE, p2);
        decorator.setWizardFamily(WizardFamily.KING, p2);

        decorator.initializeMatch();

        assertEquals(2,decorator.getClouds().size());
        for (Cloud cloud:decorator.getClouds()){
            assertEquals(3,cloud.getNumOfHostableStudents());
            assertEquals(0,cloud.getCurrNumOfStudents());
        }
    }

    @Test
    public void testInitializeIslands(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");

        wrappedMatch.addPlayer(p1);
        wrappedMatch.setTowerColors(TowerColor.BLACK, p1);
        wrappedMatch.setWizardFamily(WizardFamily.SHAMAN, p1);

        wrappedMatch.addPlayer(p2);
        wrappedMatch.setTowerColors(TowerColor.WHITE, p2);
        wrappedMatch.setWizardFamily(WizardFamily.KING, p2);

        wrappedMatch.initializeMatch();
        IslandManager islandManager = wrappedMatch.getIslandManager();
        MotherNature motherNature = wrappedMatch.getMotherNature();

        Island MNposition = motherNature.getCurrPosition();
        assertNotNull(MNposition);

        assertTrue(islandManager.getIslands().contains(MNposition));
        assertTrue(MNposition.isMotherNaturePresent());

        ListIterator<Island> iterator = islandManager.getIterator();
        Island islandOppositeMN = iterator.next();
        while (islandOppositeMN != MNposition){
            islandOppositeMN = iterator.next();
        }
        for (int i = 0; i < 6; i++) {
            islandOppositeMN = iterator.next();
        }
        assertEquals(0, islandOppositeMN.getTotStudents());
        assertEquals(0,MNposition.getTotStudents());

        Island island = iterator.next();
        for (int i = 0; i < 12; i++) {
            if (island != MNposition)
                assertFalse(island.isMotherNaturePresent());
            if(island != MNposition || island != islandOppositeMN)
                assertEquals(1, island.getTotStudents());
        }
    }

    @Test
    public void testActionPhaseOrder(){
        Player p1 = new Player("Phil",TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        p1.getHelperDeck().removeAll(new ArrayList<>(p1.getHelperDeck()));
        p2.getHelperDeck().removeAll(new ArrayList<>(p2.getHelperDeck()));

        HelperCard card1 = new HelperCard(WizardFamily.SHAMAN,1,1 );
        HelperCard card2 = new HelperCard(WizardFamily.KING,1,1 );
        p1.getHelperDeck().add(card1);
        p2.getHelperDeck().add(card2);

        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        decorator.setCurrPlayer(p2);

        assertDoesNotThrow(() -> decorator.useHelperCard(card2));

        decorator.setCurrPlayer(p1);

        assertDoesNotThrow(() -> decorator.useHelperCard(card1));

        System.out.println("Order of players in the next round:");
        for (Player p : decorator.getActionPhaseOrder()){
            System.out.println(p.getNickname());
        }
    }

    @Test
    public void testSortingOutPlanningPhase() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil", TowerColor.WHITE, WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.BLACK, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);

        assertDoesNotThrow(() -> decorator.useHelperCard(p1.getHelperDeck().get(6)));
        decorator.setCurrPlayer(p2);
        assertDoesNotThrow(() -> decorator.useHelperCard(p2.getHelperDeck().get(0)));

        //check that the action phase order is as expected
        assertEquals(p1, decorator.getActionPhaseOrder().get(1));
        assertEquals(p2, decorator.getActionPhaseOrder().get(0));
        //sort out the next planning phase order
        decorator.updatePlanningPhaseOrder();
        //check that the next planning phase order is as expected
        assertEquals(p1, decorator.getPlanningPhaseOrder().get(1));
        assertEquals(p2, decorator.getPlanningPhaseOrder().get(0));
    }

    @Test
    public void testRefillClouds() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.BLACK, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        assertDoesNotThrow(()->decorator.refillClouds());

        for(Cloud c: decorator.getClouds()) {
            assertEquals(3, c.getCurrNumOfStudents());
        }
    }

    @Test
    public void testMoveStudentInsideGameBoard() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.BLACK, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        GameBoard gameBoard = decorator.getGameBoards().get(p1);
        PieceColor student;

        if(gameBoard.getEntrance().get(PieceColor.RED) > 0)
            student = PieceColor.RED;
        else if(gameBoard.getEntrance().get(PieceColor.BLUE) > 0)
            student = PieceColor.BLUE;
        else if(gameBoard.getEntrance().get(PieceColor.GREEN) > 0)
            student = PieceColor.GREEN;
        else if(gameBoard.getEntrance().get(PieceColor.YELLOW) > 0)
            student = PieceColor.YELLOW;
        else
            student = PieceColor.PINK;

        decorator.setCurrPlayer(p1);

        assertDoesNotThrow(() -> decorator.moveStudentToDiningRoom(student));
        assertEquals(1, gameBoard.getDiningRoomNumOfStud());
        assertEquals(1, gameBoard.getDiningRoom().get(student));
    }

    @Test
    public void testMotherNatureMovement() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        HelperCard helperCard = p1.getHelperDeck().get(9);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        decorator.setCurrPlayer(p1);

        assertDoesNotThrow(() ->  decorator.useHelperCard(helperCard));

        MotherNature motherNature = decorator.getMotherNature();
        Island currPos = motherNature.getCurrPosition();
        assertTrue(currPos.isMotherNaturePresent());

        assertDoesNotThrow(() -> decorator.moveMotherNature(3));

        Island nextPos = motherNature.getCurrPosition();
        assertFalse(currPos.isMotherNaturePresent());
        assertTrue(nextPos.isMotherNaturePresent());
    }

    @Test
    public void testMoveStudent() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        Island island = decorator.getIslandManager().getIslands().get(0);
        GameBoard gameBoard = decorator.getGameBoards().get(p1);

        gameBoard.getEntrance().replace(PieceColor.PINK,1);
        int oldEntrance = gameBoard.getEntranceNumOfStud();
        int oldIsland = island.getTotStudents();

        assertDoesNotThrow(() -> decorator.moveStudent(PieceColor.PINK, gameBoard, island));
        assertEquals(oldEntrance - 1 ,gameBoard.getEntranceNumOfStud());
        assertEquals(oldIsland + 1 ,island.getTotStudents());
    }

    @Test
    public void testGetterAndSetterCurrPlayer() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil",TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        decorator.setCurrPlayer(p1);
        assertEquals(p1, decorator.getCurrPlayer());
    }

    @Test
    public void testGetProfessor(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);

        decorator.initializeMatch();
        assertNotNull(decorator.getProfessorManager());
    }

}