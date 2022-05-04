package it.polimi.ingsw.am19.Network.Message;

public class ReplyCreateMatchMessage extends Message {
    private final int numOfPlayer;
    private final boolean isExpert;

    public ReplyCreateMatchMessage(int numOfPlayer, boolean isExpert) {
        super(null, MessageType.REPLY_CREATE_MATCH);
        this.numOfPlayer = numOfPlayer;
        this.isExpert = isExpert;
    }

    public int getNumOfPlayer() {
        return numOfPlayer;
    }

    public boolean isExpert() {
        return isExpert;
    }

    @Override
    public String toString() {
        return "ReplyCreateMatchMessage{" +
                "numOfPlayer=" + numOfPlayer +
                ", isExpert=" + isExpert +
                '}';
    }
}
