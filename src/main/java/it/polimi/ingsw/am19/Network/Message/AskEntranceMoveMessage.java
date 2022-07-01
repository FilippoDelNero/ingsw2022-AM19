package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask what and where move a student from the entrance in the first part of actionPhase
 */
public class AskEntranceMoveMessage extends Message {
    private final int movesLeft;

    /**
     * class constructor
     * @param movesLeft the number of students the player can still move
     */
    public AskEntranceMoveMessage(int movesLeft) {
        super("server", MessageType.ENTRANCE_MOVE);
        this.movesLeft = movesLeft;
    }

    /**
     * getter for the movesLeft attribute
     * @return the number of students left to be moved
     */
    public int getMovesLeft() {
        return movesLeft;
    }

    @Override
    public String toString() {
        return "AskEntranceMoveMessage{movesLeft=" +
                movesLeft + "}";
    }
}
