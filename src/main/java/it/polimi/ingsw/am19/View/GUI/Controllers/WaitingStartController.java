package it.polimi.ingsw.am19.View.GUI.Controllers;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.View.GUI.Controllers.SceneController;
import it.polimi.ingsw.am19.View.GUI.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * A Class for managing waiting while other clients still connecting
 */
public class WaitingStartController implements SceneController {
    /**
     * gui's reference
     */
    private Gui gui;

    @FXML
    private Label waitingLabel;

    /**
     * Sets gui's reference
     * @param gui id the gui's reference
     */
    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {
    }
}
