package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Client.Client;
import it.polimi.ingsw.am19.Network.Client.Dispatcher;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Gui extends Application implements View {
    private Client myClient;
    private String nickname;

    /** cache used to store objects to be displayed on the view */
    private Cache cache;
    private SceneController currController;
    private Scene currScene;
    private Stage stage;
    private final String CONNECTION = "/it/polimi/ingsw/am19.View.GUI/Connection.fxml";
    private final String GAME_OPT = "/it/polimi/ingsw/am19.View.GUI/GameOptions.fxml";
    private final String LOGIN = "/it/polimi/ingsw/am19.View.GUI/Login.fxml";
    private final String USERNAMES_OPT = "/it/polimi/ingsw/am19.View.GUI/UsernameOptions.fxml";
    private final String WAITING = "/it/polimi/ingsw/am19.View.GUI/WaitingStart.fxml";
    private final String HELPERCARD = "/it/polimi/ingsw/am19.View.GUI/HelperCard.fxml";
    private final String MATCH = "/it/polimi/ingsw/am19.View.GUI/Board.fxml";
    private final String ASK_CHARACTER = "/it/polimi/ingsw/am19.View.GUI/askCharacter.fxml";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CONNECTION));
        Parent root = fxmlLoader.load();
        //stage.setFullScreen(true);
        //stage.setResizable(false);
        Scene scene = new Scene(root, 1440, 900);
        currScene = scene;
        ConnectionController controller = fxmlLoader.getController();
        controller.setGui(this);
        currController = controller;
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();
    }

    public void startView(String ip, int port) {
        myClient = new Client(ip, port, this);
        myClient.startPinging();
        myClient.receiveMessage();
    }

    @Override
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void setMyClient(Client client) {
        this.myClient = client;

    }

    @Override
    public void setDispatcher(Dispatcher dispatcher) {

    }

    @Override
    public void setPreviousMsg(Message msg) {

    }

    @Override
    public void askLoginFirstPlayer(AskFirstPlayerMessage msg) {
        changeScene(GAME_OPT);

        Platform.runLater(() -> {
            if(msg.isMatchToResume())
                ((GameOptionsController)currController).askResume();
            else
                ((GameOptionsController)currController).askNew();
        });
    }

    @Override
    public void askNicknameFromResumedMatch(AskNicknameOptionsMessage msg) {
        changeScene(USERNAMES_OPT);

        Platform.runLater(() ->
            ((UsernameOptionsController)currController).setAvailableUsernames(msg.getNicknameAvailable()));

    }

    @Override
    public void askLoginInfo(AskLoginInfoMessage msg) {
        changeScene(LOGIN);

        Platform.runLater(() ->
                ((LoginController)currController).setOptions(msg.getTowerColorsAvailable(),msg.getWizardFamiliesAvailable()));
    }

    @Override
    public void showHelperOptions(AskHelperCardMessage msg) {
        changeScene(HELPERCARD);

        Platform.runLater(()->
                ((HelperCardController)currController).setHelperCardList(msg.getPlayableHelperCard()));
    }

    @Override
    public void askEntranceMove(AskEntranceMoveMessage msg) {
        changeScene(MATCH);

        Platform.runLater(() -> {
            ((MatchController)currController).setCache(cache);
            ((MatchController)currController).drawScene();
                });

    }

    @Override
    public void askMotherNatureStep() {

    }

    @Override
    public void askCloud(AskCloudMessage msg) {

    }

    @Override
    public void askPlayCharacter(AskPlayCharacterCardMessage msg) {
        changeScene(ASK_CHARACTER);

        Platform.runLater(()->
                ((CharacterCardController)currController).setCharacterCards(msg.getAvailableCharacterCards()));
    }

    @Override
    public void askCharacterCardParameters(AskCharacterParameterMessage msg) {

    }

    @Override
    public void endMatch(EndMatchMessage msg) {

    }

    @Override
    public void generic(GenericMessage msg) {
        if (msg.getMessage().equals("waiting for others player to join..."))
            changeScene(WAITING);
    }

    @Override
    public void error(ErrorMessage msg) {
        Platform.runLater(() -> {
            //((LoginController)currController).getWarningLabel().setVisible(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(msg.getError());
            Optional<ButtonType> result = alert.showAndWait();
        });
    }

    public void changeScene(String controllerName) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerName));
                Parent root = fxmlLoader.load();
                currController = fxmlLoader.getController();
                currController.setGui(this);
                stage.getScene().setRoot(root);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Client getMyClient() {
        return myClient;
    }
}