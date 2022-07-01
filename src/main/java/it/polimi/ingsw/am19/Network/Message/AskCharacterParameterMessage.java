package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for asking the parameter of the activateEffect of a CharacterCard
 */
public class AskCharacterParameterMessage extends Message {
    private final boolean requireColor;
    private final boolean requireIsland;
    private final boolean requireColorList;

    /**
     * class constructor
     * @param requireColor true if the selected card requires a color as a parameter
     * @param requireIsland true if the selected card requires an island as a parameter
     * @param requireColorList true if the selected requires a list of pieceColor as parameters
     */
    public AskCharacterParameterMessage(boolean requireColor, boolean requireIsland, boolean requireColorList) {
        super("server", MessageType.ASK_CHARACTER_PARAMETER);
        this.requireColor = requireColor;
        this.requireIsland = requireIsland;
        this.requireColorList = requireColorList;
    }

    /**
     * getter for requireColor boolean
     * @return true if the card requires a pieceColor
     */
    public boolean isRequireColor() {
        return requireColor;
    }

    /**
     * getter for requireIsland boolean
     * @return true if the card requires an island
     */
    public boolean isRequireIsland() {
        return requireIsland;
    }

    /**
     * getter for requireColorList boolean
     * @return true if the card requires a list of pieceColor
     */
    public boolean isRequireColorList() {
        return requireColorList;
    }

    @Override
    public String toString() {
        return "AskCharacterParameterMessage{" +
                "requireColor=" + requireColor +
                ", requireIsland=" + requireIsland +
                ", requireColorList=" + requireColorList +
                '}';
    }
}
