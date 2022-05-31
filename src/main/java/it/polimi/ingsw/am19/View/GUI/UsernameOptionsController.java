package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyLoginInfoMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;

import java.util.List;

/**
 * A Class for managing username choice scene, when resuming a match
 */
public class UsernameOptionsController implements SceneController{
    /**
     * gui's reference
     */
    private Gui gui;

    /** the fxml file for the waiting-for-other-players-to-join scene */
    private final String WAITING = "/it/polimi/ingsw/am19.View.GUI/Login/WaitingStart.fxml";

    public void initialize(){
        warningLabel.setVisible(false);
        savedUsernamesField.setOnKeyPressed(e -> {
            if ( e.getCode() == KeyCode.ENTER )
            sendUserData();
        });
    }

    @FXML
    private MenuButton savedUsernamesField;

    @FXML
    private Button submitButton;

    @FXML
    private Label warningLabel;

    /**
     * when pressing submitButton, it manages user input
     * @param event event of pressing on submitButton
     */
    @FXML
    void sendUserData(ActionEvent event) {
        sendUserData();
    }

    /**
     * Sets up username options
     * @param availableUsernames the list of username to display as options
     */
    public void setAvailableUsernames(List<String> availableUsernames) {
        for (String username: availableUsernames){
            savedUsernamesField.getItems().add(new MenuItem(username));
        }

        EventHandler<ActionEvent> clickOnUsernamesMenuItem = (e) ->
                savedUsernamesField.setText(((MenuItem)e.getSource()).getText());

        for (MenuItem item: savedUsernamesField.getItems())
            item.setOnAction(clickOnUsernamesMenuItem);

    }

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

    /**
     * if a username was selected, it sends a reply message to server
     * communicating it the chosen username
     */
    private void sendUserData(){
        if (savedUsernamesField.getText().equals("Username"))
            warningLabel.setVisible(true);
        else {
            gui.setNickname(savedUsernamesField.getText());
            gui.getMyClient().sendMessage(new ReplyLoginInfoMessage(savedUsernamesField.getText(), null, null));
            gui.changeScene(WAITING);
        }
    }
}
