package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
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
        assertDoesNotThrow(() -> m.useHelperCard(p1.getHelperDeck().get(3)));
        m.setCurrPlayer(p2);
        assertDoesNotThrow(() -> m.useHelperCard(p2.getHelperDeck().get(0)));
        m.setCurrPlayer(p3);
        assertDoesNotThrow(() -> m.useHelperCard(p3.getHelperDeck().get(6)));
        //check that the action phase order is as expected
        assertEquals(p1, m.getActionPhaseOrder().get(1));
        assertEquals(p2, m.getActionPhaseOrder().get(0));
        assertEquals(p3, m.getActionPhaseOrder().get(2));
        //sort out the next planning phase order
        m.updatePlanningPhaseOrder();
        //check that the next planning phase order is as expected
        assertEquals(p1, m.getPlanningPhaseOrder().get(2));
        assertEquals(p2, m.getPlanningPhaseOrder().get(0));
        assertEquals(p3, m.getPlanningPhaseOrder().get(1));
    }
}
