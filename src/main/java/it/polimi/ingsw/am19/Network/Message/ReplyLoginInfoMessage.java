package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

public class ReplyLoginInfoMessage extends Message {
    private final String nickname;
    private final TowerColor towerColor;
    private final WizardFamily wizardFamily;
    private ClientManager clientManager = null;

    public ReplyLoginInfoMessage(String nickname, TowerColor towerColor, WizardFamily wizardFamily) {
        super(null, MessageType.REPLY_LOGIN_INFO);
        this.nickname = nickname;
        this.towerColor = towerColor;
        this.wizardFamily = wizardFamily;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public WizardFamily getWizardFamily() {
        return wizardFamily;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    @Override
    public String toString() {
        return "ReplyLoginInfoMessage{" +
                "nickname='" + nickname + '\'' +
                ", towercolor=" + towerColor +
                ", wizardFamily=" + wizardFamily +
                ", clientManager=" + clientManager +
                '}';
    }
}
