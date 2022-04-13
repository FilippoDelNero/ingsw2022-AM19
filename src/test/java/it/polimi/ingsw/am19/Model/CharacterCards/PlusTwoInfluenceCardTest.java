package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.PlusTwoInfluence;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlusTwoInfluenceCardTest {
    @Test
    void activateEffectTest() {
        AbstractMatch m = new TwoPlayersMatch();

        Player player1 = new Player("Laura", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);

        m.addPlayer(player1);
        m.addPlayer(player2);

        m.initializeMatch();
        m.setCurrPlayer(player1);

        AbstractCharacterCard card = new PlusTwoInfluenceCard(m);

        card.activateEffect(null, null);
        assertTrue(m.getIslandManager().getInfluenceStrategy() instanceof PlusTwoInfluence);

        m.getIslandManager().calculateInfluence(m.getIslandManager().getIslands().get(0));
        assertTrue(m.getIslandManager().getInfluenceStrategy() instanceof StandardInfluence);
    }
}
