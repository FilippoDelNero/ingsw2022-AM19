package it.polimi.ingsw.am19.Network.Message;

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
