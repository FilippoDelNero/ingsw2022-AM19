package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for asking the parameter of the activateEffect of a CharacterCard
 */
public class AskCharacterParameterMessage extends Message {
    private final boolean requireColor;
    private final boolean requireIsland;
    private final boolean requireColorList;

    public AskCharacterParameterMessage(boolean requireColor, boolean requireIsland, boolean requireColorList) {
        super("server", MessageType.ASK_CHARACTER_PARAMETER);
        this.requireColor = requireColor;
        this.requireIsland = requireIsland;
        this.requireColorList = requireColorList;
    }

    public boolean isRequireColor() {
        return requireColor;
    }

    public boolean isRequireIsland() {
        return requireIsland;
    }

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
