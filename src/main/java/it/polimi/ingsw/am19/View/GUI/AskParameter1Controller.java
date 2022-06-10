package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;

import it.polimi.ingsw.am19.Network.Message.ReplyCharacterParameterMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Map;

/**
 * Scene controller to give the first 2 parameter of the Character Cards
 * If the card require a single color, there is a menu button when you can choose one
 * if the card need an island, you can click on an island
 * When all parameter are chosen, the submit button will be enabled
 */
public class AskParameter1Controller implements SceneController {
    private Gui gui;
    private PieceColor color=null;
    private Integer island = null;
    private AbstractCharacterCard card;
    private Drawer drawer;

    /**
     * Set the card choose in the previous scene
     * @param card the card to use
     */
    public void setCard(AbstractCharacterCard card) {
        this.card = card;
    }

    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
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

    /**Method invoked when you click on the first island (the one on top-center)**/
    @FXML
    void selectIsland1() {
        chooseIsland(1);
        setButton();
    }

    /**Method invoked when you click on the second island**/
    @FXML
    void selectIsland2() {
        chooseIsland(2);
        setButton();
    }

    /**Method invoked when you click on the third island**/
    @FXML
    void selectIsland3() {
        chooseIsland(3);
        setButton();
    }

    /**Method invoked when you click on the fourth island**/
    @FXML
    void selectIsland4() {
        chooseIsland(4);
        setButton();
    }

    /**Method invoked when you click on the island n.5**/
    @FXML
    void selectIsland5() {
        chooseIsland(5);
        setButton();
    }

    /**Method invoked when you click on the island n.6**/
    @FXML
    void selectIsland6(){
        chooseIsland(6);
        setButton();
    }

    /**Method invoked when you click on the island n.7**/
    @FXML
    void selectIsland7() {
        chooseIsland(7);
        setButton();
    }

    /**Method invoked when you click on the island n.8**/
    @FXML
    void selectIsland8() {
        chooseIsland(8);
        setButton();
    }

    /**Method invoked when you click on the island n.9**/
    @FXML
    void selectIsland9() {
        chooseIsland(9);
        setButton();
    }

    /**Method invoked when you click on the island n.10**/
    @FXML
    void selectIsland10() {
        chooseIsland(10);
        setButton();
    }

    /**Method invoked when you click on the island n.11**/
    @FXML
    void selectIsland11() {
        chooseIsland(11);
        setButton();
    }

    /**Method invoked when you click on the island n.12**/
    @FXML
    void selectIsland12() {
        chooseIsland(12);
        setButton();
    }

    /**
     * Method invoked after a choice in the drop-down menu
     * @param event to take where the user have clicked
     */
    @FXML
    void setColor(ActionEvent event) {
        colorMenu.setText(((MenuItem)event.getSource()).getText());
        color = drawer.getColor(colorMenu.getText());
        setButton();
    }

    /**Send the message to reply after the submit button was clicked**/
    @FXML
    void sendReply() {
        gui.getMyClient().sendMessage(new ReplyCharacterParameterMessage(gui.getNickname(), color,island,null));
    }

    /**
     * Method invoked after the choice of an island
     * @param n the num of island which must remain visible
     */
    private void chooseIsland(int n){
        island=n-1;
        archipelago.getChildren().get(island).setOpacity(1.00);
        for(int i=0;i<12;i++){
            if(archipelago.getChildren().get(i).isVisible() && i != island)
                archipelago.getChildren().get(i).setOpacity(0.50);
        }
    }

    /**
     * Method to initialize the scene
     * Setting up the card, the student on it (if there are), the color menu and the archipelago
     */
    public void initializeScene(){
        Cache cache = gui.getCache();

        //Setting the character Card
        String imageUrl = drawer.getCharacterImagePath(card.getId());
        character.setImage(new Image(getClass().getResource(imageUrl).toExternalForm()));
        description.setText(drawer.getCharacterDescription(card));
        drawer.setStudentOnCard(card,onCardGrid);
        nameCharacter.setText(card.getId().toString());
        submit.setDisable(true);

        //Setting the color Menu. All color if the card haven't student, otherwise the color of students available on the card
        Map<PieceColor,Integer> students = card.getStudents();
        if(students!=null){
            for(PieceColor p : students.keySet()){
                if(students.get(p)>0){
                    colorMenu.getItems().add(new MenuItem(p.toString(), new ImageView(drawer.getStudentPath(p))));
                }
            }
        }
        else{
            for(PieceColor p : PieceColor.values())
                colorMenu.getItems().add(new MenuItem(p.toString(), new ImageView(drawer.getStudentPath(p))));
        }

        //Set the action after the choice of a color on the Menu
        for (MenuItem item: colorMenu.getItems())
            item.setOnAction(this::setColor);

        //If the card doesn't need an island, set invisible the archipelago. Same for the color (set invisible the menu button)
        if(!card.isRequiringIsland()){
            archipelago.setVisible(false);
            archipelago.setDisable(true);
            askIsland.setVisible(false);
        }
        else if (!card.isRequiringPieceColor()){
            askColor.setVisible(false);
            askColor.setDisable(true);
        }

        //Populate island and set invisible all Island after the archipelago size (caused by the merges)
        for(int i = 0; i < cache.getIslands().size(); i++) {
            drawer.populateIsland((GridPane)((AnchorPane) archipelago.getChildren().get(i)).getChildren().get(1), cache.getIslands().get(i));
        }
        for(int i = cache.getIslands().size(); i < 12; i++) {
            archipelago.getChildren().get(i).setVisible(false);
        }
    }

    /**Set the button visible if the check of parameter return true**/
    private void setButton(){
        if(checkParameter())
            submit.setDisable(false);
    }

    /**Return true if all parameter needed have been chosen**/
    private boolean checkParameter(){
        if(card.isRequiringPieceColor() && color == null)
            return false;
        else if(card.isRequiringIsland() && island == null)
            return false;
        return true;
    }
}

