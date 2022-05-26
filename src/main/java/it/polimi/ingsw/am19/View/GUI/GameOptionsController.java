package it.polimi.ingsw.am19.View.GUI;
import it.polimi.ingsw.am19.Network.Message.ReplyCreateMatchMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyResumeMatchMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class GameOptionsController implements SceneController{
    boolean isExpert;
    int numPlayers;
    private Gui gui;

    public void initialize(){
        difficultyLabel.setVisible(false);
        numPlayerLabel.setVisible(false);
        twoPlayersRadioButton.setVisible(false);
        threePlayerRadioButton.setVisible(false);
        easyRadioButton.setVisible(false);
        expertRadioButton.setVisible(false);
        resumeGameButton.setVisible(false);
        newGameButton.setVisible(false);
        submitButton.setVisible(false);

        twoPlayersRadioButton.setUserData(2);
        threePlayerRadioButton.setUserData(3);
        easyRadioButton.setUserData(false);
        expertRadioButton.setUserData(true);
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
    private Button submitButton;

    @FXML
    private RadioButton threePlayerRadioButton;

    @FXML
    private RadioButton twoPlayersRadioButton;

    @FXML
    void difficultyRadioButtonSelected(ActionEvent event) {
        isExpert = (boolean) difficultyGroup.getSelectedToggle().getUserData();
    }

    @FXML
    void newGameButtonPressed(ActionEvent event) {
        difficultyLabel.setVisible(true);
        numPlayerLabel.setVisible(true);
        twoPlayersRadioButton.setVisible(true);
        threePlayerRadioButton.setVisible(true);
        easyRadioButton.setVisible(true);
        expertRadioButton.setVisible(true);
        resumeGameButton.setVisible(false);
        newGameButton.setVisible(false);
        submitButton.setVisible(true);
    }

    @FXML
    void numPlayerRadioButtonSelected(ActionEvent event) {
        numPlayers = (int) numPlayersGroup.getSelectedToggle().getUserData();
    }

    @FXML
    void resumeGameButtonPressed(ActionEvent event) {
        gui.getMyClient().sendMessage(new ReplyResumeMatchMessage());
    }

    @FXML
    void sendMatchData(ActionEvent event) {
        gui.getMyClient().sendMessage(new ReplyCreateMatchMessage(numPlayers, isExpert));

    }

    public void askResume(){
        newGameButton.setVisible(true);
        resumeGameButton.setVisible(true);
    }

    public void askNew(){
        newGameButton.setVisible(true);
    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }
}
