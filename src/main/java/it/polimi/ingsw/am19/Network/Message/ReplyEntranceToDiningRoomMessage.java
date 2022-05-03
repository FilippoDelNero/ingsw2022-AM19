package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

/**
 * Reply to AskWhereMove when the player want to move the student to it's diningRoom
 */
public class ReplyEntranceToDiningRoomMessage extends Message {
    private final PieceColor colorChosen;

    public ReplyEntranceToDiningRoomMessage(String nickname, PieceColor colorChosen) {
        super(nickname, MessageType.ENTRANCE_TO_DINING_ROOM);
        this.colorChosen = colorChosen;
    }

    public PieceColor getColorChosen() {
        return colorChosen;
    }

    @Override
    public String toString() {
        return "ReplyEntranceToDiningRoomMessage{" +
                "nickname: " + getNickname() +
                "colorChosen=" + colorChosen +
                '}';
    }
}
