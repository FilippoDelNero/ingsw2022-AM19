package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoEntryTileInfluence;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Utilities.Notification;

import java.util.List;
import java.util.Map;

/**
 * class representing the Grandmother Card
 */
public class NoEntryTileCard extends AbstractCharacterCard{
    /**
     * a reference to the match's island manager
     */
    private final IslandManager islandManager;

    /**
     * the number of No Entry Tile currently in the use
     * it makes sure that at any point no more than 4 No Entry Tile are in use
     */
    private int numberOfNoEntryTiles;

    /**
     * the influence strategy that this card will set
     */
    private final InfluenceStrategy strategy = new NoEntryTileInfluence();

    /**
     * card constructor
     * @param match a reference to the match
     */
    public NoEntryTileCard(AbstractMatch match) {
        super(Character.GRANNY);
        this.islandManager = match.getIslandManager();
    }

    /**
     * sets the number of No Entry Tiles to 4
     */
    @Override
    public void initialAction() {
        numberOfNoEntryTiles = 4;
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
     * if available puts a No Entry Tile on an island
     * @param island the island on which a No Entry Tile should be put
     * @param color should be null, not used
     * @param pieceColorList should be null, not used
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);
        if(numberOfNoEntryTiles > 0) {
            this.islandManager.setIslandInfluenceStrategy(island, strategy);
            numberOfNoEntryTiles--;
            this.islandManager.notifyObservers(Notification.UPDATE_ISLANDS);
        }
        else {
            numberOfNoEntryTiles = 4;
            for(Island isle : islandManager.getIslands()) {
                if(isle.getInfluenceStrategy() instanceof NoEntryTileInfluence)
                    numberOfNoEntryTiles--;
            }
            if(numberOfNoEntryTiles > 0) {
                this.islandManager.setIslandInfluenceStrategy(island, strategy);
                numberOfNoEntryTiles--;
                this.islandManager.notifyObservers(Notification.UPDATE_ISLANDS);
            }
        }

        this.active = false;
    }
}
