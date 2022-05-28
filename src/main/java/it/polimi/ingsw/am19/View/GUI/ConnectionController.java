package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class ConnectionController implements SceneController{
    private Gui gui;

    public void initialize(){
        warningLabel.setVisible(false);
        warningLabel.setText("Please fill out all fields");
        genericMsgField.setVisible(false);

        ipAddress.setOnKeyPressed( e -> {
            if( e.getCode() == KeyCode.ENTER )
                sendConnectionData();
        } );

        portNumber.setOnKeyPressed( e -> {
            if( e.getCode() == KeyCode.ENTER )
                sendConnectionData();
        } );
    }

    @FXML
    private Pane pane;

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField portNumber;

    @FXML
    private Button submitButton;

    @FXML
    private Label warningLabel;

    @FXML
    private Label genericMsgField;

    @FXML
    void sendConnectionData(ActionEvent event) {
        sendConnectionData();
    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {
        //TODO eliminare
        System.out.println(msg.getMessage());
        genericMsgField.setText(msg.getMessage());
        genericMsgField.setVisible(true);
    }

    private boolean checkFieldsFilled() {
        return ipAddress.getText() != null && !ipAddress.getText().equals("")
                && portNumber.getText() != null && !portNumber.getText().equals("");
    }

    private boolean checkIpValidity(){
        return ipAddress.getText().matches("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }

    private void sendConnectionData(){
        if (!checkFieldsFilled()){
            warningLabel.setVisible(true);
        }
        else if (!checkIpValidity()){
            warningLabel.setVisible(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid IP address. Please retry");
                Optional<ButtonType> result = alert.showAndWait();
            });
        }
        else
            gui.startView(ipAddress.getText(), Integer.parseInt(portNumber.getText()));
    }
}
