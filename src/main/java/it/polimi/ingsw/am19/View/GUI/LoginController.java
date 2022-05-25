package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.ReplyLoginInfoMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class LoginController implements SceneController{
        private Gui gui;

        public void initialize(){
                warningLabel.setVisible(false);
                warningLabel.setText("Please fill out all fields");
        }
        @FXML
        private Pane pane;

        @FXML
        private Label warningLabel;

        @FXML
        private Button submitButton;

        @FXML
        private MenuButton towerColorField;

        @FXML
        private TextField usernameField;

        @FXML
        private MenuButton wizardFamilyField;

        @FXML
        void sendUserData(ActionEvent event) {
                if (!checkInputValidity())
                        warningLabel.setVisible(true);
                else{
                        gui.getMyClient().sendMessage(new ReplyLoginInfoMessage(
                                usernameField.getText(),
                                getTowerColor(towerColorField.getText()),
                                getWizardFamily(wizardFamilyField.getText())));
                }
        }


        private TowerColor getTowerColor(String towerColor){
               return switch (towerColor.toLowerCase()){
                        case "white" ->  TowerColor.WHITE;
                        case "black"->  TowerColor.BLACK;
                        case "grey"->  TowerColor.GREY;
                        default -> null;
                };
        }

        private WizardFamily getWizardFamily(String wizardFamily){
                return switch (wizardFamily.toLowerCase()){
                        case "king" ->  WizardFamily.KING;
                        case "witch"->  WizardFamily.WITCH;
                        case "shaman"->  WizardFamily.SHAMAN;
                        case "warrior"->  WizardFamily.WARRIOR;
                        default -> null;
                };
        }

        public void setOptions(ArrayList<TowerColor> towerColors, ArrayList<WizardFamily> wizardFamilies){
                for (TowerColor color: towerColors)
                        towerColorField.getItems().add(new MenuItem(color.toString()));

                for (WizardFamily wizardFamily: wizardFamilies)
                        wizardFamilyField.getItems().add(new MenuItem(wizardFamily.toString()));

                // create action event
                EventHandler<ActionEvent> clickOnTowerMenuItem = (e) ->
                        towerColorField.setText(((MenuItem)e.getSource()).getText());

                EventHandler<ActionEvent> clickOnWizardFamMenuItem = (e) ->
                        wizardFamilyField.setText(((MenuItem)e.getSource()).getText());

                // add action events to the menuitems
                for (MenuItem item: towerColorField.getItems())
                        item.setOnAction(clickOnTowerMenuItem);

                for (MenuItem item: wizardFamilyField.getItems())
                        item.setOnAction(clickOnWizardFamMenuItem);

        }

        @Override
        public void setGui(Gui gui) {
                this.gui = gui;
        }

        private boolean checkInputValidity() {
                return usernameField.getText() != null && !usernameField.getText().equals("")
                        && !towerColorField.getText().equals("Tower color") //default option still set
                        && !wizardFamilyField.getText().equals("Wizard family"); //default option still set
        }
}
