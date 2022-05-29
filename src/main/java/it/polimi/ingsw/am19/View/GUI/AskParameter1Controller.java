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
        colorMenu.setText(((MenuItem)event.getSource()).getText());
        color = getColor(colorMenu.getText());
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
        character.setImage(new Image(getImagePath(card.getId())));
        description.setText(getDescription(card.getId()));
        setStudentOnCard(card,onCardGrid);
        nameCharacter.setText(card.getId().toString());
        submit.setDisable(true);

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
            populateIsland((GridPane)((AnchorPane) archipelago.getChildren().get(i)).getChildren().get(1), cache.getIslands().get(i));
        }
        for(int i = cache.getIslands().size(); i < 12; i++) {
            archipelago.getChildren().get(i).setVisible(false);
        }
    }

    private PieceColor getColor (String s){
        return switch (s){
            case "GREEN"-> PieceColor.GREEN;
            case "BLUE"-> PieceColor.BLUE;
            case "PINK"->PieceColor.PINK;
            case "RED"-> PieceColor.RED;
            case "YELLOW"-> PieceColor.YELLOW;
            default -> null;
        };
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

    private String getImagePath(Character c){
        return "file:src/main/resources/it/polimi/ingsw/am19.View.GUI/CharacterCard/" + c + ".jpg";
    }

    private String getDescription(Character c){
        return "Price: " + c.getPrice() + "\n" + c.getDescription();
    }

    private void setStudentOnCard(AbstractCharacterCard card,GridPane onCardGrid){
        Map<PieceColor,Integer> studentOnCard = card.getStudents();
        if(studentOnCard!=null){
            int r=0;
            int c=0;
            for (PieceColor color : studentOnCard.keySet()){
                for(int k=0; k<studentOnCard.get(color);k++){
                    onCardGrid.add(createStudent(color,20),c,r);
                    r++;
                    if(r==3){
                        c=2;
                        r=0;
                    }
                }
            }
        }
    }

    private Circle createStudent(PieceColor pieceColor, int radius) {
        StudentPiece student = new StudentPiece(radius); //create a circle of radius 10
        student.setColor(pieceColor);
        switch (pieceColor) {
            case RED -> {
                student.setFill(new ImagePattern(redStudent));
                student.setStroke(Color.DARKRED);
            }
            case GREEN -> {
                student.setFill(new ImagePattern(greenStudent));
                student.setStroke(Color.LIME);
            }
            case BLUE -> {
                student.setFill(new ImagePattern(blueStudent));
                student.setStroke(Color.DARKBLUE);
            }
            case YELLOW -> {
                student.setFill(new ImagePattern(yellowStudent));
                student.setStroke(Color.LEMONCHIFFON);
            }
            case PINK -> {
                student.setFill(new ImagePattern(pinkStudent));
                student.setStroke(Color.PEACHPUFF);
            }
        }
        return student;
    }

    /**
     * method used to put student on the island
     * @param island the GridPane of the island we want to draw students on
     * @param reducedIsland the island saved in cache corresponding to the gui's GridPane
     */
    private void populateIsland(GridPane island, ReducedIsland reducedIsland) {
        Circle motherNature = new Circle(20);
        motherNature.setFill(new ImagePattern(motherNatureImg));

        for (PieceColor color : PieceColor.values()) {
            int num = reducedIsland.numOfStudents().get(color);
            if(num != 0) {
                switch (color) {
                    case RED -> {
                        island.add(createStudent(PieceColor.RED,10), 0, 0);
                        island.add(new Label("x" + num), 1, 0);
                    }
                    case GREEN -> {
                        island.add(createStudent(PieceColor.GREEN,10), 2, 0);
                        island.add(new Label("x" + num), 3, 0);
                    }
                    case BLUE -> {
                        island.add(createStudent(PieceColor.BLUE,10), 0, 1);
                        island.add(new Label("x" + num), 1, 1);
                    }
                    case YELLOW -> {
                        island.add(createStudent(PieceColor.YELLOW,10), 2, 1);
                        island.add(new Label("x" + num), 3, 1);
                    }
                    case PINK -> {
                        island.add(createStudent(PieceColor.PINK,10), 0, 2);
                        island.add(new Label("x" + num), 1, 2);
                    }
                }
            }
        }
        if(reducedIsland.presenceOfMotherNature())
            island.add(motherNature, 2,2);
        if(reducedIsland.noEntryTile()) {
            Circle noEntryTile = new Circle(15);
            noEntryTile.setFill(new ImagePattern(noEntryTileImg));
            island.add(noEntryTile, 2, 2);
        }
        if(reducedIsland.towerColor() != null)
            island.add(createTower(reducedIsland.towerColor()), 3, 2);
    }

    /**
     * method to create a circle of radius 10 with the color of a tower
     * @param towerColor the color of the tower
     * @return a colored circle
     */
    private Circle createTower(TowerColor towerColor) {
        Color color;
        Color strokeColor;

        if(towerColor == TowerColor.BLACK) {
            color = Color.BLACK;
            strokeColor = Color.DARKGRAY;
        }
        else if(towerColor == TowerColor.WHITE) {
            color = Color.WHITE;
            strokeColor = Color.LIGHTGRAY;
        }
        else {
            color = Color.GREY;
            strokeColor = Color.DARKSLATEGREY;
        }

        Circle tower = new Circle(10, color);
        tower.setStroke(strokeColor);

        return tower;
    }
}

