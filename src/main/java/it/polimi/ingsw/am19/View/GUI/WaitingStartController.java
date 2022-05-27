package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitingStartController implements SceneController {
    private Gui gui;

    @FXML
    private Label waitingLabel;

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {

    }
}
