package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * abstract class used to model the 12 different cards
 */
public abstract class AbstractCharacterCard implements Serializable {
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
     * Is true when the card require a PieceColor for the activateEffect
     */
    protected boolean requiringPieceColor;

    /**
     * If true when the card require an Island for the activateEffect
     */
    protected boolean requiringIsland;

    /**
     * If true when the card require a PieceColorList for the activateEffect
     */
    protected boolean requiringPieceColorList;

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
        this.requiringPieceColor = character.isRequiringPieceColor();
        this.requiringIsland= character.isRequiringIsland();
        this.requiringPieceColorList= character.isRequiringPieceColorList();
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

    /**
     * getter for the id attribute
     * @return the id of the card
     */
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
     * Boolean for requiring a PieceColor to use in the activateEffect
     * @return if we need to require a PieceColor
     */
    public boolean isRequiringPieceColor() {
        return requiringPieceColor;
    }

    /**
     * Boolean for requiring an Island to use in the activateEffect
     * @return if we need to require an Island
     */
    public boolean isRequiringIsland() {
        return requiringIsland;
    }

    /**
     * Boolean for requiring a PieceColorList to use in the activateEffect
     * @return if we need to require a PieceColorList
     */
    public boolean isRequiringPieceColorList() {
        return requiringPieceColorList;
    }

    /**
     * Getter for the description of the card's effect
     * @return the string with the effect's description
     */
    public String getDescription() {
        return description;
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
     * Sets the boolean which says whether a CharacterCard is active or not
     * @param active is true if the card is active, false in the other case
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Sets the boolean which says whether a CharacterCard was previously active or not
     * @param wasUsed is true if the card was previously active, false in the other case
     */
    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }

    /**
     * Returns true if the card was previously active, false in the other case
     * @return true if the card was previously active, false in the other case
     */
    public boolean wasUsed() {
        return wasUsed;
    }

    /**
     * The effect to launch when we use a card
     * @param island references of an Island where we can change something (can be null)
     * @param color a PieceColor to use in the effect (can be null)
     * @param pieceColorList a list containing students
     * @throws NoSuchColorException exception thrown if the chosen color is not present
     * @throws TooManyStudentsException exception thrown if too many students are present
     */
    public void activateEffect (Island island, PieceColor color, List<PieceColor> pieceColorList) throws NoSuchColorException, TooManyStudentsException {
        if (!wasUsed)
            this.wasUsed=true;
        this.active = true;
    }

    /**
     * Redefines equals() method between two AbstractCharacterCards depending on their reference
     * @param o an AbstractCharacterCard to compare to this
     * @return true if two AbstractCharacterCards share the same reference, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCharacterCard that = (AbstractCharacterCard) o;
        return id == that.id;
    }
}
