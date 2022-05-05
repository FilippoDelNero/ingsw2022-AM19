package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.util.ArrayList;
import java.util.List;

/**
 * Message for asking the login info of a player
 */
public class AskLoginInfoMessage extends Message {
    private final ArrayList<TowerColor> towerColorsAvailable;
    private final ArrayList<WizardFamily> wizardFamiliesAvailable;

    public AskLoginInfoMessage(ArrayList<TowerColor> towerColorsAvailable, ArrayList<WizardFamily> wizardFamiliesAvailable) {
        super("server", MessageType.ASK_LOGIN_INFO);
        this.towerColorsAvailable = towerColorsAvailable;
        this.wizardFamiliesAvailable = wizardFamiliesAvailable;
    }

    public ArrayList<TowerColor> getTowerColorsAvailable() {
        return towerColorsAvailable;
    }

    public ArrayList<WizardFamily> getWizardFamiliesAvailable() {
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
