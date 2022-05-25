package it.polimi.ingsw.am19.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConnectionController implements SceneController{
    private Gui gui;

    public void initialize(){
        warningLabel.setVisible(false);
        warningLabel.setText("Please fill out all fields");
    }

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField portNumber;

    @FXML
    private Button submitButton;

    @FXML
    private Label warningLabel;

    @FXML
    void sendConnectionData(ActionEvent event) {
        if (!checkInputValidity())
            warningLabel.setVisible(true);
        else
            gui.startView(ipAddress.getText(), Integer.parseInt(portNumber.getText()));
    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    private boolean checkInputValidity() {
        return ipAddress.getText() != null && !ipAddress.getText().equals("")
                && portNumber.getText() != null && !portNumber.getText().equals("");
    }
}
