package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Network.Client.Cache;
import it.polimi.ingsw.am19.Network.Message.ReplyEntranceToDiningRoomMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyEntranceToIslandMessage;
import it.polimi.ingsw.am19.Network.Message.ReplyMotherNatureStepMessage;
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

public class MatchController implements SceneController {
    private final static Image redStudent = new Image("file:src/main/resources/Board/student_red.png");
    private final static Image greenStudent = new Image("file:src/main/resources/Board/student_green.png");
    private final static Image blueStudent = new Image("file:src/main/resources/Board/student_blue.png");
    private final static Image yellowStudent = new Image("file:src/main/resources/Board/student_yellow.png");
    private final static Image pinkStudent = new Image("file:src/main/resources/Board/student_pink.png");

    private final static Image redProfessor = new Image("file:src/main/resources/Board/teacher_red.png");
    private final static Image greenProfessor = new Image("file:src/main/resources/Board/teacher_green.png");
    private final static Image blueProfessor = new Image("file:src/main/resources/Board/teacher_blue.png");
    private final static Image yellowProfessor = new Image("file:src/main/resources/Board/teacher_yellow.png");
    private final static Image pinkProfessor = new Image("file:src/main/resources/Board/teacher_pink.png");

    private final static Image motherNatureImg = new Image("file:src/main/resources/Board/mother_nature.png");

    @FXML private Group gameboards;

    @FXML private Group archipelago;

    @FXML private AnchorPane cloud1AP;
    @FXML private AnchorPane cloud2AP;
    @FXML private AnchorPane cloud3AP;

    private Gui gui;

    private String nickname;

    private Cache cache;

    private List<AnchorPane> islandsAP;

    private List<GridPane> islands;

    private List<GridPane> gameboard1;

    private List<GridPane> gameboard2;

    private List<GridPane> gameboard3;

    private List<GridPane> clouds;

    private PieceColor studentToMove;

    public void initialize(){
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

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public void drawScene() {
        this.nickname = gui.getNickname();
        initializeGameBoards();
        initializeClouds();
        initializeArchipelago();
    }

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

    private void initializeClouds() {
        populateCloud(clouds.get(0), cache.getClouds().get(0));
        populateCloud(clouds.get(1), cache.getClouds().get(1));
        if(cache.getClouds().size() == 3) {
            populateCloud(clouds.get(2), cache.getClouds().get(2));
        }
        else {
            cloud3AP.setVisible(false);
        }
    }

    private void initializeArchipelago() {
        for(int i = 0; i < cache.getIslands().size(); i++) {
            populateIsland(islands.get(i), cache.getIslands().get(i));
        }
        for(int i = cache.getIslands().size(); i < 12; i++) {
            islandsAP.get(i).setVisible(false);
        }
    }

    public void moveStudentPhase() {
        gameboard1.get(0).setCursor(Cursor.HAND);
        gameboard1.get(0).setOnMouseClicked(this::pickStudentToMove);
    }

    public void moveMotherNaturePhase() {
        for(GridPane gp : islands) {
            gp.setOnMouseClicked(this::getMotherNatureDestination);
            gp.setCursor(Cursor.HAND);
        }
    }

    public void pickStudentToMove(MouseEvent event) {
        Node clickedStudent = event.getPickResult().getIntersectedNode();
        if (clickedStudent != gameboard1.get(0)) {
            // click on descendant node
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
                gameboard1.get(0).setCursor(Cursor.DEFAULT);
                gameboard1.get(0).setOnMouseClicked(null);
                table.setOnMouseClicked(this::getDestinationTable);
                table.setCursor(Cursor.HAND);
                for(GridPane gp : islands) {
                    gp.setOnMouseClicked(this::getDestinationIsland);
                    gp.setCursor(Cursor.HAND);
                }
            }

            System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
            System.out.println("color: " + studentToMove);
        }
    }

    public void getDestinationTable(MouseEvent event) {
        GridPane clickedTable = (GridPane) event.getPickResult().getIntersectedNode();
        gui.getMyClient().sendMessage(new ReplyEntranceToDiningRoomMessage(nickname, studentToMove));
        clickedTable.setCursor(Cursor.DEFAULT);
        clickedTable.setOnMouseClicked(null);
        System.out.println("eccomi hai scelto un tavolo");
    }

    public void getDestinationIsland(MouseEvent event) {
        GridPane clickedIsland = (GridPane) event.getPickResult().getIntersectedNode();
        int islandIndex;
        for(islandIndex = 0; islandIndex < islands.size(); islandIndex++) {
            if(islands.get(islandIndex) == clickedIsland)
                break;
        }
        gui.getMyClient().sendMessage(new ReplyEntranceToIslandMessage(nickname, islandIndex, studentToMove));
        islands.get(islandIndex).setCursor(Cursor.DEFAULT);
        islands.get(islandIndex).setOnMouseClicked(null);
        System.out.println("eccomi hai scelto un'isola");
    }

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
            MNDestIndex = (MNDepIndex - step)%numOfIslands;
            if(islands.get(MNDestIndex) == clickedIsland)
                break;
        }
        gui.getMyClient().sendMessage(new ReplyMotherNatureStepMessage(nickname, step));
        System.out.println("muovo madre natura di " + step + " steps");
    }

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

    private void populateTowers(GridPane towersField, ReducedGameBoard gameBoard) {
        int i = 0;
        int j = 0;
        Color towerColor;
        Color strokeColor;
        if(gameBoard.TowerColor() == TowerColor.BLACK) {
            towerColor = Color.BLACK;
            strokeColor = Color.DARKGRAY;
        }
        else if(gameBoard.TowerColor() == TowerColor.WHITE) {
            towerColor = Color.WHITE;
            strokeColor = Color.LIGHTGRAY;
        }
        else {
            towerColor = Color.GREY;
            strokeColor = Color.DARKSLATEGREY;
        }
        for(int k = 0; k < gameBoard.numOfTowers(); k++) {
            Circle tower = new Circle(10, towerColor);
            tower.setStroke(strokeColor);
            towersField.add(tower, i, j);
            i++;
            if(i == 2) {
                i = 0;
                j++;
            }
        }
    }

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
    }

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

    private Circle createProfessor(PieceColor pieceColor) {
        Circle professor = new Circle(15); //create a circle of radius 20
        switch (pieceColor) {
            case RED -> professor.setFill(new ImagePattern(redProfessor));
            case GREEN -> professor.setFill(new ImagePattern(greenProfessor));
            case BLUE -> professor.setFill(new ImagePattern(blueProfessor));
            case YELLOW -> professor.setFill(new ImagePattern(yellowProfessor));
            case PINK -> professor.setFill(new ImagePattern(pinkProfessor));
        }
        return professor;
    }

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
