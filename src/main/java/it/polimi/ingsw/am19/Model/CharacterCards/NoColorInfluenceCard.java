package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoColorInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Map;

/**
 * Character Card for setting a NoColorInfluence strategy for the next calculateInfluence
 */
public class NoColorInfluenceCard extends AbstractCharacterCard{
    /**
     * A references to the IslandManager of the match
     */
    private final IslandManager islandManager;

    /**
     * Constructor for the card
     * @param match references to the match. Is used to save the specific parameter the card needs
     */
    public NoColorInfluenceCard(AbstractMatch match) {
        super(Character.FUNGARO);
        this.islandManager = match.getIslandManager();
    }

    /**
     * do nothing
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
     * Set NoColorInfluence to the IslandManager
     * @param island is never used
     * @param color color to not consider in calculate influence
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) {
        super.activateEffect(island, color, pieceColorList);
        NoColorInfluence noColorInfluence = new NoColorInfluence();
        noColorInfluence.setColor(color);
        this.islandManager.setInfluenceStrategy(noColorInfluence);
    }
}
