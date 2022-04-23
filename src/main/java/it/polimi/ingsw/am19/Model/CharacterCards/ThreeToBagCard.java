package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.ExceedingStudentsPerColorException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Map;

/**
 * Card to remove 3 student of a color from the dining hall of each player
 */
public class ThreeToBagCard extends AbstractCharacterCard {

    /**
     * References to the gameBoard of the match
     */
    private Map<Player, GameBoard> gameBoards;

    /**
     * References to the bag of the match
     */
    private Bag bag;

    /**
     * Constructor for the Card
     * @param match references to the match. Is used to save the specific parameter the card needs
     */
    public ThreeToBagCard(AbstractMatch match) {
        super(Character.LADRO);
        this.gameBoards = match.getGameBoards();
        this.bag=match.getBag();
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
     * Remove 3 student of the color chosen (or the maximum possible) from the DiningHall of each player
     * @param island null in this card
     * @param color the color to use
     * @param pieceColorList null in this card
     */
    @Override
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        super.activateEffect(island, color, pieceColorList);
        for(Player p: gameBoards.keySet()){
            int num = gameBoards.get(p).getDiningRoom().get(color);
            if (num>=3){
                gameBoards.get(p).getDiningRoom().replace(color,num-3);
                try {
                    bag.refillWith(color,3);
                } catch (ExceedingStudentsPerColorException e) {
                    e.printStackTrace();
                }
            }
            if (num>0 && num<3){
                gameBoards.get(p).getDiningRoom().replace(color,0);
                try {
                    bag.refillWith(color,num);
                } catch (ExceedingStudentsPerColorException e) {
                    e.printStackTrace();
                }
            }
        }
        active = false;
    }
}
