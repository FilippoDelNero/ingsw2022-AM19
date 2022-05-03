package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.List;

/**
 * Message for asking the login info of a player
 */
public class AskLoginInfoMessage extends Message {
    private final List<TowerColor> towerColorsAvailable;
    private final List<WizardFamily> wizardFamiliesAvailable;

    public AskLoginInfoMessage(List<TowerColor> towerColorsAvailable, List<WizardFamily> wizardFamiliesAvailable) {
        super("server", MessageType.ASK_LOGIN_INFO);
        this.towerColorsAvailable = towerColorsAvailable;
        this.wizardFamiliesAvailable = wizardFamiliesAvailable;
    }

    public List<TowerColor> getTowerColorsAvailable() {
        return towerColorsAvailable;
    }

    public List<WizardFamily> getWizardFamiliesAvailable() {
        return wizardFamiliesAvailable;
    }

    @Override
    public String toString() {
        return "AskLoginInfoMessage{" +
                "towerColorsAvailable=" + towerColorsAvailable +
                ", wizardFamiliesAvailable=" + wizardFamiliesAvailable +
                '}';
    }
}
