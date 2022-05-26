package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Message.ReplyLoginInfoMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.util.List;

public class UsernameOptionsController implements SceneController{
    private Gui gui;

    @FXML
    private MenuButton savedUsernamesField;

    @FXML
    private Button submitButton;

    @FXML
    void sendUserData(ActionEvent event) {
        gui.getMyClient().sendMessage(new ReplyLoginInfoMessage(savedUsernamesField.getText(), null, null));
    }

    public void setAvailableUsernames(List<String> availableUsernames) {
        for (String username: availableUsernames){
            savedUsernamesField.getItems().add(new MenuItem(username));
        }

        EventHandler<ActionEvent> clickOnUsernamesMenuItem = (e) ->
                savedUsernamesField.setText(((MenuItem)e.getSource()).getText());

        for (MenuItem item: savedUsernamesField.getItems())
            item.setOnAction(clickOnUsernamesMenuItem);

    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }
}
