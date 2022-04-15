package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Map;

/**
 * class representing the Herald Card
 */
public class ExtraInfluenceCard extends AbstractCharacterCard {
    /**
     * a reference to the match's island manager
     */
    private final IslandManager islandManager;

    /**
     * card constructor
     * @param match a reference to the match
     */
    public ExtraInfluenceCard(AbstractMatch match) {
        super(Character.ARALDO);
        this.islandManager = match.getIslandManager();
    }

    /**
     * not available for this card
     */
    @Override
    public void initialAction() {

    }

    /**
     * Do nothing
     * @return null
     */
    @Override
    public Map<PieceColor, Integer> getStudents() {
        return null;
    }

    /**
     * let the player choose an island on which he/she wants to calculate the influence on
     * @param island the island on which the player wants to calculate influence on
     * @param color should be null, not used
     * @param pieceColorList should be null, not used
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) {
        super.activateEffect(island, color,pieceColorList);
        this.islandManager.calculateInfluence(island);
        this.active = false;
    }

}
