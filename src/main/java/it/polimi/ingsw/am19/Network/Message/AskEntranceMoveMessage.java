package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask what and where move a student from the entrance in the first part of actionPhase
 */
public class AskEntranceMoveMessage extends Message {
    private int movesLeft;
    public AskEntranceMoveMessage(int movesLeft) {
        super("server", MessageType.ENTRANCE_MOVE);
        this.movesLeft = movesLeft;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    @Override
    public String toString() {
        return "AskEntranceMoveMessage{movesLeft=" +
                movesLeft + "}";
    }
}
