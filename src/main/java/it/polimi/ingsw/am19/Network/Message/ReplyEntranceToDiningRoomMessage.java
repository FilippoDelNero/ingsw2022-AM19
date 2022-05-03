package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskWhereMove when the player want to move the student to it's diningRoom
 */
public class ReplyEntranceToDiningRoomMessage extends Message {
    public ReplyEntranceToDiningRoomMessage(String nickname) {
        super(nickname, MessageType.ENTRANCE_TO_DINING_ROOM);
    }

    @Override
    public String toString() {
        return "ReplyEntranceToDiningRoomMessage{}";
    }
}
