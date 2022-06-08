package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchControllerTest {
    @Test
    public void twoPlayersNoExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(2,false);
        assertEquals(2,c.getModel().getNumOfPlayers());
        assertFalse(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void threePlayersNoExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(3,false);
        assertEquals(3,c.getModel().getNumOfPlayers());
        assertFalse(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void twoPlayersExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(2,true);
        assertEquals(2,c.getModel().getNumOfPlayers());
        assertTrue(c.getModel() instanceof ExpertMatchDecorator);
    }

    @Test
    public void threePlayersExpert(){
        MatchController c = new MatchController();
        c.createNewMatch(3,true);
        assertEquals(3,c.getModel().getNumOfPlayers());
        assertTrue(c.getModel() instanceof ExpertMatchDecorator);
    }

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

    //TODO test match save
}


