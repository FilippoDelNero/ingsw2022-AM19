package it.polimi.ingsw.am19.Model;
import java.util.HashMap;
import java.util.Map;

/**
 * This class models an island or a group of them, it stores the number of students present, the tower's color if any and the number of island that makes the group. It calcute the influence of a given player in the island
 */
public class Island implements InfluenceStrategy, MoveStudent {

    /**
     * contains the number of students for each colour present on a island
     */
    private final Map<PieceColor, Integer> numOfStudents;

    /**
     * indicates the color of the tower, is null if no tower is present
     */
    private TowerColor towerColor;

    /**
     * indicates the presence or lack thereof of mother nature
     */
    private boolean presenceOfMotherNature;

    /**
     * stores which strategy is currently set
     */
    private InfluenceStrategy influenceStrategy;

    /**
     * keep tracks of the number of island absorbed in a single entity
     */
    private final int numOfIslands;

    /**
     * Builds a single island with no students and no mother nature
     */
    public Island() {
        numOfStudents = new HashMap<>();
        //initialize each color to zero
        for(PieceColor color: PieceColor.values())
            numOfStudents.put(color, 0);

        presenceOfMotherNature = false;
        numOfIslands = 1;
    }



    /**
     * Builds a group of island with a set number of students, a tower's color, a value for the presenceMotherNature and a number of islands that compose the group
     * @param map: number of students present on the island
     * @param towerColor: the tower's color
     * @param presence: presence or lack thereOf of Mother Nature
     * @param numOfIslands: the number of Island that makes up a group
     */
    public Island(Map<PieceColor,Integer> map, TowerColor towerColor, boolean presence, int numOfIslands) {
        numOfStudents = new HashMap<>();
        this.numOfStudents.putAll(map);
        this.towerColor = towerColor;
        this.presenceOfMotherNature = presence;
        this.numOfIslands = numOfIslands;
    }

    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * add a student to the island of the specified color, that is it increments by one the value associated to the passed color key
     * @param color the color of the students you want to add to the island
     */
    @Override
    public void addStudent(PieceColor color) {
        int oldValue = numOfStudents.get(color);
        numOfStudents.replace(color, oldValue + 1);
    }

    @Override
    public void removeStudent(PieceColor color) {
        //not supported on an island at the moment
    }

    public Map<PieceColor, Integer> getNumOfStudent() {
        return Map.copyOf(numOfStudents);
    }

    /**
     * calculate the influence on island of the playing player according to the strategy set
     */
    @Override
    public void calculateInfluence() {
        influenceStrategy.calculateInfluence();
    }
    // the following method is commented out due to the absence of any strategy
    // public void setInfluenceStrategy(InfluenceStrategy strategy) {
    //     this.influeceStrategy = strategy;
    // }

    public boolean isMotherNaturePresent() {
        return presenceOfMotherNature;
    }

    public void setPresenceOfMotherNature(boolean presenceOfMotherNature) {
        this.presenceOfMotherNature = presenceOfMotherNature;
    }

    public int getNumOfIslands() {
        return numOfIslands;
    }

}