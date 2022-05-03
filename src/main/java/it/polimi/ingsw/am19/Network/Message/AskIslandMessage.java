package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask an Island number.
 * Contains the num of Island.
 * The reply must be between 1 and numMaxIsland
 */
public class AskIslandMessage extends Message {
    private final int numMaxIsland;

    public AskIslandMessage( int numMaxIsland) {
        super("server", MessageType.REQUEST_ISLAND);
        this.numMaxIsland = numMaxIsland;
    }

    public int getNumMaxIsland() {
        return numMaxIsland;
    }

    @Override
    public String toString() {
        return "AskIslandMessage{" +
                "numMaxIsland=" + numMaxIsland +
                '}';
    }
}
