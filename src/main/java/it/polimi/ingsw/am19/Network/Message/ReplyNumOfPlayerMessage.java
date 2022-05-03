package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskNum of player. Contains the num of player of the new match (must be 2 or 3)
 */
public class ReplyNumOfPlayerMessage extends Message {
    private final int numOfPlayers;

    public ReplyNumOfPlayerMessage(String nickname, int numOfPlayers) {
        super(nickname, MessageType.NUM_PLAYERS);
        this.numOfPlayers = numOfPlayers;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public String toString() {
        return "ReplyNumOfPlayerMessage{" +
                "nickname: "+ getNickname() +
                "numOfPlayers=" + numOfPlayers +
                '}';
    }
}
