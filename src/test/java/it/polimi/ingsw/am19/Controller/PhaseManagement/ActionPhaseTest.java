package it.polimi.ingsw.am19.Controller.PhaseManagement;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Class for testing ActionPhase state
 */
public class ActionPhaseTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * Tests if in an expert match a character card was truly played in the action phase of a player
     */
    @Test
    public void testCardPlayed(){
        MatchController matchController = new MatchController();
        matchController.createNewMatch(2,true);
        matchController.getModel().initializeMatch();
        List<String> playersOrder = new ArrayList<>();
        playersOrder.add("Lau");
        playersOrder.add("Phil");

        ActionPhase actionPhase = new ActionPhase(playersOrder,matchController);
        actionPhase.setHasPlayedCard(true);

        assertTrue(actionPhase.getHasPlayedCard());
    }

    /**
     * Tests if after action phase initialization no character card was already played
     */
    @Test
    public void testCardNotPlayed(){
        MatchController matchController = new MatchController();
        matchController.createNewMatch(2,true);
        matchController.getModel().initializeMatch();
        List<String> playersOrder = new ArrayList<>();
        playersOrder.add("Lau");
        playersOrder.add("Phil");
        ActionPhase actionPhase = new ActionPhase(playersOrder,matchController);

        assertFalse(actionPhase.getHasPlayedCard());
    }

    /**
     * Test having to move a student being the first player's step in action phase
     */
    @Test
    public void testCurrentStep(){
        MatchController matchController = new MatchController();
        matchController.createNewMatch(2,true);
        matchController.getModel().initializeMatch();
        List<String> playersOrder = new ArrayList<>();
        playersOrder.add("Lau");
        playersOrder.add("Phil");
        ActionPhase actionPhase = new ActionPhase(playersOrder,matchController);

        assertEquals(ActionPhaseSteps.MOVE_STUD,actionPhase.getCurrStep());
    }

    /**
     * Tests being 3 the number of students that a player must move in his action phase in matches of two players
     */
    @Test
    public void testNumOfStudentsMoved(){
        MatchController matchController = new MatchController();
        matchController.createNewMatch(2,false);
        matchController.getModel().initializeMatch();
        List<String> playersOrder = new ArrayList<>();
        playersOrder.add("Lau");
        playersOrder.add("Phil");
        ActionPhase actionPhase = new ActionPhase(playersOrder,matchController);

        assertEquals(3,actionPhase.getMAX_NUM_STUDENTS());
    }

    /**
     * Tests being 4 the number of students that a player must move in his action phase in matches of three players
     */
    @Test
    public void testNumOfStudentsMovedBis(){
        MatchController matchController = new MatchController();
        matchController.createNewMatch(3,false);
        matchController.getModel().initializeMatch();
        List<String> playersOrder = new ArrayList<>();
        playersOrder.add("Lau");
        playersOrder.add("Phil");
        ActionPhase actionPhase = new ActionPhase(playersOrder,matchController);

        assertEquals(4,actionPhase.getMAX_NUM_STUDENTS());
    }

}
