package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyLoginInfoMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class LoginController implements SceneController{
        private final Image blackTower = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/Towers/blackTower.png");
        private final Image whiteTower = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/Towers/whiteTower.png");
        private final Image greyTower = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/Towers/greyTower.png");
        private final Image king = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/HelperCard/king.png");
        private final Image witch = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/HelperCard/witch.png");
        private final Image warrior = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/HelperCard/warrior.png");
        private final Image shaman = new Image("file:src/main/resources/it/polimi/ingsw/am19.View.GUI/HelperCard/shaman.png");

        private final ImageView blackImageView = new ImageView(blackTower);
        private final ImageView whiteTowerImageView = new ImageView(whiteTower);
        private final ImageView greyTowerImageView = new ImageView(greyTower);
        private final ImageView witchImageView = new ImageView(witch);
        private final ImageView kingImageView = new ImageView(king);
        private final ImageView warriorImageView = new ImageView(warrior);
        private final ImageView shamanImageView = new ImageView(shaman);

        private Gui gui;

        public void initialize(){
                warningLabel.setVisible(false);
                warningLabel.setText("Please fill out all fields");

                usernameField.setOnKeyPressed( e -> {
                        if( e.getCode() == KeyCode.ENTER )
                                sendUserData();
                });

                towerColorField.setOnKeyPressed( e -> {
                        if( e.getCode() == KeyCode.ENTER )
                                sendUserData();
                });

                wizardFamilyField.setOnKeyPressed( e -> {
                        if( e.getCode() == KeyCode.ENTER )
                                sendUserData();
                });
        }

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
                sendUserData();
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
                        switch (color) {
                                case BLACK -> towerColorField.getItems().add(new MenuItem(color.toString().toLowerCase(), blackImageView));
                                case WHITE -> towerColorField.getItems().add(new MenuItem(color.toString().toLowerCase(), whiteTowerImageView));
                                case GREY -> towerColorField.getItems().add(new MenuItem(color.toString().toLowerCase(), greyTowerImageView));
                        }

                for (WizardFamily wizardFamily: wizardFamilies)
                        switch (wizardFamily) {
                                case WARRIOR -> {
                                        warriorImageView.setFitWidth(98.8);
                                        warriorImageView.setFitHeight(149.8);
                                        wizardFamilyField.getItems().add(new MenuItem(wizardFamily.toString().toLowerCase(),warriorImageView));}
                                case WITCH -> {
                                        witchImageView.setFitWidth(98.8);
                                        witchImageView.setFitHeight(149.8);
                                        wizardFamilyField.getItems().add(new MenuItem(wizardFamily.toString().toLowerCase(),witchImageView));}
                                case KING -> {
                                        kingImageView.setFitWidth(98.8);
                                        kingImageView.setFitHeight(149.8);
                                        wizardFamilyField.getItems().add(new MenuItem(wizardFamily.toString().toLowerCase(),kingImageView));}
                                case SHAMAN -> {
                                        shamanImageView.setFitWidth(98.8);
                                        shamanImageView.setFitHeight(149.8);
                                        wizardFamilyField.getItems().add(new MenuItem(wizardFamily.toString().toLowerCase(),shamanImageView));}
                        }


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

        @Override
        public void showGenericMsg(GenericMessage msg) {
        }

        private boolean checkInputValidity() {
                return usernameField.getText() != null && !usernameField.getText().equals("")
                        && !towerColorField.getText().equals("Tower color") //default option still set
                        && !wizardFamilyField.getText().equals("Wizard family"); //default option still set
        }

        private void sendUserData(){
                if (!checkInputValidity())
                        warningLabel.setVisible(true);
                else{
                        gui.setNickname(usernameField.getText());
                        gui.getMyClient().sendMessage(new ReplyLoginInfoMessage(
                                usernameField.getText(),
                                getTowerColor(towerColorField.getText()),
                                getWizardFamily(wizardFamilyField.getText())));
                }
        }
}
