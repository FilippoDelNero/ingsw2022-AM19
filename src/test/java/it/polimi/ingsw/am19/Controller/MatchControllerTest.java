package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Server.ClientManager;
import org.junit.jupiter.api.Test;

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
    void testResumingMatch() {
        //TODO TEST PROPERLY ONCE THAT PART IS COMPLETE OR NOT (?)
    }

    @Test
    public void addPlayer(){
        MatchController c = new MatchController();
        c.createNewMatch(2,false);
        assertTrue(c.getModel().getPlanningPhaseOrder().isEmpty());

        c.addPlayer("Laura", TowerColor.WHITE, WizardFamily.WITCH);
        assertEquals(1,c.getModel().getPlanningPhaseOrder().size());
    }

    @Test
    void setClientManagerTest() {
        String player = "Phil";
        ClientManager cm = new ClientManager();
        MatchController c = new MatchController();
        c.setClientManager(player, cm);
        assertEquals(cm, c.getClientManagerMap().get(player));
    }

    @Test
    void changeStateTest() {
        //TODO SENZA MOCK è IMPOSSIBILE SCRIVERE QUALUNQUE TEST RIGUARDI IL CLIENTMANAGER
    }
}


