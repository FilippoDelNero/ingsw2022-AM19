package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

public class ReplyLoginInfoMessage extends Message {
    private final TowerColor towerColor;
    private final WizardFamily wizardFamily;

    public ReplyLoginInfoMessage(String nickname, TowerColor towerColor, WizardFamily wizardFamily) {
        super(nickname, MessageType.REPLY_LOGIN_INFO);
        this.towerColor = towerColor;
        this.wizardFamily = wizardFamily;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public WizardFamily getWizardFamily() {
        return wizardFamily;
    }


    @Override
    public String toString() {
        return "ReplyLoginInfoMessage{" +
                "nickname='" + getNickname() + '\'' +
                ", towercolor=" + towerColor +
                ", wizardFamily=" + wizardFamily +
                '}';
    }
}
