package it.polimi.ingsw.am19.Model.Match;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertMatchDecoratorTest {
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
    public void createExpertMatch(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        assertEquals(20,decorator.getAvailableCoins());
        assertEquals(2,decorator.getWrappedMatch().numOfPlayers);
        assertEquals(12,decorator.getCharacterCards().size());
    }

    @Test
    public void addExpertPlayer(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);
        assertEquals(p1,decorator.getPlanningPhaseOrder().get(0));

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);
        assertEquals(p2,decorator.getPlanningPhaseOrder().get(1));
    }

    @Test
    public void initializeMatch(){
        AbstractMatch wrappedMatch = new TwoPlayersMatch();
        ExpertMatchDecorator decorator = new ExpertMatchDecorator(wrappedMatch);

        Player p1 = new Player("Dennis", TowerColor.BLACK, WizardFamily.KING,0);
        decorator.addPlayer(p1);

        Player p2 = new Player("Phil", TowerColor.WHITE, WizardFamily.WITCH,0);
        decorator.addPlayer(p2);

        assertEquals(20,decorator.getAvailableCoins());
        decorator.initializeMatch();
        assertEquals(1,p1.getCoins());
        assertEquals(1,p2.getCoins());
        assertEquals(18,decorator.getAvailableCoins());

    }

}