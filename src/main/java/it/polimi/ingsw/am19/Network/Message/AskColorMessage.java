package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask a PieceColor
 */
public class AskColorMessage extends Message {
    public AskColorMessage() {
        super("server", MessageType.REQUEST_COLOR);
    }

    @Override
    public String toString() {
        return "AskColorMessage{}";
    }
}
