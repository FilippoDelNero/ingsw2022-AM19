package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyCharacterParameterMessage;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AskParameter2Controller implements SceneController {

    private Gui gui;
    private Drawer drawer = new Drawer();
    private AbstractCharacterCard card;
    private PieceColor color1 = null;
    private PieceColor color2= null;
    private PieceColor color3 = null;
    private PieceColor color4 = null;
    private PieceColor color5 = null;
    private PieceColor color6 = null;

    public void setCard(AbstractCharacterCard card) {
        this.card = card;
    }

    @FXML
    private ImageView character;

    @FXML
    private TextArea description;

    @FXML
    private Label from;

    @FXML
    private AnchorPane gameboard;

    @FXML
    private MenuButton menuButton1;

    @FXML
    private MenuButton menuButton2;

    @FXML
    private MenuButton menuButton3;

    @FXML
    private MenuButton menuButton4;

    @FXML
    private MenuButton menuButton5;

    @FXML
    private MenuButton menuButton6;

    @FXML
    private Label nameCharacter;

    @FXML
    private GridPane onCardGrid;

    @FXML
    private Button resetButton;

    @FXML
    private Button submitButton;

    @FXML
    private HBox thirdSwap;

    @FXML
    private Label to;

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void showGenericMsg(GenericMessage msg) {

    }


    @FXML
    void resetColor(MouseEvent event) {
        String s = "Select a color";
        menuButton1.setText(s);
        menuButton2.setText(s);
        menuButton3.setText(s);
        menuButton4.setText(s);
        menuButton5.setText(s);
        menuButton6.setText(s);
        color1=null;
        color2=null;
        color3=null;
        color4=null;
        color5=null;
        color6=null;
        submitButton.setDisable(true);
    }

    @FXML
    void sendColorList(MouseEvent event) {
        List<PieceColor> colorList = new ArrayList<>();
        //first swap
        if(color1!=null){
            colorList.add(color1);
            colorList.add(color2);
        }
        //second swap
        if(color3!=null){
            colorList.add(color3);
            colorList.add(color4);
        }
        //third swap
        if(color5!=null){
            colorList.add(color5);
            colorList.add(color6);
        }
        gui.getMyClient().sendMessage(new ReplyCharacterParameterMessage(gui.getNickname(), null,null,colorList));
    }

    public void initializeScene(){
        updateCard();
        submitButton.setDisable(true);
        updateButton();
        initializeButtons();
        populateGameboard();
    }

    private void populateGameboard(){
        ReducedGameBoard reduced = gui.getCache().getGameBoards().get(0);
        drawer.populateEntrance((GridPane) gameboard.getChildren().get(1), reduced);
        drawer.populateDiningRoom((GridPane) gameboard.getChildren().get(2),
                (GridPane) gameboard.getChildren().get(3), (GridPane) gameboard.getChildren().get(4),
                (GridPane) gameboard.getChildren().get(5), (GridPane) gameboard.getChildren().get(6), reduced);
        drawer.populateProfessors((GridPane) gameboard.getChildren().get(7), reduced);
        drawer.populateTowers((GridPane) gameboard.getChildren().get(8), reduced);
    }

    private void updateCard(){
        character.setImage(new Image(drawer.getCharacterImagePath(card.getId())));
        description.setText(drawer.getCharacterDescription(card));
        drawer.setStudentOnCard(card,onCardGrid);
        nameCharacter.setText(card.getId().toString());

        if(card.getId()== Character.MINSTREL){
            thirdSwap.setDisable(true);
            thirdSwap.setVisible(false);
        }

    }

    private void updateButton(){
        Map<PieceColor,Integer> entrance = gui.getCache().getGameBoards().get(0).entrance();
        if(card.getId()==Character.MINSTREL){
            for(PieceColor color : entrance.keySet())
                if(entrance.get(color)>0){
                    menuButton1.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton3.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton5.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                }


            Map<PieceColor,Integer> dining = gui.getCache().getGameBoards().get(0).diningRoom();
            for(PieceColor color: dining.keySet())
                if(dining.get(color)>0){
                    menuButton2.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton4.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton6.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                }
        }
        else{
            from.setText("CARD");
            to.setText("ENTRANCE");
            for(PieceColor color : entrance.keySet())
                if(entrance.get(color)>0){
                    menuButton2.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton4.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton6.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                }
            Map<PieceColor,Integer> students = card.getStudents();
            for(PieceColor color: students.keySet())
                if(students.get(color)>0){
                    menuButton1.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton3.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                    menuButton5.getItems().add(new MenuItem(color.toString(), new ImageView(drawer.getStudentPath(color))));
                }
        }
    }

    private void initializeButtons(){
        for(MenuItem i : menuButton1.getItems())
            i.setOnAction(this::setColor1);
        for(MenuItem i : menuButton2.getItems())
            i.setOnAction(this::setColor2);
        for(MenuItem i : menuButton3.getItems())
            i.setOnAction(this::setColor3);
        for(MenuItem i : menuButton4.getItems())
            i.setOnAction(this::setColor4);
        for(MenuItem i : menuButton5.getItems())
            i.setOnAction(this::setColor5);
        for(MenuItem i : menuButton6.getItems())
            i.setOnAction(this::setColor6);
    }

    @FXML
    void setColor1(ActionEvent event) {
        menuButton1.setText(((MenuItem)event.getSource()).getText());
        color1 = drawer.getColor(menuButton1.getText());
        checkDisableSubmit();
    }

    @FXML
    void setColor2(ActionEvent event) {
        menuButton2.setText(((MenuItem)event.getSource()).getText());
        color2 = drawer.getColor(menuButton2.getText());
        checkDisableSubmit();
    }

    @FXML
    void setColor3(ActionEvent event) {
        menuButton3.setText(((MenuItem)event.getSource()).getText());
        color3 = drawer.getColor(menuButton3.getText());
        checkDisableSubmit();
    }

    @FXML
    void setColor4(ActionEvent event) {
        menuButton4.setText(((MenuItem)event.getSource()).getText());
        color4 = drawer.getColor(menuButton4.getText());
        checkDisableSubmit();
    }

    @FXML
    void setColor5(ActionEvent event) {
        menuButton5.setText(((MenuItem)event.getSource()).getText());
        color5 = drawer.getColor(menuButton5.getText());
        checkDisableSubmit();
    }

    @FXML
    void setColor6(ActionEvent event) {
        menuButton6.setText(((MenuItem)event.getSource()).getText());
        color6 = drawer.getColor(menuButton6.getText());
        checkDisableSubmit();
    }

    private void checkDisableSubmit(){
        submitButton.setDisable(!checkColor());
    }

    private boolean checkColor(){
        String s = "Select a color";
        if(color1== null || color2 == null)
            return false;
        if (menuButton3.getText().equals(s) && menuButton4.getText().equals(s))
            return true;
        else if(color3==null || color4 == null)
            return false;

        if(card.getId()==Character.JESTER){
            if (menuButton5.getText().equals(s) && menuButton6.getText().equals(s))
                return true;
            else if(color5==null || color6==null)
                return false;
        }
        return true;
    }

}

