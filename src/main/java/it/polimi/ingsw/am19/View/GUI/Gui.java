package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
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
    /** the client of this user, used to send messages to the server */
    private Client myClient;

    /** the nickname of the user */
    private String nickname;

    /** cache used to store objects to be displayed on the view */
    private Cache cache;

    /** the controller of the current scene */
    private SceneController currController;

    /** the current scene displayed */
    private Scene currScene;

    /** a reference to the stage */
    private Stage stage;

    /** the fxml file for the scene where the connection-related parameters are asked */
    private final String CONNECTION = "/it/polimi/ingsw/am19.View.GUI/Connection.fxml";

    /** the fxml file for the scene where the game options are asked*/
    private final String GAME_OPT = "/it/polimi/ingsw/am19.View.GUI/GameOptions.fxml";

    /** the fxml file for the login scene */
    private final String LOGIN = "/it/polimi/ingsw/am19.View.GUI/Login.fxml";

    /** the fxml file for the scene where the username of a saved match are asked*/
    private final String USERNAMES_OPT = "/it/polimi/ingsw/am19.View.GUI/UsernameOptions.fxml";

    /** the fxml file for the waiting-for-other-players-to-join scene */
    private final String WAITING = "/it/polimi/ingsw/am19.View.GUI/WaitingStart.fxml";

    /** the fxml file for the scene where the helper cards are shown */
    private final String HELPERCARD = "/it/polimi/ingsw/am19.View.GUI/HelperCard.fxml";

    /** the fxml file for the main scene */
    private final String MATCH = "/it/polimi/ingsw/am19.View.GUI/Board.fxml";

    /** the fxml file for the scene where the character cards are shown*/
    private final String ASK_CHARACTER = "/it/polimi/ingsw/am19.View.GUI/askCharacter.fxml";

    private final String PARAMETER_1 = "/it/polimi/ingsw/am19.View.GUI/Parameter1.fxml";
    private final String PARAMETER_2 = "/it/polimi/ingsw/am19.View.GUI/Parameter2.fxml";

    public Cache getCache() {
        return cache;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CONNECTION));
        Parent root = fxmlLoader.load();
        root.getStylesheets().add("@Login.css");
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

    /**
     * method used to create a client and connect it to the specified parameters
     * @param ip the ip address of the server the user wants to connect to
     * @param port the port of the sever the user wants to connect to
     */
    public void startView(String ip, int port) {
        myClient = new Client(ip, port, this);
        myClient.startPinging();
        myClient.receiveMessage();
    }

    @Override
    public void setDispatcher(Dispatcher dispatcher) {

    }

    @Override
    public void setPreviousMsg(Message msg) {

    }

    /**
     * setter for the cache attribute
     * @param cache the cache this view will pull data from
     */
    @Override
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    /**
     * setter for the nickname attribute
     * it also sets the cache's nickname attribute
     * @param nickname the nickname chosen by the user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
        cache.setNickname(nickname);
    }

    /**
     * getter for the nickname attribute
     * @return the nickname chosen by the user
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * setter for the myClient attribute
     * @param client the client this view needs to refer to to send messages
     */
    @Override
    public void setMyClient(Client client) {
        this.myClient = client;
    }

    /**
     * getter for the myClient attribute
     * @return the client bound to this view
     */
    public Client getMyClient() {
        return myClient;
    }

    /**
     * according to msg content, it tells the GameOptionsController controller what to display
     * @param msg the AskFirstPlayerMessage sent by the server
     */
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

    /**
     * when resuming a match it tells the UsernameOptionsController the available
     * nicknames to display, according to the msg content
     * @param msg the AskNicknameOptionsMessage sent by the server
     */
    @Override
    public void askNicknameFromResumedMatch(AskNicknameOptionsMessage msg) {
        changeScene(USERNAMES_OPT);

        Platform.runLater(() ->
            ((UsernameOptionsController)currController).setAvailableUsernames(msg.getNicknameAvailable()));
    }

    /**
     * when receiving logging, it tells the LoginController the available
     * match parameters to display as options
     * @param msg the AskLoginInfoMessage sent by the server
     */
    @Override
    public void askLoginInfo(AskLoginInfoMessage msg) {
        changeScene(LOGIN);

        Platform.runLater(() ->
                ((LoginController)currController).setOptions(msg.getTowerColorsAvailable(),msg.getWizardFamiliesAvailable()));
    }

    /**
     * according to msg content, it tells the HelperCardController the list of available cards
     * to be displayed
     * @param msg the message sent by the server containing the availableHelperCards
     */
    @Override
    public void showHelperOptions(AskHelperCardMessage msg) {
        changeScene(HELPERCARD);

        Platform.runLater(()->
                ((HelperCardController)currController).setHelperCardList(msg.getPlayableHelperCard()));
    }

    /**
     * method used to display the main scene
     * and let the user select the student they want to move and where
     * @param msg the AskEntranceMoveMessage sent by the server
     */
    @Override
    public void askEntranceMove(AskEntranceMoveMessage msg) {
        changeScene(MATCH);

        Platform.runLater(() -> {
            ((MatchController)currController).setCache(cache);
            ((MatchController)currController).drawScene();
            ((MatchController)currController).moveStudentPhase();
        });

    }

    /**
     * method used to display the main scene
     * and let the user choose an island where they want to move mother nature to
     */
    @Override
    public void askMotherNatureStep() {
        changeScene(MATCH);

        Platform.runLater(() -> {
            ((MatchController)currController).setCache(cache);
            ((MatchController)currController).drawScene();
            ((MatchController)currController).moveMotherNaturePhase();
        });
    }

    /**
     * method used to display the main scene
     * and let the user choose which cloud they would like
     * @param msg the AskCloudMessage sent by server
     */
    @Override
    public void askCloud(AskCloudMessage msg) {
        changeScene(MATCH);

        Platform.runLater(() -> {
            ((MatchController)currController).setCache(cache);
            ((MatchController)currController).drawScene();
            ((MatchController)currController).chooseCloudPhase();
        });
    }

    /**
     * it tells the CharacterCardController the available character cards options to be displayed,
     * according to msg content
     * @param msg the AskPlayCharacterCardMessage sent by server containing the options to present to the user
     */
    @Override
    public void askPlayCharacter(AskPlayCharacterCardMessage msg) {
        changeScene(ASK_CHARACTER);

        Platform.runLater(()->
                ((CharacterCardController)currController).setCharacterCards(msg.getAvailableCharacterCards()));
    }

    @Override
    public void askCharacterCardParameters(AskCharacterParameterMessage msg) {
        boolean color = msg.isRequireColor();
        boolean island = msg.isRequireIsland();
        boolean colorList = msg.isRequireColorList();

        if(colorList){
            AbstractCharacterCard card = ((CharacterCardController)currController).getCardChosen();

            changeScene(PARAMETER_2);
            Platform.runLater(()->{
                ((AskParameter2Controller)currController).setCard(card);
                ((AskParameter2Controller)currController).initializeScene();
            });
            //todo
            }
        else if(color || island){
            AbstractCharacterCard card = ((CharacterCardController)currController).getCardChosen();

            changeScene(PARAMETER_1);
            Platform.runLater(()->{
                ((AskParameter1Controller)currController).setCard(card);
                ((AskParameter1Controller)currController).initializeScene();
            });
        }
        else
            myClient.sendMessage(new ReplyCharacterParameterMessage(nickname,null,null,null));
    }

    /**
     * when end match message arrives, it shows an alert containing information about the winners.
     * If an error occurred an error alert is shown.
     * In both cases the stage is closed and connection with the client interrupted
     * @param msg the EndMatchMessage sent by the server
     */
    @Override
    public void endMatch(EndMatchMessage msg) {
        if(msg.getWinners() != null)
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("End match");
                alert.setContentText("Match ended. Winner:" + msg.getWinners().toString());
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> Platform.exit());
            });
        else
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("We are sorry, the match will interrupted due to a fatal error occurring\"");
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> Platform.exit());
            });
        myClient.disconnect();
    }

    /**
     * it tells the current controller to show the message passed as parameter in the current scene
     * @param msg the GenericMessage sent by the server
     */
    @Override
    public void generic(GenericMessage msg) {
        if (msg.getMessage().equals("waiting for others player to join..."))
            changeScene(WAITING);

        Platform.runLater(() -> currController.showGenericMsg(msg));
    }

    /**
     * it shows an alert containing the error message passed as parameter
     * @param msg the ErrorMessage sent by the server
     */
    @Override
    public void error(ErrorMessage msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(msg.getError());
            Optional<ButtonType> result = alert.showAndWait();
        });
    }

    /**
     * it changes the current scene, setting the right controller
     * @param controllerName the path that name od the next scene fxml
     */
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
}