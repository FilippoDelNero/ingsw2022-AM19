package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;

import it.polimi.ingsw.am19.Network.Message.ReplyCharacterParameterMessage;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

public class AskParameter1Controller implements SceneController {
    private Gui gui;
    private PieceColor color=null;
    private Integer island = null;
    private AbstractCharacterCard card;
    private final static Image redStudent = new Image("file:src/main/resources/Board/student_red.png");
    private final static Image greenStudent = new Image("file:src/main/resources/Board/student_green.png");
    private final static Image blueStudent = new Image("file:src/main/resources/Board/student_blue.png");
    private final static Image yellowStudent = new Image("file:src/main/resources/Board/student_yellow.png");
    private final static Image pinkStudent = new Image("file:src/main/resources/Board/student_pink.png");

    /** Image containing the sprite of mother nature */
    private final Image motherNatureImg = new Image("file:src/main/resources/Board/mother_nature.png");

    /** Image containing the sprite of a no-entry-tile */
    private final Image noEntryTileImg = new Image("file:src/main/resources/Board/noEntryTile.png");
    /** reference to the cache of this client*/
    private Cache cache;

    public void setCard(AbstractCharacterCard card) {
        this.card = card;
    }

    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {

    }

    @FXML
    private Group archipelago;

    @FXML
    private HBox askColor;

    @FXML
    private Label askIsland;

    @FXML
    private Button submit;

    @FXML
    private ImageView character;

    @FXML
    private MenuButton colorMenu;

    @FXML
    private TextArea description;

    @FXML
    private Label nameCharacter;

    @FXML
    private GridPane onCardGrid;


    @FXML
    void selectIsland1(MouseEvent event) {
        chooseIsland(1);
        setButton();
    }

    @FXML
    void selectIsland2(MouseEvent event) {
        chooseIsland(2);
        setButton();
    }

    @FXML
    void selectIsland3(MouseEvent event) {
        chooseIsland(3);
        setButton();
    }

    @FXML
    void selectIsland4(MouseEvent event) {
        chooseIsland(4);
        setButton();
    }

    @FXML
    void selectIsland5(MouseEvent event) {
        chooseIsland(5);
        setButton();
    }

    @FXML
    void selectIsland6(MouseEvent event){
        chooseIsland(6);
        setButton();
    }

    @FXML
    void selectIsland7(MouseEvent event) {
        chooseIsland(7);
        setButton();
    }

    @FXML
    void selectIsland8(MouseEvent event) {
        chooseIsland(8);
        setButton();
    }

    @FXML
    void selectIsland9(MouseEvent event) {
        chooseIsland(9);
        setButton();
    }

    @FXML
    void selectIsland10(MouseEvent event) {
        chooseIsland(10);
        setButton();
    }

    @FXML
    void selectIsland11(MouseEvent event) {
        chooseIsland(11);
        setButton();
    }

    @FXML
    void selectIsland12(MouseEvent event) {
        chooseIsland(12);
        setButton();
    }

    @FXML
    void setColor(ActionEvent event) {
        Drawer d = new Drawer();
        colorMenu.setText(((MenuItem)event.getSource()).getText());
        color = d.getColor(colorMenu.getText());
        setButton();
    }

    @FXML
    void sendReply(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyCharacterParameterMessage(gui.getNickname(), color,island,null));
    }

    private void chooseIsland(int n){
        island=n-1;
        archipelago.getChildren().get(island).setOpacity(1.00);
        for(int i=0;i<12;i++){
            if(archipelago.getChildren().get(i).isVisible() && i != island)
                archipelago.getChildren().get(i).setOpacity(0.50);
        }
    }

    public void initializeScene(){
        Drawer drawer = new Drawer();
        character.setImage(new Image(drawer.getCharacterImagePath(card.getId())));
        description.setText(drawer.getCharacterDescription(card));

        drawer.setStudentOnCard(card,onCardGrid);
        nameCharacter.setText(card.getId().toString());
        submit.setDisable(true);

        Map<PieceColor,Integer> students = card.getStudents();
        if(students!=null){
            for(PieceColor p : students.keySet()){
                if(students.get(p)>0){
                    colorMenu.getItems().add(new MenuItem(p.toString(), new ImageView(drawer.getStudentPath(p))));
                }
            }
        }
        else
            for(PieceColor p : PieceColor.values())
                colorMenu.getItems().add(new MenuItem(p.toString(), new ImageView(drawer.getStudentPath(p))));

        for (MenuItem item: colorMenu.getItems())
            item.setOnAction(this::setColor);

        if(!card.isRequiringIsland()){
            archipelago.setVisible(false);
            archipelago.setDisable(true);
            askIsland.setVisible(false);
        }
        else if (!card.isRequiringPieceColor()){
            askColor.setVisible(false);
            askColor.setDisable(true);
        }


        this.cache= gui.getCache();
        for(int i = 0; i < cache.getIslands().size(); i++) {
            drawer.populateIsland((GridPane)((AnchorPane) archipelago.getChildren().get(i)).getChildren().get(1), cache.getIslands().get(i));
        }
        for(int i = cache.getIslands().size(); i < 12; i++) {
            archipelago.getChildren().get(i).setVisible(false);
        }
    }


    private void setButton(){
        if(checkParameter())
            submit.setDisable(false);
    }

    private boolean checkParameter(){
        if(card.isRequiringPieceColor() && color == null)
            return false;
        else if(card.isRequiringIsland() && island == null)
            return false;
        return true;
    }
}

