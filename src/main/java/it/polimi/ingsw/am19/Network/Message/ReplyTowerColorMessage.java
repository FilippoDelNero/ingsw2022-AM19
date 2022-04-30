package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

public class ReplyTowerColorMessage extends Message {
    private final TowerColor colorChosen;

    public ReplyTowerColorMessage(String nickname, TowerColor colorChosen) {
        super(nickname, MessageType.LOGIN_TOWER_COLOR);
        this.colorChosen = colorChosen;
    }

    public TowerColor getColorChosen() {
        return colorChosen;
    }

    @Override
    public String toString() {
        return "ReplyTowerColorMessage{" +
                "nickname= " + getNickname() +
                "colorChosen=" + colorChosen +
                '}';
    }
}
