package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoEntryTileInfluence;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testing class for the NoEntryTileCard
 */
public class NoEntryTileCardTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        bag.removeAll();
    }

    /**
     * testing the activateEffect method
     */
    @Test
    void activateEffectTest() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch m = new TwoPlayersMatch();

        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);

        m.addPlayer(player1);
        m.addPlayer(player2);

        m.initializeMatch();

        AbstractCharacterCard card = new NoEntryTileCard(m);

        Island island = m.getIslandManager().getIslands().get(2);

        card.activateEffect(island, null, null);
        assertTrue(island.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        m.getIslandManager().calculateInfluence(island);
        assertTrue(island.getInfluenceStrategy() instanceof StandardInfluence);
    }

    @Test
    void activateEffectFiveTimesTest() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch m = new TwoPlayersMatch();

        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);

        m.addPlayer(player1);
        m.addPlayer(player2);

        m.initializeMatch();

        AbstractCharacterCard card = new NoEntryTileCard(m);

        Island island1 = m.getIslandManager().getIslands().get(1);
        Island island2 = m.getIslandManager().getIslands().get(2);
        Island island3 = m.getIslandManager().getIslands().get(3);
        Island island4 = m.getIslandManager().getIslands().get(4);
        Island island5 = m.getIslandManager().getIslands().get(5);

        card.activateEffect(island1, null, null);
        assertTrue(island1.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island2, null, null);
        assertTrue(island2.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island3, null, null);
        assertTrue(island3.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island4, null, null);
        assertTrue(island4.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island5, null, null);
        assertTrue(island5.getInfluenceStrategy() instanceof StandardInfluence);
    }

    @Test
    void activateEffectFiveTimesRemovingOneTest() throws NoSuchColorException, TooManyStudentsException {
        AbstractMatch m = new TwoPlayersMatch();

        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);

        m.addPlayer(player1);
        m.addPlayer(player2);

        m.initializeMatch();

        AbstractCharacterCard card = new NoEntryTileCard(m);

        Island island1 = m.getIslandManager().getIslands().get(1);
        Island island2 = m.getIslandManager().getIslands().get(2);
        Island island3 = m.getIslandManager().getIslands().get(3);
        Island island4 = m.getIslandManager().getIslands().get(4);
        Island island5 = m.getIslandManager().getIslands().get(5);

        card.activateEffect(island1, null, null);
        assertTrue(island1.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island2, null, null);
        assertTrue(island2.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island3, null, null);
        assertTrue(island3.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        card.activateEffect(island4, null, null);
        assertTrue(island4.getInfluenceStrategy() instanceof NoEntryTileInfluence);

        m.getIslandManager().calculateInfluence(island2);
        assertTrue(island2.getInfluenceStrategy() instanceof StandardInfluence);

        card.activateEffect(island5, null, null);
        assertTrue(island5.getInfluenceStrategy() instanceof NoEntryTileInfluence);
    }
}
