package it.polimi.ingsw.am19.View.GUI;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Network.ReducedObjects.ReducedIsland;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Map;

public class Drawer {

    private final static Image redStudent = new Image("file:src/main/resources/Board/student_red.png");
    private final static Image greenStudent = new Image("file:src/main/resources/Board/student_green.png");
    private final static Image blueStudent = new Image("file:src/main/resources/Board/student_blue.png");
    private final static Image yellowStudent = new Image("file:src/main/resources/Board/student_yellow.png");
    private final static Image pinkStudent = new Image("file:src/main/resources/Board/student_pink.png");
    private final Image redProfessor = new Image("file:src/main/resources/Board/teacher_red.png");
    private final Image greenProfessor = new Image("file:src/main/resources/Board/teacher_green.png");
    private final Image blueProfessor = new Image("file:src/main/resources/Board/teacher_blue.png");
    private final Image yellowProfessor = new Image("file:src/main/resources/Board/teacher_yellow.png");
    private final Image pinkProfessor = new Image("file:src/main/resources/Board/teacher_pink.png");
    private final Image motherNatureImg = new Image("file:src/main/resources/Board/mother_nature.png");
    private final Image noEntryTileImg = new Image("file:src/main/resources/Board/noEntryTile.png");


    public String getCharacterImagePath(Character c){
        return "file:src/main/resources/it/polimi/ingsw/am19.View.GUI/CharacterCard/" + c + ".jpg";
    }

    public String getCharacterDescription(AbstractCharacterCard c){
        return "Price: " + c.getPrice() + "\n" + c.getDescription();
    }

    public void setStudentOnCard(AbstractCharacterCard card, GridPane onCardGrid){
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

    public Circle createStudent(PieceColor pieceColor, int radius) {
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

    public Image getStudentPath(PieceColor color){
        return switch (color){
            case BLUE -> blueStudent;
            case PINK -> pinkStudent;
            case RED -> redStudent;
            case YELLOW -> yellowStudent;
            case GREEN -> greenStudent;
        };
    }

    /**
     * method used to put student on the island
     * @param island the GridPane of the island we want to draw students on
     * @param reducedIsland the island saved in cache corresponding to the gui's GridPane
     */
    public void populateIsland(GridPane island, ReducedIsland reducedIsland) {
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
    public Circle createTower(TowerColor towerColor) {
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

    public PieceColor getColor (String s){
        return switch (s){
            case "GREEN"-> PieceColor.GREEN;
            case "BLUE"-> PieceColor.BLUE;
            case "PINK"->PieceColor.PINK;
            case "RED"-> PieceColor.RED;
            case "YELLOW"-> PieceColor.YELLOW;
            default -> null;
        };
    }

    /**
     * method used to put player's student in their correct space in the gameBoard's entrance
     * @param entrance the GridPane where we want to draw the students on
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    public void populateEntrance(GridPane entrance, ReducedGameBoard gameBoard) {
        int i = 0;
        int j = 0;
        for(PieceColor color : PieceColor.values()) {
            for(int k = 0; k < gameBoard.entrance().get(color); k++) {
                entrance.add(createStudent(color,10), i, j);
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
    public void populateDiningRoom(GridPane redTable, GridPane greenTable, GridPane blueTable, GridPane yellowTable, GridPane pinkTable,ReducedGameBoard gameBoard) {
        //red table
        for(int r = 0; r < gameBoard.diningRoom().get(PieceColor.RED); r++) {
            redTable.add(createStudent(PieceColor.RED,10), r, 0);
        }
        //green table
        for(int g = 0; g < gameBoard.diningRoom().get(PieceColor.GREEN); g++) {
            greenTable.add(createStudent(PieceColor.GREEN,10), g, 0);
        }
        //blue table
        for(int b = 0; b < gameBoard.diningRoom().get(PieceColor.BLUE); b++) {
            blueTable.add(createStudent(PieceColor.BLUE,10), b, 0);
        }
        //yellow table
        for(int y = 0; y < gameBoard.diningRoom().get(PieceColor.YELLOW); y++) {
            yellowTable.add(createStudent(PieceColor.YELLOW,10), y, 0);
        }
        //pink table
        for(int p = 0; p < gameBoard.diningRoom().get(PieceColor.PINK); p++) {
            pinkTable.add(createStudent(PieceColor.PINK,10), p, 0);
        }
    }

    /**
     * method to create a circle of radius 15 with the sprite of a professor on it
     * @param pieceColor the color of the professor
     * @return a decorated circle
     */
    public Circle createProfessor(PieceColor pieceColor) {
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
     * method used to put player's professors in their space in the gameboard
     * @param professorsTable the GridPane where we want to draw professors in
     * @param gameBoard the gameboard saved in cache corresponding to the one we are drawing on
     */
    public void populateProfessors(GridPane professorsTable, ReducedGameBoard gameBoard) {
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
    public void populateTowers(GridPane towersField, ReducedGameBoard gameBoard) {
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

}
