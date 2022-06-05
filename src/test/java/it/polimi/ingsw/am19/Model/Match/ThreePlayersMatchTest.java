package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalCardOptionException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the ThreePlayerMatch Class
 */
public class ThreePlayersMatchTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * Tests assigning and removing from the list of available TowerColors the ones chosen for each Player
     */
    @Test
    void testInitializeTowers() {
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        AbstractMatch m = new ThreePlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.addPlayer(p3);
        m.setTowerColors(TowerColor.GREY, p3);
        m.setWizardFamily(WizardFamily.WARRIOR, p3);

        assertEquals(0, m.getTowerColors().size());
        assertFalse(m.getTowerColors().contains(TowerColor.GREY));
        assertFalse(m.getTowerColors().contains(TowerColor.WHITE));
        assertFalse(m.getTowerColors().contains(TowerColor.BLACK));
    }

    /**
     * Tests assigning and removing from the list of available WizardFamilies the ones chosen for each Player
     */
    @Test
    void testInitializeWizards() {
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        AbstractMatch m = new ThreePlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.addPlayer(p3);
        m.setTowerColors(TowerColor.GREY, p3);
        m.setWizardFamily(WizardFamily.WARRIOR, p3);

        assertEquals(1, m.getWizardFamilies().size());
        assertTrue(m.getWizardFamilies().contains(WizardFamily.WITCH));
        assertFalse(m.getWizardFamilies().contains(WizardFamily.WARRIOR));
        assertFalse(m.getWizardFamilies().contains(WizardFamily.SHAMAN));
        assertFalse(m.getWizardFamilies().contains(WizardFamily.KING));
    }

    /**
     * Tests the state of the Bag and GameBoards after the Match initialization
     */
    @Test
    void testInitializeGameBoards(){
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        AbstractMatch m = new ThreePlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.addPlayer(p3);
        m.setTowerColors(TowerColor.GREY, p3);
        m.setWizardFamily(WizardFamily.WARRIOR, p3);

        m.initializeMatch();

        assertEquals(93,m.getBag().getTotNumOfStudents());
        assertEquals(3,m.getClouds().size());
        assertEquals(3, m.getGameBoards().size());
        for (Player player: m.getPlanningPhaseOrder()){
            GameBoard gameBoard = m.getGameBoards().get(player);
            assertEquals(9,gameBoard.getEntranceNumOfStud());
            assertEquals(6,gameBoard.getNumOfTowers());
            assertEquals(0,gameBoard.getDiningRoomNumOfStud());
        }
    }

    /**
     * Tests Clouds state after Match initialization and if they have a correct capacity
     */
    @Test
    void testInitializeClouds(){
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        AbstractMatch m = new ThreePlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.addPlayer(p3);
        m.setTowerColors(TowerColor.GREY, p3);
        m.setWizardFamily(WizardFamily.WARRIOR, p3);

        m.initializeMatch();
        assertEquals(3,m.getClouds().size());
        for (Cloud cloud:m.getClouds()){
            assertEquals(4,cloud.getNumOfHostableStudents());
            assertEquals(0,cloud.getCurrNumOfStudents());
        }
    }

    /**
     * Tests the state of the archipelago of Islands after Match initialization
     */
    @Test
    void testInitializeIslands(){
        Player p1 = new Player("Phil");
        Player p2 = new Player("Laura");
        Player p3 = new Player("Dennis");

        AbstractMatch m = new ThreePlayersMatch();

        m.addPlayer(p1);
        m.setTowerColors(TowerColor.BLACK, p1);
        m.setWizardFamily(WizardFamily.SHAMAN, p1);

        m.addPlayer(p2);
        m.setTowerColors(TowerColor.WHITE, p2);
        m.setWizardFamily(WizardFamily.KING, p2);

        m.addPlayer(p3);
        m.setTowerColors(TowerColor.GREY, p3);
        m.setWizardFamily(WizardFamily.WARRIOR, p3);

        m.initializeMatch();
        IslandManager islandManager = m.getIslandManager();
        MotherNature motherNature = m.getMotherNature();

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
     * Testing the sorting of the players for next turn's planning phase
     */
    @Test
    void testSortingOutPlanningPhase() {
        //create three players
        Player p1 = new Player("Phil", TowerColor.WHITE, WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.BLACK, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        //create a match for three players
        AbstractMatch m = new ThreePlayersMatch();
        //initialize the match
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();
        //the three players play a card each
        m.setCurrPlayer(p1);
        assertDoesNotThrow(() -> m.useHelperCard(p1.getHelperDeck().get(1)));
        m.setCurrPlayer(p2);
        assertDoesNotThrow(() -> m.useHelperCard(p2.getHelperDeck().get(2)));
        m.setCurrPlayer(p3);
        assertDoesNotThrow(() -> m.useHelperCard(p3.getHelperDeck().get(3)));
        //check that the action phase order is as expected
        assertEquals(p1, m.getActionPhaseOrder().get(0));
        assertEquals(p2, m.getActionPhaseOrder().get(1));
        assertEquals(p3, m.getActionPhaseOrder().get(2));
        //sort out the next planning phase order
        m.updatePlanningPhaseOrder();
        //check that the next planning phase order is as expected
        assertEquals(p1, m.getPlanningPhaseOrder().get(0));
        assertEquals(p2, m.getPlanningPhaseOrder().get(1));
        assertEquals(p3, m.getPlanningPhaseOrder().get(2));
    }

    /**
     * Tests the winning of the player with the lower number of towers in his game board
     * Game does not end in a draw
     */
    @Test
    public void testGetWinner(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        m.getGameBoards().get(p3).removeTower();
        assertEquals(5, m.getGameBoards().get(p3).getNumOfTowers());

        m.getGameBoards().get(p2).removeTower();
        assertEquals(5, m.getGameBoards().get(p2).getNumOfTowers());

        m.getGameBoards().get(p2).removeTower();
        assertEquals(4, m.getGameBoards().get(p2).getNumOfTowers());

        assertEquals(1,m.getWinner().size());
        assertEquals(p2,m.getWinner().get(0));
    }

    /**
     * Tests the winning of two players in a draw. The winners share the same number of towers and the same number of professors
     */
    @Test
    public void testEndMatchInADraw(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        m.getGameBoards().get(p3).removeTower();
        assertEquals(5, m.getGameBoards().get(p3).getNumOfTowers());

        m.getGameBoards().get(p2).removeTower();
        assertEquals(5, m.getGameBoards().get(p2).getNumOfTowers());

        m.getProfessorManager().getProfessors().put(PieceColor.BLUE,p2);
        m.getProfessorManager().getProfessors().put(PieceColor.RED,p3);

        assertEquals(2,m.getWinner().size());
        assertTrue(m.getWinner().contains(p2));
        assertTrue(m.getWinner().contains(p3));
    }

    /**
     * Tests the winning of a Player. The winner share the same number of towers with another Player, but has a greater number of professors
     */
    @Test
    public void testEndMatchWithProfNumber(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        m.getGameBoards().get(p3).removeTower();
        assertEquals(5, m.getGameBoards().get(p3).getNumOfTowers());

        m.getGameBoards().get(p2).removeTower();
        assertEquals(5, m.getGameBoards().get(p2).getNumOfTowers());

        m.getProfessorManager().getProfessors().put(PieceColor.RED,p3);

        assertEquals(1,m.getWinner().size());
        assertEquals(p3,m.getWinner().get(0));
    }

    /**
     * Tests the winning of a Player. The winner share the same number of towers with another Player, but has a greater number of professors
     */
    @Test
    public void testEndMatchWithProfNum(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        m.getGameBoards().get(p1).removeTower();
        assertEquals(5, m.getGameBoards().get(p1).getNumOfTowers());

        m.getGameBoards().get(p2).removeTower();
        assertEquals(5, m.getGameBoards().get(p2).getNumOfTowers());

        m.getProfessorManager().getProfessors().put(PieceColor.RED,p2);

        assertEquals(1,m.getWinner().size());
        assertEquals(p2,m.getWinner().get(0));
    }

    /**
     * Tests the end match when all players have the same number of towers and professors
     */
    @Test
    public void testAllWin(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        List<Player> winners = m.getWinner();
        assertEquals(3, winners.size());
        assertTrue(winners.containsAll(m.getPlanningPhaseOrder()));
    }

    /**
     * Tests the case in which:
     * - player 1 has 6 towers and 2 professors
     * - player 2 has 5 towers and 1 professor
     * - player 3 has 5 towers and no professors
     *
     * According to game rules, player 2 should win because he has less towers than player 1
     * and more professors than player 3
     */
    @Test
    public void testWinner(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        m.getProfessorManager().getProfessors().put(PieceColor.RED,p1);
        m.getProfessorManager().getProfessors().put(PieceColor.BLUE,p1);

        m.getProfessorManager().getProfessors().put(PieceColor.YELLOW,p2);
        m.getGameBoards().get(p2).removeTower();

        m.getGameBoards().get(p3).removeTower();

        List<Player> winners = m.getWinner();
        assertEquals(1, winners.size());
        assertEquals(p2,winners.get(0));
    }

    /**
     * Tests each player using the same helper card, when having no other option in their deck
     */
    @Test
    public void testChoosingSameHelperCard(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        HelperCard helperCard1 = new HelperCard(WizardFamily.SHAMAN,1,1);
        HelperCard helperCard2 = new HelperCard(WizardFamily.KING,1,1);
        HelperCard helperCard3 = new HelperCard(WizardFamily.WARRIOR,1,1);

        for (Player player : m.getPlanningPhaseOrder()){
            player.setHelperDeck(new ArrayList<>());
        }

        p1.getHelperDeck().add(helperCard1);
        p2.getHelperDeck().add(helperCard2);
        p3.getHelperDeck().add(helperCard3);

        m.setCurrPlayer(p1);
        assertDoesNotThrow(() -> m.useHelperCard(helperCard1));

        m.setCurrPlayer(p2);
        assertDoesNotThrow(() -> m.useHelperCard(helperCard2));

        m.setCurrPlayer(p3);
        assertDoesNotThrow(() -> m.useHelperCard(helperCard3));
    }

    /**
     * Tests throwing IllegalCardOptionException when a player wants to play a card that other players
     * already played, but he had other options in his deck and could have chosen between them
     */
    @Test
    public void testChoosingHelperCard(){
        AbstractMatch m = new ThreePlayersMatch();
        Player p1 = new Player("Phil", TowerColor.BLACK,WizardFamily.SHAMAN);
        Player p2 = new Player("Laura", TowerColor.WHITE, WizardFamily.KING);
        Player p3 = new Player("Dennis", TowerColor.GREY, WizardFamily.WARRIOR);
        m.addPlayer(p1);
        m.addPlayer(p2);
        m.addPlayer(p3);
        m.initializeMatch();

        HelperCard helperCard1 = new HelperCard(WizardFamily.SHAMAN,1,1);
        HelperCard helperCard2 = new HelperCard(WizardFamily.KING,1,1);
        HelperCard helperCard3 = new HelperCard(WizardFamily.WARRIOR,1,1);
        HelperCard helperCard3Bis = new HelperCard(WizardFamily.WARRIOR,2,1);

        for (Player player : m.getPlanningPhaseOrder()){
            player.setHelperDeck(new ArrayList<>());
        }

        p1.getHelperDeck().add(helperCard1);
        p2.getHelperDeck().add(helperCard2);
        p3.getHelperDeck().add(helperCard3);
        p3.getHelperDeck().add(helperCard3Bis);

        m.setCurrPlayer(p1);
        assertDoesNotThrow(() -> m.useHelperCard(helperCard1));

        m.setCurrPlayer(p2);
        assertDoesNotThrow(() -> m.useHelperCard(helperCard2));

        m.setCurrPlayer(p3);
        assertThrows(IllegalCardOptionException.class,
                () -> m.useHelperCard(helperCard3));
    }
}
