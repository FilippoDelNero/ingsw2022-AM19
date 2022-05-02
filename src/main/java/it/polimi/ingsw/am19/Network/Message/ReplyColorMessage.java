package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

public class ReplyColorMessage extends Message {
    private final PieceColor colorChosen;

    public PieceColor getColorChosen() {
        return colorChosen;
    }

    public ReplyColorMessage(String nickname, PieceColor colorChosen) {
        super(nickname, MessageType.CHOSEN_COLOR);
        this.colorChosen = colorChosen;
    }
}
