package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controller for the main scene of the match
 * it allows to perform the action phase to the user
 */
public class MatchController implements SceneController {
    /** Image containing the sprite of the red student */
    private final Image redStudent = new Image("file:src/main/resources/Board/student_red.png");

    /** Image containing the sprite of the green student */
    private final Image greenStudent = new Image("file:src/main/resources/Board/student_green.png");

    /** Image containing the sprite of the blue student */
    private final Image blueStudent = new Image("file:src/main/resources/Board/student_blue.png");

    /** Image containing the sprite of the yellow student */
    private final Image yellowStudent = new Image("file:src/main/resources/Board/student_yellow.png");

    /** Image containing the sprite of the pink student */
    private final Image pinkStudent = new Image("file:src/main/resources/Board/student_pink.png");

    /** Image containing the sprite of the red professor */
    private final Image redProfessor = new Image("file:src/main/resources/Board/teacher_red.png");

    /** Image containing the sprite of the green professor */
    private final Image greenProfessor = new Image("file:src/main/resources/Board/teacher_green.png");

    /** Image containing the sprite of the blue professor */
    private final Image blueProfessor = new Image("file:src/main/resources/Board/teacher_blue.png");

    /** Image containing the sprite of the yellow professor */
    private final Image yellowProfessor = new Image("file:src/main/resources/Board/teacher_yellow.png");

    /** Image containing the sprite of the pink professor */
    private final Image pinkProfessor = new Image("file:src/main/resources/Board/teacher_pink.png");

    /** Image containing the sprite of mother nature */
    private final Image motherNatureImg = new Image("file:src/main/resources/Board/mother_nature.png");

    /** Image containing the sprite of a no-entry-tile */
    private final Image noEntryTileImg = new Image("file:src/main/resources/Board/noEntryTile.png");

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

    /** a reference to the gui class which "controls" this controller */
    private Gui gui;

    /** String containing the nickname of the player*/
    private String nickname;

    /** reference to the cache of this client*/
    private Cache cache;

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

    /** attribute used to save the student's color that the user wants to move */
    private PieceColor studentToMove;

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
        for(i = 0; i < 3; i++)
            gameboardsAP.add((AnchorPane) gameboards.getChildren().get(i));

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
     * method to show the user the generic messages
     * @param msg the GenericMessage sent by the server
     */
    @Override
    public void showGenericMsg(GenericMessage msg) {

    }

    /**
     * method that draws the scene using the parameters saved in cache
     */
    public void drawScene() {
        this.nickname = gui.getNickname();
        initializeGameBoards();
        initializeArchipelago();
        initializeClouds();
    }

    /**
     * method that sets up the view to let the user perform a move-student-sub-phase
     */
    public void moveStudentPhase() {
        gameboard1.get(0).setCursor(Cursor.HAND);
        gameboard1.get(0).setOnMouseClicked(this::pickStudentToMove);
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
    }

    /**
     * method that sets up the view to let the user perform a choose-cloud-sub-phase
     */
    public void chooseCloudPhase() {
        for(GridPane gp : islands) {
            gp.setOnMouseClicked(null);
            gp.setCursor(Cursor.DEFAULT);
        }
        for(GridPane gp : clouds) {
            gp.setOnMouseClicked(this::getChosenCloud);
            gp.setCursor(Cursor.HAND);
        }
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
                if(Objects.equals(GridPane.getRowIndex(node), rowIndex) && Objects.equals(GridPane.getColumnIndex(node), colIndex)) {
                    result = node;
                    break;
                }
            }

            if(result != null)
                studentToMove = ((StudentPiece) result).getColor();

            GridPane table = getTable(studentToMove);

            if(table != null) {
                table.setOnMouseClicked(this::getDestinationTable);
                table.setCursor(Cursor.HAND);

                for(GridPane gp : islands) {
                    gp.setOnMouseClicked(this::getDestinationIsland);
                    gp.setCursor(Cursor.HAND);
                }
            }
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
        GridPane clickedIsland = (GridPane) event.getPickResult().getIntersectedNode();
        int islandIndex;
        for(islandIndex = 0; islandIndex < islands.size(); islandIndex++) {
            if(islands.get(islandIndex) == clickedIsland)
                break;
        }

        gui.getMyClient().sendMessage(new ReplyEntranceToIslandMessage(nickname, islandIndex, studentToMove));
    }

    /**
     * method that calculates the number of steps the user wants to do with mother nature
     * it then sends a ReplyMotherNatureStepMessage to the server via the client
     * @param event the click of the user's mouse
     */
    public void getMotherNatureDestination(MouseEvent event) {
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
    }

    /**
     * method that sends the server a ReplyCloudMessage with the index of the cloud chosen by the player
     * @param event the click of the user's mouse
     */
    public void getChosenCloud(MouseEvent event) {
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
    }

    /**
     * method to initialize the gameBoards using the data saved in cache
     */
    private void initializeGameBoards() {
        List<ReducedGameBoard> list = cache.getGameBoards();

        populateEntrance(gameboard1.get(0), list.get(0));
        populateDiningRoom(gameboard1.get(1), gameboard1.get(2), gameboard1.get(3), gameboard1.get(4), gameboard1.get(5), list.get(0));
        populateProfessors(gameboard1.get(6), list.get(0));
        populateTowers(gameboard1.get(7), list.get(0));

        populateEntrance(gameboard2.get(0), list.get(1));
        populateDiningRoom(gameboard2.get(1), gameboard2.get(2), gameboard2.get(3), gameboard2.get(4), gameboard2.get(5), list.get(1));
        populateProfessors(gameboard2.get(6), list.get(1));
        populateTowers(gameboard2.get(7), list.get(1));

        if(cache.getGameBoards().size() == 3) {
            populateEntrance(gameboard3.get(0), list.get(2));
            populateDiningRoom(gameboard3.get(1), gameboard3.get(2), gameboard3.get(3), gameboard3.get(4), gameboard3.get(5), list.get(2));
            populateProfessors(gameboard2.get(6), list.get(2));
            populateTowers(gameboard2.get(7), list.get(2));
        }
        else {
            gameboards.getChildren().get(2).setVisible(false);
        }
    }

    /**
     * method used to initialize the islands using the data saved in cache
     */
    private void initializeArchipelago() {
        for(int i = 0; i < cache.getIslands().size(); i++) {
            populateIsland(islands.get(i), cache.getIslands().get(i));
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
        populateCloud(clouds.get(0), cache.getClouds().get(0));
        populateCloud(clouds.get(1), cache.getClouds().get(1));
        if(cache.getClouds().size() == 3) {
            populateCloud(clouds.get(2), cache.getClouds().get(2));
        }
        else {
            cloud3AP.setVisible(false);
            clouds.remove((GridPane)cloud3AP.getChildren().get(1));
        }
    }

    /**
     * method used to put player's student in their correct space in the gameBoard's entrance
     * @param entrance the GridPane where we want to draw the students on
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    private void populateEntrance(GridPane entrance, ReducedGameBoard gameBoard) {
        int i = 0;
        int j = 0;
        for(PieceColor color : PieceColor.values()) {
            for(int k = 0; k < gameBoard.entrance().get(color); k++) {
                entrance.add(createStudent(color), i, j);
                i++;
                if(i == 2) {
                    i = 0;
                    j++;
                }
            }
        }
    }

    /**
     * method used to put player's student in their correct space in the gameboard
     * @param redTable the GridPane of the table containing red students
     * @param greenTable the GridPane of the table containing green students
     * @param blueTable the GridPane of the table containing blue students
     * @param yellowTable the GridPane of the table containing yellow students
     * @param pinkTable the GridPane of the table containing pink students
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    private void populateDiningRoom(GridPane redTable, GridPane greenTable, GridPane blueTable, GridPane yellowTable, GridPane pinkTable,ReducedGameBoard gameBoard) {
        //red table
        for(int r = 0; r < gameBoard.diningRoom().get(PieceColor.RED); r++) {
            redTable.add(createStudent(PieceColor.RED), r, 0);
        }
        //green table
        for(int g = 0; g < gameBoard.diningRoom().get(PieceColor.GREEN); g++) {
            greenTable.add(createStudent(PieceColor.GREEN), g, 0);
        }
        //blue table
        for(int b = 0; b < gameBoard.diningRoom().get(PieceColor.BLUE); b++) {
            blueTable.add(createStudent(PieceColor.BLUE), b, 0);
        }
        //yellow table
        for(int y = 0; y < gameBoard.diningRoom().get(PieceColor.YELLOW); y++) {
            yellowTable.add(createStudent(PieceColor.YELLOW), y, 0);
        }
        //pink table
        for(int p = 0; p < gameBoard.diningRoom().get(PieceColor.PINK); p++) {
            pinkTable.add(createStudent(PieceColor.PINK), p, 0);
        }
    }

    /**
     * method used to put player's professors in their space in the gameboard
     * @param professorsTable the GridPane where we want to draw professors in
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    private void populateProfessors(GridPane professorsTable, ReducedGameBoard gameBoard) {
        for(int i = 0; i < gameBoard.professors().size(); i++) {
            switch (gameBoard.professors().get(i)) {
                case RED -> professorsTable.add(createProfessor(PieceColor.RED), 0, 1);
                case GREEN -> professorsTable.add(createProfessor(PieceColor.GREEN), 0, 0);
                case BLUE -> professorsTable.add(createProfessor(PieceColor.BLUE), 0, 4);
                case YELLOW -> professorsTable.add(createProfessor(PieceColor.YELLOW), 0, 2);
                case PINK -> professorsTable.add(createProfessor(PieceColor.PINK), 0, 3);
            }
        }
    }

    /**
     * method used to put player's towers in their space in the gameboard
     * @param towersField the GridPane where we want to draw the towers in
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    private void populateTowers(GridPane towersField, ReducedGameBoard gameBoard) {
        int i = 0;
        int j = 0;
        TowerColor color = gameBoard.TowerColor();
        for(int k = 0; k < gameBoard.numOfTowers(); k++) {
            towersField.add(createTower(color), i, j);
            i++;
            if(i == 2) {
                i = 0;
                j++;
            }
        }
    }

    /**
     * method used to put student on the cloud
     * @param cloud the GridPane of the cloud we want to draw students on
     * @param cloudMap the cloud saved in cache corresponding to the gui's GridPane
     */
    private void populateCloud(GridPane cloud, Map<PieceColor, Integer> cloudMap) {
        int i = 0;
        int j = 0;
        for(PieceColor color : cloudMap.keySet()) {
            for(int k = 0; k < cloudMap.get(color); k++) {
                cloud.add(createStudent(color), i, j);
                i++;
                if(i == 2) {
                    i = 0;
                    j++;
                }
            }
        }
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
                        island.add(createStudent(PieceColor.RED), 0, 0);
                        island.add(new Label("x" + num), 1, 0);
                    }
                    case GREEN -> {
                        island.add(createStudent(PieceColor.GREEN), 2, 0);
                        island.add(new Label("x" + num), 3, 0);
                    }
                    case BLUE -> {
                        island.add(createStudent(PieceColor.BLUE), 0, 1);
                        island.add(new Label("x" + num), 1, 1);
                    }
                    case YELLOW -> {
                        island.add(createStudent(PieceColor.YELLOW), 2, 1);
                        island.add(new Label("x" + num), 3, 1);
                    }
                    case PINK -> {
                        island.add(createStudent(PieceColor.PINK), 0, 2);
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
     * method to create a circle of radius 10 with the sprite of a student on it
     * @param pieceColor the color of the student
     * @return a decorated circle
     */
    private Circle createStudent(PieceColor pieceColor) {
        StudentPiece student = new StudentPiece(10); //create a circle of radius 10
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
     * method to create a circle of radius 15 with the sprite of a professor on it
     * @param pieceColor the color of the professor
     * @return a decorated circle
     */
    private Circle createProfessor(PieceColor pieceColor) {
        Circle professor = new Circle(15); //create a circle of radius 15
        switch (pieceColor) {
            case RED -> professor.setFill(new ImagePattern(redProfessor));
            case GREEN -> professor.setFill(new ImagePattern(greenProfessor));
            case BLUE -> professor.setFill(new ImagePattern(blueProfessor));
            case YELLOW -> professor.setFill(new ImagePattern(yellowProfessor));
            case PINK -> professor.setFill(new ImagePattern(pinkProfessor));
        }
        return professor;
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
