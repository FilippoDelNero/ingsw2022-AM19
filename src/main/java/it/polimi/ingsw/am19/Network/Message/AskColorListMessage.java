package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask a PieceColor List for the activateEffect of some CharacterCards
 * Contains an int with the max length for the list
 */
public class AskColorListMessage extends Message {
    private final int lengthList;

    public AskColorListMessage( int lengthList) {
        super("server", MessageType.REQUEST_COLOR_LIST);
        this.lengthList = lengthList;
    }

    public int getLengthList() {
        return lengthList;
    }

    @Override
    public String toString() {
        return "AskColorListMessage{" +
                "lengthList=" + lengthList +
                '}';
    }
}
