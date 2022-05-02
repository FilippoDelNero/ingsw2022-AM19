package it.polimi.ingsw.am19.Network.Message;

public class AskColorMessage extends Message {
    public AskColorMessage() {
        super("server", MessageType.REQUEST_COLOR);
    }

    @Override
    public String toString() {
        return "AskColorMessage{}";
    }
}
