package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.CheckProfessorStrategy.ChangeIfEqualCheckProfessor;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.Match;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import java.util.List;

/**
 * class representing the Farmer Card
 */
public class TakeProfessorCard extends AbstractCharacterCard{
    /**
     * a reference to the match's professor manager
     */
    private final ProfessorManager professorManager;

    /**
     * the strategy that this card will set
     */
    private final ChangeIfEqualCheckProfessor strategy;

    /**
     * a reference to the match currently in use
     */
    private final Match m;

    /**
     * card constructor
     * @param match a reference to the match
     */
    public TakeProfessorCard(AbstractMatch match) {
        super(Character.CONTADINO);
        this.m = match;
        this.professorManager = match.getProfessorManager();
        this.strategy = new ChangeIfEqualCheckProfessor();
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
     * @param pieceColorList should be null, not used
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) {
        super.activateEffect(island, color, pieceColorList);
        professorManager.setWhoUsedTheCard(m.getCurrPlayer().getCurrentCard().getNextRoundOrder());
        this.professorManager.setCurrentStrategy(strategy);
        this.active = false;
    }

}
