package it.polimi.ingsw.am19.Network.Message;

public class ReplyEntranceToDiningRoomMessage extends Message {
    public ReplyEntranceToDiningRoomMessage(String nickname) {
        super(nickname, MessageType.ENTRANCE_TO_DINING_ROOM);
    }

    @Override
    public String toString() {
        return "ReplyEntranceToDiningRoomMessage{}";
    }
}
