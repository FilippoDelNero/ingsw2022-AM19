package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Class for testing MatchController
 */
public class MatchControllerTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * Tests model not being an instance of an expert match, when an easy match of two players was requested
     */
    @Test
    public void twoPlayersNoExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(2,false);
        assertEquals(2,c.getModel().getNumOfPlayers());
        assertFalse(c.getModel() instanceof ExpertMatchDecorator);
    }

    /**
     * Tests model not being an instance of an expert match, when an easy match of three players was requested
     */
    @Test
    public void threePlayersNoExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(3,false);
        assertEquals(3,c.getModel().getNumOfPlayers());
        assertFalse(c.getModel() instanceof ExpertMatchDecorator);
    }

    /**
     * Tests model being an instance of an expert match, when an expert match of two players was requested
     */
    @Test
    public void twoPlayersExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(2,true);
        assertEquals(2,c.getModel().getNumOfPlayers());
        assertTrue(c.getModel() instanceof ExpertMatchDecorator);
    }

    /**
     * Tests model being an instance of an expert match, when an expert match of three players was requested
     */
    @Test
    public void threePlayersExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(3,true);
        assertEquals(3,c.getModel().getNumOfPlayers());
        assertTrue(c.getModel() instanceof ExpertMatchDecorator);
    }

    /**
     * Tests adding a player from the MatchController perspective. The addition made should also be visible in model state
     */
    @Test
    public void addPlayer(){
        MatchController c = new MatchController();
        c.createNewMatch(2,false);
        assertTrue(c.getModel().getPlanningPhaseOrder().isEmpty());

        c.addPlayer("Laura", TowerColor.WHITE, WizardFamily.WITCH);
        assertEquals(1,c.getModel().getPlanningPhaseOrder().size());
    }

    /**
     * Tests if MatchController class retrieves the right nicknames list from the model
     */
    @Test
    public void getNicknamesFromSavedMatch(){
        MatchController controller = new MatchController();
        controller.createNewMatch(2,false);

        controller.addPlayer("Phil", TowerColor.BLACK,WizardFamily.KING);
        controller.addPlayer("Laura", TowerColor.WHITE,WizardFamily.SHAMAN);
        List<String> oldNicknames = new ArrayList<>();
        oldNicknames.add("Phil");
        oldNicknames.add("Laura");

        assertEquals(oldNicknames,controller.getNicknamesFromResumedMatch());
    }

    /**
     * Test controller's initial state being the login one
     */
    @Test
    public void initialState(){
        MatchController controller = new MatchController();
        assertEquals(StateType.LOGIN, controller.getCurrState());
    }

    /**
     * Tests if the current player is the same between model and controller
     */
    @Test
    public void getCurrPlayer(){
        MatchController controller = new MatchController();
        controller.createNewMatch(2,false);

        controller.addPlayer("Phil", TowerColor.BLACK,WizardFamily.KING);
        controller.addPlayer("Laura", TowerColor.WHITE,WizardFamily.SHAMAN);

        controller.setCurrPlayer("Phil");

        assertEquals(controller.getModel().getCurrPlayer().getNickname(),
                controller.getCurrPlayer());
    }

    /**
     * Tests if RoundManager is already set after building MatchController
     */
    @Test
    public void getRoundManager(){
        MatchController controller = new MatchController();

        assertNotNull(controller.getRoundsManager());
    }

    //TODO test match save
}


