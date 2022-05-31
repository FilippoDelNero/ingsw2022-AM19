package it.polimi.ingsw.am19.View.GUI;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyCreateMatchMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyResumeMatchMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * A class for managing match set-up scene
 */
public class GameOptionsController implements SceneController {
    /**
     * true if the user clicks on expertRadioButton, false if he clicks on easyRadioButton
     */
    boolean isExpert;

    /**
     * it contains the number of players associated with a RadioButton clicked by the user
     * in the numPlayerGroup of RadioButtons
     */
    int numPlayers;

    /**
     * id hui's reference
     */
    private Gui gui;

    public void initialize(){
        //makes all buttons and labels (except for the newGameButton) hidden
        difficultyLabel.setVisible(false);
        numPlayerLabel.setVisible(false);
        twoPlayersRadioButton.setVisible(false);
        threePlayerRadioButton.setVisible(false);
        easyRadioButton.setVisible(false);
        expertRadioButton.setVisible(false);
        resumeGameButton.setVisible(false);
        newGameButton.setVisible(false);
        submitButton.setVisible(false);

        //associates some values to buttons of difficultyGroup and numPlayersGroup
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

    /**
     * when a button of difficultyRadioButton is selected, it reveals which of them
     * was been chosen by the user and retrieves its content
     * @param event the event triggered by selecting a button of the group
     */
    @FXML
    void difficultyRadioButtonSelected(ActionEvent event) {
        isExpert = (boolean) difficultyGroup.getSelectedToggle().getUserData();
    }

    /**
     * when a button of numPlayersRadioButton is selected, it reveals which of them
     * was been chosen by the user and retrieves its content
     * @param event the event triggered by selecting a button of the group
     */
    @FXML
    void numPlayerRadioButtonSelected(ActionEvent event) {
        numPlayers = (int) numPlayersGroup.getSelectedToggle().getUserData();
    }

    /**
     * when pressing the newGameButton, it hides it (together with resumeGameButton)
     * and makes visible all remaining labels and buttons
     * to let the user select match options
     * @param event event triggered by selecting the newGameButton
     */
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

    /**
     * when pressing resumeGameButton, a reply message is sent to the server
     * to express the need for resuming a previously saved match
     * @param event the event triggered by pressing resumeGameButton
     */
    @FXML
    void resumeGameButtonPressed(ActionEvent event) {
        gui.getMyClient().sendMessage(new ReplyResumeMatchMessage());
    }

    /**
     * when pressing submitButton, a reply message is sent to the server
     * to communicate the chosen difficulty and number of players
     * @param event the event triggered by pressing submitButton
     */
    @FXML
    void sendMatchData(ActionEvent event) {
        gui.getMyClient().sendMessage(new ReplyCreateMatchMessage(numPlayers, isExpert));
    }

    /**
     * it sets visible only the newGameButton
     */
    public void askNew(){
        newGameButton.setVisible(true);
    }

    /**
     * it sets visible both newGameButton and resumeButton
     */
    public void askResume(){
        newGameButton.setVisible(true);
        resumeGameButton.setVisible(true);
    }

    /**
     * sets gui's reference
     * @param gui id the gui's reference
     */
    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {
    }
}
