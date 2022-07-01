package it.polimi.ingsw.am19.View.GUI.Utilities;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Utilities.ReducedObjects.ReducedGameBoard;
import it.polimi.ingsw.am19.Utilities.ReducedObjects.ReducedIsland;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Map;

/**
 * class that allows to draw on the scene different type of objects used in multiple scenes
 */
public class Drawer {
    //--Students--
    /** Image containing the sprite of the red student */
    private final Image redStudent = new Image(getClass().getResource("/Board/student_red.png").toExternalForm());

    /** Image containing the sprite of the green student */
    private final Image greenStudent = new Image(getClass().getResource("/Board/student_green.png").toExternalForm());

    /** Image containing the sprite of the blue student */
    private final Image blueStudent = new Image(getClass().getResource("/Board/student_blue.png").toExternalForm());

    /** Image containing the sprite of the yellow student */
    private final Image yellowStudent = new Image(getClass().getResource("/Board/student_yellow.png").toExternalForm());

    /** Image containing the sprite of the pink student */
    private final Image pinkStudent = new Image(getClass().getResource("/Board/student_pink.png").toExternalForm());

    //--Professors--
    /** Image containing the sprite of the red professor */
    private final Image redProfessor = new Image(getClass().getResource("/Board/teacher_red.png").toExternalForm());

    /** Image containing the sprite of the green professor */
    private final Image greenProfessor = new Image(getClass().getResource("/Board/teacher_green.png").toExternalForm());

    /** Image containing the sprite of the blue professor */
    private final Image blueProfessor = new Image(getClass().getResource("/Board/teacher_blue.png").toExternalForm());

    /** Image containing the sprite of the yellow professor */
    private final Image yellowProfessor = new Image(getClass().getResource("/Board/teacher_yellow.png").toExternalForm());

    /** Image containing the sprite of the pink professor */
    private final Image pinkProfessor = new Image(getClass().getResource("/Board/teacher_pink.png").toExternalForm());

    //--Various--
    /** Image containing the sprite of mother nature */
    private final Image motherNatureImg = new Image(getClass().getResource("/Board/mother_nature.png").toExternalForm());

    /** Image containing the sprite of a no-entry-tile */
    private final Image noEntryTileImg = new Image(getClass().getResource("/Board/noEntryTile.png").toExternalForm());

    //--Islands--
    /** Image containing the sprite of a single type 2 island */
    private final Image singleIslandType2 = new Image(getClass().getResource("/Board/island2.png").toExternalForm());

    /** Image containing the sprite of group of two islands */
    private final Image islandGroup2 = new Image(getClass().getResource("/Board/islandGroup2.png").toExternalForm());

    /** Image containing the sprite of group of three islands */
    private final Image islandGroup3 = new Image(getClass().getResource("/Board/islandGroup3.png").toExternalForm());

    /** Image containing the sprite of group of four islands */
    private final Image islandGroup4 = new Image(getClass().getResource("/Board/islandGroup4.png").toExternalForm());

    /** Image containing the sprite of group of five islands */
    private final Image islandGroup5 = new Image(getClass().getResource("/Board/islandGroup5.png").toExternalForm());

    /** Image containing the sprite of group of six islands */
    private final Image islandGroup6 = new Image(getClass().getResource("/Board/islandGroup6.png").toExternalForm());

    /** Image containing the sprite of group of seven islands */
    private final Image islandGroup7 = new Image(getClass().getResource("/Board/islandGroup7.png").toExternalForm());

    /** Image containing the sprite of group of eight islands */
    private final Image islandGroup8 = new Image(getClass().getResource("/Board/islandGroup8.png").toExternalForm());

    //--HelperCards--
    /** Image of the first helper card */
    private final Image helperCard1 = new Image(getClass().getResource("/HelperCard/HelperCard1.png").toExternalForm());

    /** Image of the second helper card */
    private final Image helperCard2 = new Image(getClass().getResource("/HelperCard/HelperCard2.png").toExternalForm());

    /** Image of the third helper card */
    private final Image helperCard3 = new Image(getClass().getResource("/HelperCard/HelperCard3.png").toExternalForm());

    /** Image of the fourth helper card */
    private final Image helperCard4 = new Image(getClass().getResource("/HelperCard/HelperCard4.png").toExternalForm());

    /** Image of the five helper card */
    private final Image helperCard5 = new Image(getClass().getResource("/HelperCard/HelperCard5.png").toExternalForm());

    /** Image of the six helper card */
    private final Image helperCard6 = new Image(getClass().getResource("/HelperCard/HelperCard6.png").toExternalForm());

    /** Image of the seven helper card */
    private final Image helperCard7 = new Image(getClass().getResource("/HelperCard/HelperCard7.png").toExternalForm());

    /** Image of the eight helper card */
    private final Image helperCard8 = new Image(getClass().getResource("/HelperCard/HelperCard8.png").toExternalForm());

    /** Image of the nine helper card */
    private final Image helperCard9 = new Image(getClass().getResource("/HelperCard/HelperCard9.png").toExternalForm());

    /** Image of the tenth helper card */
    private final Image helperCard10 = new Image(getClass().getResource("/HelperCard/HelperCard10.png").toExternalForm());

    /**
     * Method to get the path of the image of a CharacterCard
     * @param c the character enum value
     * @return the path of the image of the CharacterCard corresponding to c
     *
     */
    public String getCharacterImagePath(Character c){
        return "/CharacterCard/" + c + ".jpg";
    }

    /**
     * Method to get the price and description of a CharacterCard
     * @param c the CharacterCard
     * @return a string with the price and description
     */
    public String getCharacterDescription(AbstractCharacterCard c){
        return "Price: " + c.getPrice() + "\n" + c.getDescription();
    }

    /**
     * method that, given an helper card returns the corresponding image
     * @param hc the helper card
     * @return the image showing the helper card passed as a parameter
     */
    public Image getHelperCard(HelperCard hc) {
        return switch(hc.getNextRoundOrder()) {
            case 1 -> helperCard1;
            case 2 -> helperCard2;
            case 3 -> helperCard3;
            case 4 -> helperCard4;
            case 5 -> helperCard5;
            case 6 -> helperCard6;
            case 7 -> helperCard7;
            case 8 -> helperCard8;
            case 9 -> helperCard9;
            default -> helperCard10;
        };
    }

    /**
     * Method to populate a CharacterCard with students
     * @param card the CharacterCard
     * @param onCardGrid the GridPane where the students will be put on
     */
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

    /**
     * method used to put student on the cloud
     * @param cloud the GridPane of the cloud we want to draw students on
     * @param cloudMap the cloud saved in cache corresponding to the gui's GridPane
     */
    public void populateCloud(GridPane cloud, Map<PieceColor, Integer> cloudMap) {
        int i = 0;
        int j = 0;
        for(PieceColor color : cloudMap.keySet()) {
            for(int k = 0; k < cloudMap.get(color); k++) {
                cloud.add(createStudent(color, 10), i, j);
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

    /**
     * method to create a circle of radius 10 with the sprite of a student on it
     * @param pieceColor the color of the student
     * @param radius the radius of the circle of the student
     * @return a decorated circle
     */
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

    /**
     * Method to get a PieceColor starting from a string
     * @param s the string supposedly containing the color
     * @return the PieceColor
     */
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
     * method to get a path of student's PieceColor from its PieceColor
     * @param color the PieceColor of the student
     * @return the image of the correct sprite
     */
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
     * Method to get the correct image for the number of island composing a group
     * @param numOfIsland the number of island of said group
     * @return the image of numOfIsland number of island
     */
    public Image getIslandImage(int numOfIsland) {
        return switch (numOfIsland) {
            case 2 -> islandGroup2;
            case 3 -> islandGroup3;
            case 4 -> islandGroup4;
            case 5 -> islandGroup5;
            case 6 -> islandGroup6;
            case 7 -> islandGroup7;
            case 8 -> islandGroup8;
            default -> singleIslandType2;
        };
    }

}
