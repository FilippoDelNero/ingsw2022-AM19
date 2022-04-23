package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.MotherNature;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.MovementStrategies.PlusTwoMovement;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Map;

/**
 * Card to add two more step to MaxNumOfStep in this round
 */
public class MotherNaturePlusTwoCard extends AbstractCharacterCard{

    /**
     * References to MotherNature
     */
    private final MotherNature motherNature;

    /**
     * Constructor for the card
     * @param match references to the match. Is used to save the specific parameter the card needs
     */
    public MotherNaturePlusTwoCard(AbstractMatch match) {
        super(Character.POSTINO_MAGICO);
        this.motherNature = match.getMotherNature();
    }

    /**
     * Do nothing
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
     * Change the current strategy to MotherNature
     * @param island not used in this card
     * @param color not used in this card
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);
        motherNature.setCurrMovementStrategy(new PlusTwoMovement());
        active = false;
    }
}
