package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoTowersInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Map;

/**
 * class representing the Centaur Card
 */
public class NoTowersInfluenceCard extends AbstractCharacterCard {
    /**
     * a reference to the match's island manager
     */
    private final IslandManager islandManager;

    /**
     * the influence strategy that this card will set
     */
    private final InfluenceStrategy strategy = new NoTowersInfluence();

    /**
     * card constructor
     * @param match a reference to the match
     */
    public NoTowersInfluenceCard(AbstractMatch match) {
        super(Character.CENTAUR);
        this.islandManager = match.getIslandManager();
    }

    /**
     * not available for this card
     */
    @Override
    public void initialAction() {

    }

    /**
     * not available for this card
     * @return null
     */
    @Override
    public Map<PieceColor, Integer> getStudents() {
        return null;
    }

    /**
     * next time influence will be calculated on island it will be done using a NoTowerInfluence strategy
     * @param island should be null, not used
     * @param color should be null, not used
     * @param pieceColorList should be null, not used
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);
        this.islandManager.setInfluenceStrategy(strategy);
        this.active = false;
    }
}
