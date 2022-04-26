package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing MatchDecorator
 */
public class MatchDecoratorTest {
    @BeforeEach
    void removeAllFromBag() {
        Bag.getBagInstance().removeAll();
    }

    /**
     * Tests the process of wrapping an AbstractMatch with a generic MatchDecorator
     */
    @Test
    public void wrappingMatch() {
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        assertSame(wrappedMatch,decorator.getWrappedMatch());
    }

    /**
     * Tests making a MatchDecorator add a new player
     */
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

    /**
     * Tests making a MatchDecorator set each player's TowerColor
     */
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

    /**
     * Tests making a MatchDecorator set each player's WizardFamily
     */
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

    /**
     * Tests making a MatchDecorator get the number of Players chosen when creating a new match
     */
    @Test
    public void getNumOfPlayers() {

        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        assertEquals(2, decorator.getNumOfPlayers());
    }

    /**
     * Tests making a MatchDecorator set already played HelperCards
     */
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

    /**
     * Tests trying the MatchDecorator add more students than expected
     */
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

    /**
     * Tests making a MatchDecorator initialize each player's GameBoard
     */
    @Test
    public void testInitializeGameBoards(){

        Bag bag = Bag.getBagInstance();
        bag.removeAll();

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

    /**
     * ìTests making a MatchDecorator set up all the Clouds
     */
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

    /**
     * Tests making a MatchDecorator set up the archipelago of Islands
     */
    @Test
    public void testInitializeIslands(){

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
        IslandManager islandManager = decorator.getIslandManager();
        MotherNature motherNature = decorator.getMotherNature();

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

    /**
     * Tests making a MatchDecorator set the action phase order
     */
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

    /**
     * Tests making a MatchDecorator set up the planning phase order
     */
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

    /**
     * Tests making a MatchDecorator refill each Cloud
     */
    @Test
    public void testRefillClouds() {

        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        Player p1 = new Player("Phil", TowerColor.WHITE,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.BLACK, WizardFamily.KING);

        decorator.addPlayer(p1);
        decorator.addPlayer(p2);

        decorator.initializeMatch();

        assertDoesNotThrow(decorator::refillClouds);

        for(Cloud c: decorator.getClouds()) {
            assertEquals(3, c.getCurrNumOfStudents());
        }
    }

    /**
     * Tests making a MatchDecorator move students from the inside of a GameBoard
     */
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

    /**
     * Tests making a MatchDecorator move MotherNature
     */
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

    /**
     * Tests making a MatchDecorator move students
     */
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

    /**
     * Tests making a MatchDecorator set and get the current Player
     */
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

    /**
     * Tests making a MatchDecorator get ProfessorManager
     */
    @Test
    public void testGetProfessor(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(wrappedMatch);

        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(professorManager);

        decorator.initializeMatch();
        assertNotNull(decorator.getProfessorManager());
    }

    /**
     * Tests if the finalRound flag is set true in the wrapped match after removing all students from the Bag
     * It simulates the occurring of a final round condition
     */
    @Test
    public void testIsFinalRound1(){
        AbstractMatch m = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(m);
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        decorator.addPlayer(p1);
        decorator.addPlayer(p2);
        decorator.initializeMatch();

        assertFalse(decorator.isFinalRound());
        Bag bag = decorator.getBag();
        bag.removeAll();

        assertTrue(decorator.isFinalRound());
    }

    /**
     * Tests if the finalRound flag is set true in the wrappedMatch after removing all HelperCards from a player helper deck
     * It simulates the occurring of a final round condition
     */
    @Test
    public void testIsFinalRound2(){
        AbstractMatch m = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(m);
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        decorator.addPlayer(p1);
        decorator.addPlayer(p2);
        decorator.initializeMatch();
        decorator.setCurrPlayer(p1);
        assertFalse(decorator.isFinalRound());

        List<HelperCard> helperDeck = List.copyOf(p1.getHelperDeck());
        for(HelperCard card: helperDeck)
            assertDoesNotThrow(() -> decorator.useHelperCard(card));

        assertTrue(decorator.isFinalRound());
    }

    /**
     * Tests the winning of the player with the lower number of towers in his game board
     */
    @Test
    public void testGetWinner(){
        AbstractMatch m = new TwoPlayersMatch();
        MatchDecorator decorator = new MatchDecorator(m);

        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        decorator.addPlayer(p1);
        decorator.addPlayer(p2);
        decorator.initializeMatch();

        decorator.getGameBoards().get(p1).removeTower();
        assertEquals(1,decorator.getWinner().size());
        assertEquals(p1,decorator.getWinner().get(0));
    }
}