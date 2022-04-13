package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.PlusTwoInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.Match;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

/**
 * class representing the Knight Card
 */
public class PlusTwoInfluenceCard extends AbstractCharacterCard{
    /**
     * a reference to the match's island manager
     */
    private final IslandManager islandManager;

    /**
     * a reference to the match currently in use
     */
    private final Match match;

    /**
     * the influence strategy that this card will set
     */
    private final PlusTwoInfluence strategy = new PlusTwoInfluence();

    /**
     * card constructor
     * @param match a reference to the match
     */
    public PlusTwoInfluenceCard(AbstractMatch match) {
        super(Character.CAVALIERE);
        this.islandManager = match.getIslandManager();
        this.match = match;
    }

    /**
     * does nothing
     */
    @Override
    public void initialAction() {

    }

    /**
     * next time influence will be calculated on island it will be done using a PlusTwoInfluence strategy
     * @param island should be null, not used
     * @param color should be null, not used
     */
    @Override
    public void activateEffect(Island island, PieceColor color) {
        super.activateEffect(island, color);
        strategy.setCurrentPlayer(match.getCurrPlayer());
        this.islandManager.setInfluenceStrategy(strategy);
        this.active = false;
    }
}
