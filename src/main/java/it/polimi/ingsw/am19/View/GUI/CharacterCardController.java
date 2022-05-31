package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyPlayCharacterCardMessage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CharacterCardController implements SceneController {
    private Gui gui;
    private Drawer drawer;
    private List<AbstractCharacterCard> characterCards = new ArrayList<>();
    private AbstractCharacterCard cardChosen = null;


    public void setCharacterCards(List<AbstractCharacterCard> characterCards) {
        this.characterCards = characterCards;
        setLayout();
    }

    @Override
    public void setGui(Gui gui) {
        drawer = new Drawer();
        this.gui = gui;
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


    @FXML
    void negativeReplyMessage(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),null));
    }

    @FXML
    void onMouseEnteredEvent(MouseEvent event) {
        event.getPickResult().getIntersectedNode().setCursor(Cursor.HAND);
    }

    @FXML
    void onMouseExitedEvent(MouseEvent event) {

    }

    @FXML
    void useCharacter1(MouseEvent event) {
        cardChosen = characterCards.get(0);
        if(checkMinistrel())
            gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(0).getId()));
        else{
            cardChosen=null;
            ministrelError();
        }
    }

    @FXML
    void useCharacter2(MouseEvent event) {
        cardChosen = characterCards.get(1);
        if(checkMinistrel())
            gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(1).getId()));
        else{
            cardChosen=null;
            ministrelError();
        }
    }

    @FXML
    void useCharacter3(MouseEvent event) {
        cardChosen = characterCards.get(2);
        if(checkMinistrel())
            gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(2).getId()));
        else{
            cardChosen=null;
            ministrelError();
        }
    }

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

    private void ministrelError(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You can't use MINISTREL. You must have at least one student in your dining room");

            alert.showAndWait();
        });
    }

}

