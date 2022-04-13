package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

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
     * Constructor for a characterCards
     * @param character Character to create
     */
    public AbstractCharacterCard(Character character) {
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

    /**
     * Enable card's effect
     * @return true if card is in use
     */
    public boolean isActive() {
        return active;
    }

    public abstract void initialAction();

    public void activateEffect(Island island, PieceColor color){
        if (!wasUsed)
            this.wasUsed=true;
        this.active = true;
    }
}
