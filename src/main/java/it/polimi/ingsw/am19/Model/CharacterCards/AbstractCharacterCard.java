package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;
import java.util.Objects;

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

    private Character id;
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
     * The effect to launch when we use a card
     * @param island references of an Island where we can change something (can be null)
     * @param color a PieceColor to use in the effect (can be null)
     */
    public void activateEffect(Island island, PieceColor color, List<PieceColor> pieceColorList){
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
