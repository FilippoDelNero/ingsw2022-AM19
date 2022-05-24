package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gui extends Application implements View {
    /** cache used to store objects to be displayed on the view */
    private Cache cache;
    private Map<String,SceneController> controllers = new HashMap<>();
    private SceneController currController;
    private final String GAME_OPT = "/it/polimi/ingsw/am19.View.GUI/GameOptions.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource(GAME_OPT));
        controllers.put(GAME_OPT,fxmlLoader.getController());
        Parent root = fxmlLoader.load();
        stage.setFullScreen(true);
        Scene scene = new Scene(root, 1440, 900);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();
        setUpController();
    }

    private void setUpController(){
        currController = controllers.get(GAME_OPT);
    }

    @Override
    public void setViewCache(Cache viewCache) {

    }

    @Override
    public void initView() {

    }

    @Override
    public int newMatchNumOfPlayers() {
        return 0;
    }

    @Override
    public boolean newMatchIsExpert() {
        return false;
    }

    @Override
    public String askNickname() {
        return null;
    }

    @Override
    public String askNicknameFromList(List<String> nicknameAvailable) {
        return null;
    }

    @Override
    public WizardFamily askWizardFamily(List<WizardFamily> availableWizardFamilies) {
        return null;
    }

    @Override
    public TowerColor askTowerColor(List<TowerColor> availableTowerColor) {
        return null;
    }

    @Override
    public HelperCard askHelperCard(List<HelperCard> helperDeck) {
        return null;
    }

    @Override
    public String askEntranceMove(int movesLeft) {
        return null;
    }

    @Override
    public int askMotherNatureStep() {
        return 0;
    }

    @Override
    public int askCloud(List<Integer> cloudAvailable) {
        return 0;
    }

    @Override
    public Character askPlayCharacter(List<AbstractCharacterCard> characterOptions) {
        return null;
    }

    @Override
    public PieceColor askCharacterCardParamPieceColor() {
        return null;
    }

    @Override
    public int askCharacterCardParamIsland() {
        return 0;
    }

    @Override
    public List<PieceColor> askCharacterCardParamList() {
        return null;
    }

    @Override
    public void genericPrint(String toPrint) {

    }

    @Override
    public void printView(String nickname) {

    }

    @Override
    public boolean askResumeMatch() {
        return ((GameOptionsController)currController).resumeOldMatch();

    }
}