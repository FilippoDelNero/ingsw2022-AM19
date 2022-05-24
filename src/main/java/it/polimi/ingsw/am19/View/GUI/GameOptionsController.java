package it.polimi.ingsw.am19.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class GameOptionsController implements SceneController{
    boolean playOldMatch;

    public void initialize(){
        difficultyLabel.setVisible(false);
        numPlayerLabel.setVisible(false);
        twoPlayersRadioButton.setVisible(false);
        threePlayerRadioButton.setVisible(false);
        easyRadioButton.setVisible(false);
        expertRadioButton.setVisible(false);
        resumeGameButton.setVisible(false);
    }

    @FXML
    private ToggleGroup difficultyGroup;

    @FXML
    private Label difficultyLabel;

    @FXML
    private RadioButton easyRadioButton;

    @FXML
    private RadioButton expertRadioButton;

    @FXML
    private Button newGameButton;

    @FXML
    private Label numPlayerLabel;

    @FXML
    private ToggleGroup numPlayersGroup;

    @FXML
    private Button resumeGameButton;

    @FXML
    private MenuButton savedUsernameField;

    @FXML
    private RadioButton threePlayerRadioButton;

    @FXML
    private RadioButton twoPlayersRadioButton;

    @FXML
    void difficultyRadioButtonSelected(ActionEvent event) {

    }

    @FXML
    void newGameButtonPressed(ActionEvent event) {
        playOldMatch = false;

    }

    @FXML
    void numPlayerRadioButtonSelected(ActionEvent event) {

    }

    @FXML
    void resumeGameButtonPressed(ActionEvent event) {
        playOldMatch = true;
    }

    @FXML
    void sendMatchData(ActionEvent event) {

    }

    public boolean resumeOldMatch(){
        resumeGameButton.setVisible(true);
    return false;
    }

    public void newGame(){
        difficultyLabel.setVisible(true);
        numPlayerLabel.setVisible(true);
        twoPlayersRadioButton.setVisible(true);
        threePlayerRadioButton.setVisible(true);
        easyRadioButton.setVisible(true);
        expertRadioButton.setVisible(true);
        resumeGameButton.setVisible(true);
    }

}
