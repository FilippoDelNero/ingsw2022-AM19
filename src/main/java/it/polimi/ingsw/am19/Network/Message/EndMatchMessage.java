package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;

public class EndMatchMessage extends Message {
    private final String[] winners;

    public EndMatchMessage(String[] winners) {
        super("server", MessageType.END_MATCH_MESSAGE);
        this.winners = winners;
    }

    public String[] getWinners() {
        return winners;
    }

    @Override
    public String toString() {
        return "EndMatchMessage{" +
                "winners=" + Arrays.toString(winners) +
                '}';
    }
}
