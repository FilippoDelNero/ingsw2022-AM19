package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

/**
 * Reply to AskWizardFamily. Have to contain a WizardFamily still available
 */
public class ReplyWizardFamilyMessage extends Message {
    private final WizardFamily wizardFamilyChosen;

    public ReplyWizardFamilyMessage(String nickname, WizardFamily wizardFamilyChosen) {
        super(nickname, MessageType.LOGIN_WIZARD_FAMILY);
        this.wizardFamilyChosen = wizardFamilyChosen;
    }

    public WizardFamily getWizardFamilyChosen() {
        return wizardFamilyChosen;
    }

    @Override
    public String toString() {
        return "ReplyWizardFamilyMessage{" +
                "nickname="+ getNickname() +
                "wizardFamilyChosen=" + wizardFamilyChosen +
                '}';
    }
}
