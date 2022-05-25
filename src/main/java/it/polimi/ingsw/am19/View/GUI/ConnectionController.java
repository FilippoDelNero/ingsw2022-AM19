package it.polimi.ingsw.am19.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConnectionController implements SceneController{
    private Gui gui;

    /*
    public void initialize(){
        ipAddress.setVisible(false);
        portNumber.setVisible(false);
    }
     */

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField portNumber;

    @FXML
    private Button submitButton;

    @FXML
    void sendConnectionData(ActionEvent event) {
        gui.startView(ipAddress.getText(), Integer.parseInt(portNumber.getText()));
    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    /*
    public void showConnectionForm(){
        ipAddress.setVisible(true);
        portNumber.setVisible(true);
    }*/
}
