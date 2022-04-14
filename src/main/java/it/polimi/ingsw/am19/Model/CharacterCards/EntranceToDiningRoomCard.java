package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.InternalMoveStrategy.ReverseMove;
import it.polimi.ingsw.am19.Model.InternalMoveStrategy.StandardMove;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import java.util.List;
import java.util.Map;

/**
 * class representing the Minstrel Card
 */
public class EntranceToDiningRoomCard extends AbstractCharacterCard{
    /**
     * a reference to the match currently in use
     */
    private final AbstractMatch m;

    /**
     * the strategy that this card will set to move student from entrance to dining hall
     */
    private final StandardMove standardStrategy;

    /**
     * the strategy that this card will set to move student from dining hall to entrance
     */
    private final ReverseMove reverseStrategy;

    /**
     * card constructor
     * @param match a reference to the match
     */
    public EntranceToDiningRoomCard(AbstractMatch match) {
        super(Character.MENESTRELLO);
        this.m = match;
        this.standardStrategy = new StandardMove();
        this.reverseStrategy = new ReverseMove();
    }

    /**
     * does nothing
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
     * next time influence will be calculated on island it will be done using a PlusTwoInfluence strategy
     * @param island should be null, not used
     * @param color should be null, not used
     * @param pieceColorList the list of student that should be swapped, first the one the one that should go to the dining hall then the other one
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) {
        super.activateEffect(island, color, pieceColorList);

        Player currPlayer = m.getCurrPlayer();
        GameBoard currGameBoard = m.getGameBoards().get(currPlayer);

        for(int i = 0; i < pieceColorList.size() || i < 4; i += 2) {
            try {
                currGameBoard.moveStudentToDiningRoom(pieceColorList.get(i));
            } catch (NoSuchColorException | TooManyStudentsException e) {
                System.out.println("retry");
            }

            currGameBoard.setMoveStrategy(reverseStrategy);

            try {
                currGameBoard.moveStudentToDiningRoom(pieceColorList.get(i + 1));
            } catch (NoSuchColorException | TooManyStudentsException e) {
                try {
                    currGameBoard.moveStudentToDiningRoom(pieceColorList.get(i));
                } catch (NoSuchColorException | TooManyStudentsException ex) {
                    ex.printStackTrace();
                }
                System.out.println("retry");
            }

            currGameBoard.setMoveStrategy(standardStrategy);
        }



        this.active = false;
    }

}
