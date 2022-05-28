package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyPlayCharacterCardMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class CharacterCardController implements SceneController {
    private Gui gui;

    private List<AbstractCharacterCard> characterCards = new ArrayList<>();

    public void setCharacterCards(List<AbstractCharacterCard> characterCards) {
        this.characterCards = characterCards;
        setLayout();
    }

    @Override
    public void setGui(Gui gui) {
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
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(0).getId()));
    }

    @FXML
    void useCharacter2(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(1).getId()));
    }

    @FXML
    void useCharacter3(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyPlayCharacterCardMessage(gui.getNickname(),characterCards.get(2).getId()));
    }

    private void setLayout(){
        character1.setImage(new Image(getImagePath(characterCards.get(0).getId())));
        description1.setText(getDescription(characterCards.get(0).getId()));
        character2.setImage(new Image(getImagePath(characterCards.get(1).getId())));
        description2.setText(getDescription(characterCards.get(1).getId()));
        character3.setImage(new Image(getImagePath(characterCards.get(2).getId())));
        description3.setText(getDescription(characterCards.get(2).getId()));
    }

    private String getImagePath(Character c){
        String path = "file:src/main/resources/it/polimi/ingsw/am19.View.GUI/CharacterCard/" + c + ".jpg";
        return path;
    }

    private String getDescription(Character c){
        String description = "Price:" + c.getPrice() + "\n" + c.getDescription();
        return description;
    }
}

