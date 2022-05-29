package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.Optional;

/**
 * A class for managing connection scene
 */
public class ConnectionController implements SceneController {
    /**
     * is gui's reference
     */
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

    /**
     * after pressing submit button, it manages fields' content
     * @param event the event triggered by pressing the submit button
     */
    @FXML
    void sendConnectionData(ActionEvent event) {
        sendConnectionData();
    }

    /**
     * set gui's reference
     * @param gui id the gui's reference
     */
    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    /**
     * makes genericMsgField visible
     * @param msg the message that wraps what needs to be displayed
     */
    @Override
    public void showGenericMsg(GenericMessage msg) {
        //TODO fix it
        //System.out.println(msg.getMessage());
        genericMsgField.setText(msg.getMessage());
        genericMsgField.setVisible(true);
    }

    private boolean checkFieldsFilled() {
        return ipAddress.getText() != null && !ipAddress.getText().equals("")
                && portNumber.getText() != null && !portNumber.getText().equals("");
    }

    private boolean checkIpValidity(){
        //check if ip address is well formed
        return ipAddress.getText().matches("^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }

    private boolean checkPortNumValidity(){
        //checks if port number is well formed
        return portNumber.getText().matches("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
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
                alert.setContentText("Invalid IP address format. Please retry");
                Optional<ButtonType> result = alert.showAndWait();
            });
        }
        else if (!checkPortNumValidity()){
            warningLabel.setVisible(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid port number format. Please retry");
                Optional<ButtonType> result = alert.showAndWait();
            });
        }
        else //only if fields are not empty/null and inputs are well formed
            gui.startView(ipAddress.getText(), Integer.parseInt(portNumber.getText()));
    }
}
