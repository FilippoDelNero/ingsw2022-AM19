package it.polimi.ingsw.am19.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginController implements SceneController{
        private boolean isExpert;
        public void initialize(){
                easyRadioButton.setUserData(false);
                expertRadioButton.setUserData(true);
        }

        @FXML
        private ToggleGroup difficultyRadioButton;

        @FXML
        private RadioButton easyRadioButton;

        @FXML
        private RadioButton expertRadioButton;

        @FXML
        private MenuButton numPlayerField;

        @FXML
        private MenuButton savedUsernameField;

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

        }

        @FXML
        void difficultyRadioButtonSelected(ActionEvent event) {
                RadioButton selectedRadioButton = (RadioButton) difficultyRadioButton.getSelectedToggle();
                isExpert = (boolean) selectedRadioButton.getUserData();
        }

}
