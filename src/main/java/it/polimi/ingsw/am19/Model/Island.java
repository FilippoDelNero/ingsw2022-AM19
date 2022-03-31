package it.polimi.ingsw.am19.Model;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import java.util.HashMap;
import java.util.Map;

/**
 * This class models an island or a group of them
 */
public class Island implements MoveStudent {

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
     * @param strategy the island is created with a standard strategy
     */
    public Island(InfluenceStrategy strategy) {
        numOfStudents = new HashMap<>();
        //initialize each color to zero
        for(PieceColor color: PieceColor.values())
            numOfStudents.put(color, 0);
        influenceStrategy = strategy;
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
    public Island(Map<PieceColor,Integer> map, TowerColor towerColor, boolean presence, InfluenceStrategy strategy, int numOfIslands) {
        numOfStudents = new HashMap<>();
        this.numOfStudents.putAll(map);
        this.towerColor = towerColor;
        this.presenceOfMotherNature = presence;
        influenceStrategy = strategy;
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
     * calculate the influence of each player on this island according to the strategy set
     */
    public void calculateInfluence(ProfessorManager manager) {
        influenceStrategy.calculateInfluence(numOfStudents, towerColor, numOfIslands, manager);
    }

    public void setInfluenceStrategy(InfluenceStrategy strategy) {
        this.influenceStrategy = strategy;
    }

    public InfluenceStrategy getInfluenceStrategy() {
        return this.influenceStrategy;
    }

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