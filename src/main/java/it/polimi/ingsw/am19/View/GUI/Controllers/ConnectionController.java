package it.polimi.ingsw.am19.View.GUI.Controllers;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.View.GUI.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.util.Optional;

/**
 * A class for managing connection scene
 */
public class ConnectionController implements SceneController {
    /**
     * is gui's reference
     */
    private Gui gui;

    /**
     * Sets warning label hidden and sets its text property.
     * It also set event listeners for pressing enter button on keyboard events.
     * The handler acts like submit button was pressed
     */
    public void initialize(){
        warningLabel.setVisible(false);
        warningLabel.setText("Please fill out all fields");

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
    private TextField ipAddress;

    @FXML
    private TextField portNumber;

    @FXML
    private Button submitButton;

    @FXML
    private Label warningLabel;

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
     * makes an alert appear display info for the user.
     * The alert si blocking
     * @param msg the message that wraps what needs to be displayed
     */
    @Override
    public void showGenericMsg(GenericMessage msg) {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.initOwner(gui.getStage());
            alert.setContentText(msg.getMessage());
            alert.showAndWait();
        });
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
        //if fields' content is present, a check is also made on syntax
            else if (!checkIpValidity()){
                warningLabel.setVisible(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(gui.getStage());
                    alert.setTitle("Error");
                    alert.setContentText("Invalid IP address format. Please retry");
                    Optional<ButtonType> result = alert.showAndWait();
                });
            }
            else if (!checkPortNumValidity()){
                warningLabel.setVisible(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(gui.getStage());
                    alert.setTitle("Error");
                    alert.setContentText("Invalid port number format. Please retry");
                    Optional<ButtonType> result = alert.showAndWait();
                });
            }
        //
        else //only if fields are not empty/null and inputs are well formed
            gui.startView(ipAddress.getText(), Integer.parseInt(portNumber.getText()));
    }
}
