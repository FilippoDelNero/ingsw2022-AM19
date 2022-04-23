package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import java.util.List;
import java.util.Map;

/**
 * abstract class used to model the 12 different cards
 */
public abstract class AbstractCharacterCard {
    /**
     * The initial price for the CharacterCard
     */
    private final int price;

    /**
     * Is true when we are using the card's effect
     */
    protected boolean active;

    /**
     * If true, the price of card is increased by one coins
     */
    protected boolean wasUsed;

    /**
     * A short description for the effect for the Card
     */
    private final String description;

    /**
     * id number of the card, unique for each type of card
     */
    private final Character id;

    /**
     * Constructor for a characterCards
     * @param character Character to create
     */
    public AbstractCharacterCard(Character character) {
        this.id = character;
        this.price = character.getPrice();
        this.description = character.getDescription();
        this.wasUsed = false;
        this.active = false;
    }

    /**
     * Getter for the price of the Card
     * @return the price, increased by one if the card was previously used
     */
    public int getPrice() {
        if (wasUsed)
            return price +1;
        return price;
    }

    public Character getId() {
        return id;
    }

    /**
     * Enable card's effect
     * @return true if card is in use
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Initial action, to launch after the card withdrawal
     */
    public abstract void initialAction();

    /**
     * Return the student hosted in the card
     * @return the map of the students hosted (or null)
     */
    public abstract Map<PieceColor,Integer> getStudents();

    /**
     * The effect to launch when we use a card
     * @param island references of an Island where we can change something (can be null)
     * @param color a PieceColor to use in the effect (can be null)
     * @param pieceColorList a list containing students
     */
    public void activateEffect (Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        if (!wasUsed)
            this.wasUsed=true;
        this.active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCharacterCard that = (AbstractCharacterCard) o;
        return id == that.id;
    }
}
