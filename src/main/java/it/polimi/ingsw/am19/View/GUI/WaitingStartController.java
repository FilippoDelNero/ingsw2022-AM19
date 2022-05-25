package it.polimi.ingsw.am19.View.GUI;

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
}
