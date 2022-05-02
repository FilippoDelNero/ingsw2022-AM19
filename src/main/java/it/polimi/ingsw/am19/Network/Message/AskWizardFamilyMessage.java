package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.List;

public class AskWizardFamilyMessage extends Message {
    private final List<WizardFamily> wizardFamilyAvailable;

    public AskWizardFamilyMessage(List<WizardFamily> wizardFamilyAvailable) {
        super("server", MessageType.ASK_WIZARD_FAMILY);
        this.wizardFamilyAvailable = wizardFamilyAvailable;
    }

    public List<WizardFamily> getWizardFamilyAvailable() {
        return wizardFamilyAvailable;
    }

    @Override
    public String toString() {
        return "AskWizardFamilyMessage{" +
                "wizardFamilyAvailable=" + wizardFamilyAvailable +
                '}';
    }
}
