package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
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
