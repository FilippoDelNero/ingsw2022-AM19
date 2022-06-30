package it.polimi.ingsw.am19.Model.BoardManagement;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Utilities.Observable;
import it.polimi.ingsw.am19.Utilities.Notification;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class models an island or a group of them
 */
public class Island extends Observable implements MoveStudent, Serializable {

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
     * @param strategy: the island is created with a standard strategy
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
     * @param strategy: the island is created with a strategy either default or NoEntryTile
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

    /**
     * setter for the towerColor attribute
     * @param towerColor the color of the tower you want on this island
     */
    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    /**
     * getter for the towerColor attribute
     * @return the color of the tower present on the island
     */
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
        notifyObservers(Notification.UPDATE_ISLANDS);
        notifyObservers(Notification.UPDATE_GAMEBOARDS);
    }

    @Override
    public void removeStudent(PieceColor color) {
        //not supported on an island at the moment
    }

    /**
     * getter for numOfStudents attribute
     * @return a map containing all the student present on the island
     */
    public Map<PieceColor, Integer> getNumOfStudents() {
        return Map.copyOf(numOfStudents);
    }

    /**
     * calculate the influence of each player on this island according to the strategy set
     * @return true if there where updates to make, false otherwise
     */
    public boolean calculateInfluence(ProfessorManager manager){
        Player newOwner;
        newOwner = influenceStrategy.calculateInfluence(numOfStudents, towerColor, numOfIslands, manager);
        //if the ownership is not changed return false
        if(newOwner == null)
            return false;
        //if the owner has changed, change it
        this.towerColor = newOwner.getTowerColor();

        GameBoard gameBoard = manager.getGameboards().get(newOwner);

        for (int i = 0; i < numOfIslands; i++){
            if (gameBoard.areTowersFinished()){
                return true;
            }
            gameBoard.removeTower();
        }
        return true;
    }

    /**
     * setter for the influenceStrategy attribute
     * @param strategy the influence strategy of the played card
     */
    public void setInfluenceStrategy(InfluenceStrategy strategy) {
        this.influenceStrategy = strategy;
    }

    /**
     * getter for the influenceStrategy attribute
     * @return the current influence strategy
     */
    public InfluenceStrategy getInfluenceStrategy() {
        return this.influenceStrategy;
    }

    /**
     * getter for presenceOfMotherNature attribute
     * @return true if motherNature is placed on this island, false otherwise
     */
    public boolean isMotherNaturePresent() {
        return presenceOfMotherNature;
    }

    /**
     * setter for the presenceOfMotherNature attribute
     * @param presenceOfMotherNature will be set to false if MN is leaving the island, true if she's landing on it
     */
    public void setPresenceOfMotherNature(boolean presenceOfMotherNature) {
        this.presenceOfMotherNature = presenceOfMotherNature;
    }

    /**
     * getter for the numOfIslands attribute
     * @return the number of islands that makes up this group
     */
    public int getNumOfIslands() {
        return numOfIslands;
    }

    /**
     * get the total number of student present on the island
     * @return the sum of students of each color present on the island
     */
    public int getTotStudents(){
        int tot = 0;
        for (PieceColor color: numOfStudents.keySet()){
            tot += numOfStudents.get(color);
        }
        return tot;
    }
}