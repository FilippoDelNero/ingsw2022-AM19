package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.util.List;

/**
 * Reply to AskColorList.
 * Contains the list of PieceColor to use in the activateEffect
 */
public class ReplyColorListMessage extends Message {
    private final List<PieceColor> pieceColorList;

    public ReplyColorListMessage(String nickname, List<PieceColor> pieceColorList) {
        super(nickname, MessageType.CHOSEN_COLOR_LIST);
        this.pieceColorList = pieceColorList;
    }

    public List<PieceColor> getPieceColorList() {
        return pieceColorList;
    }

    @Override
    public String toString() {
        return "ReplyColorListMessage{" +
                "nickname=" + getNickname() +
                ", pieceColorList=" + pieceColorList +
                '}';
    }
}
