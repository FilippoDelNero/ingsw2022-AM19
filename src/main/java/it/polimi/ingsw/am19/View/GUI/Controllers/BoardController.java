package it.polimi.ingsw.am19.View.GUI.Controllers;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.Utilities.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Utilities.ReducedObjects.ReducedIsland;
import it.polimi.ingsw.am19.View.GUI.Gui;
import it.polimi.ingsw.am19.View.GUI.Utilities.Drawer;
import it.polimi.ingsw.am19.View.GUI.Utilities.StudentPiece;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller for the main scene of the match
 * it allows to perform the action phase to the user
 */
public class BoardController implements SceneController {

    /** Group containing the anchorPanes, each one containing the gameBoard's components */
    @FXML private Group gameboards;

    /** Group containing the anchorPanes, each one containing each island's components */
    @FXML private Group archipelago;

    /** AnchorPane containing the cloud #1 components */
    @FXML private AnchorPane cloud1AP;

    /** AnchorPane containing the cloud #2 components */
    @FXML private AnchorPane cloud2AP;

    /** AnchorPane containing the cloud #3 components */
    @FXML private AnchorPane cloud3AP;

    /** AnchorPane containing the label to display generic messages */
    @FXML private AnchorPane genericMsgAP;

    /** Label used to display the name of the second player */
    @FXML private Label player2Name;

    /** Label used to display the name of the third player */
    @FXML private Label player3Name;

    /** Group of the labels used to display the number of coins of each player */
    @FXML private Group coinLabels;

    /** Button the user presses to play their helper card */
    @FXML private Button playHelperCard;

    /** Group of the imageView used to display the HelperCards played by the player */
    @FXML private Group helperCards;

    /** a reference to the gui class which "controls" this controller */
    private Gui gui;

    /** String containing the nickname of the player*/
    private String nickname;

    /** reference to the cache of this client*/
    private Cache cache;

    /** object used to draw components on the view */
    private Drawer draw;

    /** list of the AnchorPanes of the islands */
    private List<AnchorPane> islandsAP;

    /** list of the GridPanes of the islands*/
    private List<GridPane> islands;

    /** list of the GridPanes representing each component of the gameBoard #1 */
    private List<GridPane> gameboard1;

    /** list of the GridPanes representing each component of the gameBoard #2 */
    private List<GridPane> gameboard2;

    /** list of the GridPanes representing each component of the gameBoard #3 */
    private List<GridPane> gameboard3;

    /** list of the GridPanes of the clouds */
    private List<GridPane> clouds;

    /** list of the labels used to display each player's coins */
    private List<Label> labelsForCoins;

    /** list of imageViews used to display HelperCards */
    private List<ImageView> helperCardImages;

    /** attribute used to save the student's color that the user wants to move */
    private PieceColor studentToMove;

    /** attribute used to save the helperCardMessage sent by the server */
    private AskHelperCardMessage helperCardMessage;

    /**
     * method called automatically it populates the Lists of this class
     */
    public void initialize() {
        int i;
        islands = new ArrayList<>();
        islandsAP = new ArrayList<>();
        for(i = 0; i < 12; i++) {
            islandsAP.add((AnchorPane) archipelago.getChildren().get(i));
            islands.add((GridPane) islandsAP.get(i).getChildren().get(1));
        }

        List<AnchorPane> gameboardsAP = new ArrayList<>();
        labelsForCoins = new ArrayList<>();
        helperCardImages = new ArrayList<>();
        for(i = 0; i < 3; i++) {
            gameboardsAP.add((AnchorPane) gameboards.getChildren().get(i));
            labelsForCoins.add((Label) coinLabels.getChildren().get(i));
            helperCardImages.add((ImageView) helperCards.getChildren().get(i));
        }

        gameboard2 = new ArrayList<>();
        gameboard1 = new ArrayList<>();
        gameboard3 = new ArrayList<>();
        for(i = 0; i < 8; i++) {
            gameboard1.add(i, (GridPane) gameboardsAP.get(0).getChildren().get(i+1));
            gameboard2.add(i, (GridPane) gameboardsAP.get(1).getChildren().get(i+1));
            gameboard3.add(i, (GridPane) gameboardsAP.get(2).getChildren().get(i+1));
        }

        clouds = new ArrayList<>();
        clouds.add(0, (GridPane) cloud1AP.getChildren().get(1));
        clouds.add(1, (GridPane) cloud2AP.getChildren().get(1));
        clouds.add(2, (GridPane) cloud3AP.getChildren().get(1));
    }

    /**
     * setter for the cache attribute
     * @param cache the cache currently in use on this client
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    /**
     * setter for the gui attribute
     * @param gui the gui that "created" this controller
     */
    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    /**
     * setter for the drawer attribute
     * @param drawer the drawer created by the Gui class
     */
    public void setDrawer(Drawer drawer) {
        this.draw = drawer;
    }

    /**
     * method to show the user the generic messages
     * @param msg the GenericMessage sent by the server
     */
    @Override
    public void showGenericMsg(GenericMessage msg) {
        printGeneric(msg.toString());
    }

    /**
     * method that draws the scene using the parameters saved in cache
     */
    public void drawScene() {
        this.nickname = gui.getNickname();
        initializeGameBoards();
        initializeArchipelago();
        initializeClouds();
        initializeLabel();
        initializeHelperCards();
        playHelperCard.setVisible(false);
    }

    /**
     * method used to let user play a Helper Card by pressing a button
     * @param msg the AskHelperCardMessage that will be forwarded to HelperCardController when the user presses on a card
     */
    public void playHelperCard(AskHelperCardMessage msg) {
        helperCardMessage = msg;
        playHelperCard.setVisible(true);
        playHelperCard.setOnMouseClicked(this::changeSceneAndPlayHelper);
    }

    /**
     * method that sets up the view to let the user perform a move-student-sub-phase
     */
    public void moveStudentPhase() {
        gameboard1.get(0).setCursor(Cursor.HAND);
        gameboard1.get(0).setOnMouseClicked(this::pickStudentToMove);
        printGeneric("Select a student in your game-board's entrance to move");
    }

    /**
     * method that sets up the view to let the user perform a move-mother-nature-sub-phase
     */
    public void moveMotherNaturePhase() {
        for(GridPane gp : gameboard1) {
            gp.setOnMouseClicked(null);
            gp.setCursor(Cursor.DEFAULT);
        }

        for(GridPane gp : islands) {
            gp.setOnMouseClicked(this::getMotherNatureDestination);
            gp.setCursor(Cursor.HAND);
        }
        printGeneric("On which island do you want to move mother nature on?");
    }

    /**
     * method that sets up the view to let the user perform a choose-cloud-sub-phase
     */
    public void chooseCloudPhase(List<Integer> list) {
        for(GridPane gp : islands) {
            gp.setOnMouseClicked(null);
            gp.setCursor(Cursor.DEFAULT);
        }
        for(int i = 0; i < cache.getClouds().size(); i++) {
            if(list.contains(i)) {
                clouds.get(i).setOnMouseClicked(this::getChosenCloud);
                clouds.get(i).setCursor(Cursor.HAND);
            }
        }
        printGeneric("Which cloud would you like?");
    }

    /**
     * This method changes scene allowing the user to play a HelperCard
     * @param event the click of the user's mouse
     */
    public void changeSceneAndPlayHelper(MouseEvent event) {
        gui.playHelperCard(helperCardMessage);
    }

    /**
     * This method saves the color of the students clicked by the user
     * and sets the scene up to allow the user to select a destination
     * @param event the click of the user's mouse
     */
    public void pickStudentToMove(MouseEvent event) {
        Node clickedStudent = event.getPickResult().getIntersectedNode();
        if (clickedStudent != gameboard1.get(0)) {
            Integer colIndex = GridPane.getColumnIndex(clickedStudent);
            Integer rowIndex = GridPane.getRowIndex(clickedStudent);

            Node result = null;
            ObservableList<Node> children = gameboard1.get(0).getChildren();

            for (Node node : children) {
                ((StudentPiece) node).setRadius(10);
                if(Objects.equals(GridPane.getRowIndex(node), rowIndex) && Objects.equals(GridPane.getColumnIndex(node), colIndex)) {
                    result = node;
                }
            }

            if(result != null) {
                ((StudentPiece) result).setRadius(12);
                studentToMove = ((StudentPiece) result).getColor();
            }

            GridPane table = getTable(studentToMove);

            if(table != null) {
                table.setOnMouseClicked(this::getDestinationTable);
                table.setCursor(Cursor.HAND);

                for(GridPane gp : islands) {
                    gp.setOnMouseClicked(this::getDestinationIsland);
                    gp.setCursor(Cursor.HAND);
                }
            }
            printGeneric("You selected a " + studentToMove + ", where do you want to move it?");
        }
    }

    /**
     * method that sends a ReplyEntranceToDiningRoomMessage to the server via the client
     * called when the user select a table in the dining room in the move-student-sub-phase
     * @param event the click of the user's mouse
     */
    public void getDestinationTable(MouseEvent event) {
        gui.getMyClient().sendMessage(new ReplyEntranceToDiningRoomMessage(nickname, studentToMove));
    }

    /**
     * method that sends a ReplyEntranceToIslandMessage to the server via the client
     * called when the user select an island in the move-student-sub-phase
     * @param event the click of the user's mouse
     */
    public void getDestinationIsland(MouseEvent event) {
        try {
            GridPane clickedIsland = (GridPane) event.getPickResult().getIntersectedNode();
            int islandIndex;
            for(islandIndex = 0; islandIndex < islands.size(); islandIndex++) {
                if(islands.get(islandIndex) == clickedIsland)
                    break;
            }

            gui.getMyClient().sendMessage(new ReplyEntranceToIslandMessage(nickname, islandIndex, studentToMove));
        } catch (ClassCastException ignored) {}
    }

    /**
     * method that calculates the number of steps the user wants to do with mother nature
     * it then sends a ReplyMotherNatureStepMessage to the server via the client
     * @param event the click of the user's mouse
     */
    public void getMotherNatureDestination(MouseEvent event) {
        try {
            GridPane clickedIsland = (GridPane) event.getPickResult().getIntersectedNode();
            int step;
            int MNDestIndex;
            int MNDepIndex;
            int numOfIslands = cache.getIslands().size();

            for(MNDepIndex = 0; MNDepIndex < numOfIslands; MNDepIndex++) {
                if(cache.getIslands().get(MNDepIndex).presenceOfMotherNature())
                    break;
            }

            for(step = 0; step < numOfIslands; step++) {
                MNDestIndex = (MNDepIndex + step)%numOfIslands;
                if(islands.get(MNDestIndex) == clickedIsland)
                    break;
            }
            gui.getMyClient().sendMessage(new ReplyMotherNatureStepMessage(nickname, step));
        } catch (ClassCastException ignored) {}
    }

    /**
     * method that sends the server a ReplyCloudMessage with the index of the cloud chosen by the player
     * @param event the click of the user's mouse
     */
    public void getChosenCloud(MouseEvent event) {
        try {
            GridPane clickedIsland = (GridPane) event.getPickResult().getIntersectedNode();
            int numOfClouds = cache.getClouds().size();
            int chosenCloudIndex;
            for(chosenCloudIndex = 0; chosenCloudIndex < numOfClouds; chosenCloudIndex++) {
                if(clickedIsland == clouds.get(chosenCloudIndex))
                    break;
            }

            gui.getMyClient().sendMessage(new ReplyCloudMessage(nickname, chosenCloudIndex));

            for(GridPane gp : clouds) {
                gp.setOnMouseClicked(null);
                gp.setCursor(Cursor.DEFAULT);
            }
        } catch(ClassCastException ignored) {}
    }

    /**
     * method used to refresh every gameboard in the view
     */
    public void refreshGameboards() {
        for(int i = 0; i < 8; i++) {
            gameboard1.get(i).getChildren().clear();
            gameboard2.get(i).getChildren().clear();
            if(cache.getGameBoards().size() == 3) {
                gameboard3.get(i).getChildren().clear();
            }
        }
        initializeGameBoards();
    }

    /**
     * method used to refresh every island in the view
     */
    public void refreshIslands() {
        for (GridPane island : islands) {
            island.getChildren().clear();
        }
        initializeArchipelago();
    }

    /**
     * method used to refresh every cloud in the view
     */
    public void refreshClouds() {
        for(GridPane cloud : clouds) {
            cloud.getChildren().clear();
        }
        initializeClouds();
    }

    public void refreshHelperCards() {
        initializeHelperCards();
    }

    private void initializeHelperCards() {
        if(cache.getHelperCards() != null) {
            if(cache.getHelperCards().size() >= 1 && cache.getHelperCards().get(0) != null)
                helperCardImages.get(0).setImage(draw.getHelperCard(cache.getHelperCards().get(0)));
            else
                helperCardImages.get(0).setVisible(false);

            if(cache.getHelperCards().size() >= 2 && cache.getHelperCards().get(1) != null)
                helperCardImages.get(1).setImage(draw.getHelperCard(cache.getHelperCards().get(1)));
            else
                helperCardImages.get(1).setVisible(false);

            if(cache.getHelperCards().size() == 3 && cache.getHelperCards().get(2) != null)
                helperCardImages.get(2).setImage(draw.getHelperCard(cache.getHelperCards().get(2)));
            else
                helperCardImages.get(2).setVisible(false);
        }
    }

    /**
     * method to initialize the gameBoards using the data saved in cache
     */
    private void initializeGameBoards() {
        List<ReducedGameBoard> list = cache.getGameBoards();

        draw.populateEntrance(gameboard1.get(0), list.get(0));
        draw.populateDiningRoom(gameboard1.get(1), gameboard1.get(2), gameboard1.get(3),
                gameboard1.get(4), gameboard1.get(5), list.get(0));
        draw.populateProfessors(gameboard1.get(6), list.get(0));
        draw.populateTowers(gameboard1.get(7), list.get(0));
        if(list.get(0).coins() != null)
            labelsForCoins.get(0).setText("Coins: " + list.get(0).coins());

        draw.populateEntrance(gameboard2.get(0), list.get(1));
        draw.populateDiningRoom(gameboard2.get(1), gameboard2.get(2), gameboard2.get(3),
                gameboard2.get(4), gameboard2.get(5), list.get(1));
        draw.populateProfessors(gameboard2.get(6), list.get(1));
        draw.populateTowers(gameboard2.get(7), list.get(1));
        if(list.get(1).coins() != null)
            labelsForCoins.get(1).setText("Coins: " + list.get(1).coins());

        if(cache.getGameBoards().size() == 3) {
            draw.populateEntrance(gameboard3.get(0), list.get(2));
            draw.populateDiningRoom(gameboard3.get(1), gameboard3.get(2), gameboard3.get(3),
                    gameboard3.get(4), gameboard3.get(5), list.get(2));
            draw.populateProfessors(gameboard3.get(6), list.get(2));
            draw.populateTowers(gameboard3.get(7), list.get(2));
            if(list.get(2).coins() != null)
                labelsForCoins.get(2).setText("Coins: " + list.get(2).coins());
        }
        else {
            gameboards.getChildren().get(2).setVisible(false);
        }
    }

    /**
     * method used to initialize the islands using the data saved in cache
     */
    private void initializeArchipelago() {
        ReducedIsland reducedIsland;
        for(int i = 0; i < cache.getIslands().size(); i++) {
            reducedIsland = cache.getIslands().get(i);
            draw.populateIsland(islands.get(i), reducedIsland);
            if(reducedIsland.numOfIslands() > 1) {
                ((ImageView) islandsAP.get(i).getChildren().get(0))
                        .setImage(draw.getIslandImage(reducedIsland.numOfIslands()));
            }
        }
        for(int i = cache.getIslands().size(); i < 12; i++) {
            islandsAP.get(i).setVisible(false);
            islands.remove((GridPane) islandsAP.get(i).getChildren().get(1));
        }
    }

    /**
     * method used to initialize the clouds using the data saved in cache
     */
    private void initializeClouds() {
        draw.populateCloud(clouds.get(0), cache.getClouds().get(0));
        draw.populateCloud(clouds.get(1), cache.getClouds().get(1));
        if(cache.getClouds().size() == 3)
            draw.populateCloud(clouds.get(2), cache.getClouds().get(2));
        else {
            cloud3AP.setVisible(false);
            clouds.remove((GridPane)cloud3AP.getChildren().get(1));
        }
    }

    /**
     * method used to initialize each label in the game, excluding the coin's one
     */
    private void initializeLabel() {
        genericMsgAP.getChildren().get(0).setVisible(false);
        ((Label)genericMsgAP.getChildren().get(1)).setText("");
        player2Name.setText(cache.getGameBoards().get(1).playerNickname().toUpperCase());
        if(cache.getGameBoards().size() == 3)
            player3Name.setText(cache.getGameBoards().get(2).playerNickname().toUpperCase());
    }

    /**
     * method used to print a string into the GenericMsg label
     * @param stringToPrint the string we want to print
     */
    private void printGeneric(String stringToPrint) {
        ImageView image = (ImageView) genericMsgAP.getChildren().get(0);
        Label label = (Label) genericMsgAP.getChildren().get(1);
        if(!image.isVisible())
            image.setVisible(true);
        label.setText(stringToPrint);
        label.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * method to get which table corresponds to a particular PieceColor
     * @param pieceColor the color of the student clicked by the user
     * @return the table where said color should go
     */
    private GridPane getTable(PieceColor pieceColor) {
        switch (pieceColor) {
            case RED -> {
                return gameboard1.get(1);
            }
            case GREEN -> {
                return gameboard1.get(2);
            }
            case BLUE -> {
                return gameboard1.get(3);
            }
            case YELLOW -> {
                return gameboard1.get(4);
            }
            case PINK -> {
                return gameboard1.get(5);
            }
            default -> {
                return null;
            }
        }
    }
}
