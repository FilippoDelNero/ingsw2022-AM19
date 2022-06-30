package it.polimi.ingsw.am19.View.GUI.Controllers;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyPlayCharacterCardMessage;
import it.polimi.ingsw.am19.View.GUI.Gui;
import it.polimi.ingsw.am19.View.GUI.Utilities.Drawer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller to show the Character Cards of an expert match
 */
public class CharacterCardController implements SceneController {
    private Gui gui;
    private Drawer drawer;
    private List<AbstractCharacterCard> characterCards = new ArrayList<>();
    private AbstractCharacterCard cardChosen = null;

    /**
     * Method to set the character Cards available
     * @param characterCards the characters drawn
     */
    public void setCharacterCards(List<AbstractCharacterCard> characterCards) {
        this.characterCards = characterCards;
        setLayout();
    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {

    }

    @FXML
    private ImageView character1;

    @FXML
    private ImageView character2;

    @FXML
    private ImageView character3;

    @FXML
    private TextArea description1;

    @FXML
    private TextArea description2;

    @FXML
    private TextArea description3;

    @FXML
    private Label coinLabel;

    @FXML
    private GridPane onCardGrid1;

    @FXML
    private GridPane onCardGrid2;

    @FXML
    private GridPane onCardGrid3;

    /**
     * Methos called after the press of "NO" button
     */
    @FXML
    void negativeReplyMessage() {
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),null));
    }

    @FXML
    void onMouseEnteredEvent(MouseEvent event) {
        event.getPickResult().getIntersectedNode().setCursor(Cursor.HAND);
    }

    @FXML
    void onMouseExitedEvent() {

    }

    /**
     * Method to use the first character from the left
     */
    @FXML
    void useCharacter1() {
        cardChosen = characterCards.get(0);
        if(checkMinistrel())
            gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(0).getId()));
        else{
            cardChosen=null;
            ministrelError();
        }
    }

    /**
     * Method to use the second character from the left
     */
    @FXML
    void useCharacter2() {
        cardChosen = characterCards.get(1);
        if(checkMinistrel())
            gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(1).getId()));
        else{
            cardChosen=null;
            ministrelError();
        }
    }

    /**
     * Method to use the third character from the left
     */
    @FXML
    void useCharacter3() {
        cardChosen = characterCards.get(2);
        if(checkMinistrel())
            gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(2).getId()));
        else{
            cardChosen=null;
            ministrelError();
        }
    }

    /**
     * Method to initialize the scene
     * It set all the image, description and the student on th card (if present)
     */
    private void setLayout(){
        String imageUrl = drawer.getCharacterImagePath(characterCards.get(0).getId());
        character1.setImage(new Image(getClass().getResource(imageUrl).toExternalForm()));
        description1.setText(drawer.getCharacterDescription(characterCards.get(0)));
        drawer.setStudentOnCard(characterCards.get(0),onCardGrid1);

        imageUrl = drawer.getCharacterImagePath(characterCards.get(1).getId());
        character2.setImage(new Image(getClass().getResource(imageUrl).toExternalForm()));
        description2.setText(drawer.getCharacterDescription(characterCards.get(1)));
        drawer.setStudentOnCard(characterCards.get(1),onCardGrid2);

        imageUrl = drawer.getCharacterImagePath(characterCards.get(2).getId());
        character3.setImage(new Image(getClass().getResource(imageUrl).toExternalForm()));
        description3.setText(drawer.getCharacterDescription(characterCards.get(2)));
        drawer.setStudentOnCard(characterCards.get(2),onCardGrid3);
        coinLabel.setText("You have:\n" + gui.getCache().getGameBoards().get(0).coins()+ " coins");
    }

    public AbstractCharacterCard getCardChosen() {
        return cardChosen;
    }

    /**
     * Method to check if the player how want to use the "MINISTREL" card have at least a student on the dining
     * @return false if the dining is empty
     */
    private boolean checkMinistrel(){
        if(cardChosen.getId()== Character.MINSTREL){
            Map<PieceColor,Integer> dining = gui.getCache().getGameBoards().get(0).diningRoom();
            for(PieceColor color: dining.keySet())
                if(dining.get(color)>0)
                    return true;
                return false;
        }
        return true;
    }

    /**
     * Method to show an alert if the player want to use the "MINISTREL" card with an empty dining room
     */
    private void ministrelError(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You can't use MINISTREL. You must have at least one student in your dining room");
            alert.initOwner(gui.getStage());
            alert.showAndWait();
        });
    }

}

