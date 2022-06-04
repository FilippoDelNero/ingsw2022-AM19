package it.polimi.ingsw.am19.Controller;

import eu.hansolo.tilesfx.tools.DoubleExponentialSmoothingForLinearSeries;
import it.polimi.ingsw.am19.Model.BoardManagement.*;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Persistence.SavedData;
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
     * Tests MatchController correctly retrieving old matches from storage data
     * NOOOOO
     */
    @Test
    public void savedMatch() {
        MatchController controller = new MatchController();
        MatchDecorator matchDecorator = new MatchDecorator(new TwoPlayersMatch());

        assertFalse(controller.checkOldMatches());
        SavedData savedData = new SavedData(matchDecorator,5);
        controller.saveMatch();
        assertTrue(controller.checkOldMatches());
    }

    /**
     * NOOOO
     */
    @Test
    public void resumeMatch(){
        MatchController controller = new MatchController();
        assertNull(controller.getModel());

        controller.createNewMatch(2,false);
        MatchDecorator model = new MatchDecorator(new TwoPlayersMatch());
        model.addPlayer(new Player("Phil", TowerColor.BLACK,WizardFamily.KING));
        model.addPlayer(new Player("Laura", TowerColor.WHITE,WizardFamily.SHAMAN));
        model.initializeMatch();

        SavedData savedData = new SavedData(model,2);

        controller.saveMatch();

        controller.resumeMatch();
        assertEquals(model,controller.getModel());
        assertEquals(2,controller.getRoundsManager().getRoundNum());
    }
}


