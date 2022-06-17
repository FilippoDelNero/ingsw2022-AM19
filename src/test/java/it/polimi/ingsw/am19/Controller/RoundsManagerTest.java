package it.polimi.ingsw.am19.Controller;

import static org.junit.jupiter.api.Assertions.*;
import it.polimi.ingsw.am19.Controller.PhaseManagement.ActionPhase;
import it.polimi.ingsw.am19.Controller.PhaseManagement.PlanningPhase;
import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A Class for testing rounds management
 */
public class RoundsManagerTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * Tests always starting having 0 as round number after RoundsManager initialization
     */
    @Test
    public void roundZero(){
        RoundsManager roundsManager = new RoundsManager();
        int roundZero = roundsManager.getRoundNum();

        assertEquals(0,roundZero);
    }

    /**
     * Tests incrementing round numbers
     */
    @Test
    public void roundNumPlusOne(){
        RoundsManager roundsManager = new RoundsManager();

        roundsManager.incrementRoundsNum();
        assertEquals(1,roundsManager.getRoundNum());

        roundsManager.incrementRoundsNum();
        assertEquals(2,roundsManager.getRoundNum());
    }

    /**
     * Tests switching from planning phase to action phase
     */
    @Test
    public void switchToPlanningPhase(){
        RoundsManager roundsManager = new RoundsManager();
        MatchController matchController = new MatchController();
        List<String> playerOrder = new ArrayList<>();
        playerOrder.add("Lau");
        playerOrder.add("Phil");
        PlanningPhase planningPhase = new PlanningPhase(playerOrder,matchController);

        roundsManager.changePhase(planningPhase);
        assertEquals(planningPhase,roundsManager.getCurrPhase());
    }

    /**
     * Tests keeping info about which was the previous round
     */
    @Test
    public void savePrevPhase(){
        RoundsManager roundsManager = new RoundsManager();
        MatchController matchController = new MatchController();
        matchController.setModel(new MatchDecorator(new TwoPlayersMatch()));
        matchController.getModel().initializeMatch();

        List<String> playerOrder = new ArrayList<>();
        playerOrder.add("Lau");
        playerOrder.add("Phil");
        PlanningPhase planningPhase = new PlanningPhase(playerOrder,matchController);
        ActionPhase actionPhase = new ActionPhase(playerOrder,matchController);

        roundsManager.changePhase(planningPhase);
        roundsManager.changePhase(actionPhase);

        assertEquals(planningPhase,roundsManager.getPrevPhase());
    }

    /**
     * Tests next round management when the current round number is the maximum
     */
    @Test
    public void hasNotNextRound(){
        RoundsManager roundsManager = new RoundsManager();
        roundsManager.setRoundNum(roundsManager.getMAX_ROUND_NUM());

        assertFalse(roundsManager.hasNextRound());
    }

    /**
     * Tests a round having another round after it, considering the case in which a round is not the last
     */
    @Test
    public void hasNextRound(){
        RoundsManager roundsManager = new RoundsManager();
        roundsManager.setRoundNum(roundsManager.getMAX_ROUND_NUM()-1);

        assertTrue(roundsManager.hasNextRound());
    }
}
