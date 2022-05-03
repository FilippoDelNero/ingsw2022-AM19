package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

/**
 * Reply to AskNickname. Contains a new nickname choose by a player
 */
public class ReplyLoginMessage extends Message {
    private final String nicknameChosen;
    private final TowerColor colorChosen;
    private final WizardFamily wizardFamilyChosen;

    public ReplyLoginMessage(String nicknameChosen, TowerColor colorChosen, WizardFamily wizardFamilyChosen) {
        super(null, MessageType.LOGIN_NICKNAME);
        this.nicknameChosen = nicknameChosen;
        this.colorChosen = colorChosen;
        this.wizardFamilyChosen = wizardFamilyChosen;
    }

    public String getNicknameChosen() {
        return nicknameChosen;
    }

    public TowerColor getColorChosen() {
        return colorChosen;
    }

    public WizardFamily getWizardFamilyChosen() {
        return wizardFamilyChosen;
    }

    @Override
    public String toString() {
        return "ReplyNicknameMessage{" +
                "nicknameChosen='" + nicknameChosen + '\'' +
                ", colorChosen=" + colorChosen +
                ", wizardFamilyChosen=" + wizardFamilyChosen +
                '}';
    }
}
