package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.IslandManager;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;

public class ExtraInfluenceCard extends AbstractCharacterCard {

    private final IslandManager islandManager;

    public ExtraInfluenceCard(AbstractMatch match) {
        super(Character.ARALDO);
        this.islandManager = match.getIslandManager();
    }


    @Override
    public void initialAction() {

    }

    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) {
        super.activateEffect(island, color,pieceColorList);
        this.islandManager.calculateInfluence(island);
        this.active = false;
    }

}
