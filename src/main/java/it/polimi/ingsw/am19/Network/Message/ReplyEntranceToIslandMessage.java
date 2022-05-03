package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

/**
 * Reply to AskWhereMove when the player want to move the student on an Island
 * Contains the num of the Island chosen
 */
public class ReplyEntranceToIslandMessage extends Message {
    private final int island;
    private final PieceColor colorChosen;

    public ReplyEntranceToIslandMessage(String nickname, int island, PieceColor colorChosen) {
        super(nickname, MessageType.ENTRANCE_TO_ISLAND);
        this.island = island;
        this.colorChosen = colorChosen;
    }

    public int getIsland() {
        return island;
    }

    public PieceColor getColorChosen() {
        return colorChosen;
    }

    @Override
    public String toString() {
        return "ReplyEntranceToIslandMessage{" +
                "nickname= " + getNickname() +
                "island=" + island +
                ", colorChosen=" + colorChosen +
                '}';
    }
}
