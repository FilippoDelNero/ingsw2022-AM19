package it.polimi.ingsw.am19.Model;

import it.polimi.ingsw.am19.Model.Exceptions.IllegalIslandException;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalNumOfStepsException;
import it.polimi.ingsw.am19.Model.MovementStrategies.MovementStrategy;
import it.polimi.ingsw.am19.Model.MovementStrategies.StandardMovement;

import java.util.List;
import java.util.ListIterator;

/**
 * Singleton class that models mother nature tile, it can visit
 */
public class MotherNature {
    /**
     * Keeps a reference to the an island manager
     */
    private IslandManager islandManager;

    /**
     * Keeps a reference to the unique MotherNature instance
     */
    private static MotherNature instance;

    /**
     * Maintains a reference to the Island where MotherNature currently is
     */
    private Island currPosition;

    /**
     * Keeps the default algorithm used by MotherNature for moving on
     */
    private final MovementStrategy defaultMovement;

    /**
     * Keeps trace of the current algorithm used by MotherNature for moving on
     */
    private MovementStrategy currMovementStrategy;

    /**
     *
     */
    //TODO per via del meccanismo di istanziazione previsto per i singleton, non posso passare parametri al costruttore, devo ricordami di richiamare il setter di islandManager e di position in Match
    private MotherNature() {
        this.defaultMovement = new StandardMovement();
        this.currMovementStrategy = this.defaultMovement;
    }

    /**
     * Creates the MotherNature class instance if missing and returns it
     */
    public static MotherNature getInstance() {
        if (MotherNature.instance == null)
            MotherNature.instance = new MotherNature();
        return MotherNature.instance;
    }

    /**
     * Saves a reference to an IslandManager
     * @param islandManager a reference to an IslandManager
     * @throws IllegalArgumentException when passing an invalid islandManager
     */
    public void setIslandManager(IslandManager islandManager) throws IllegalArgumentException {
        if (islandManager == null)
            throw new IllegalArgumentException();
        this.islandManager = islandManager;
    }

    /**
     * Returns the current of position of MotherNature
     * @return the current of position of MotherNature
     */
    public Island getCurrPosition() {
        return currPosition;
    }

    /**
     * Returns the current IslandManager
     * @return an IslandManager
     */
    public IslandManager getIslandManager() {
        return islandManager;
    }

    /**
     * Updates MotherNature's position
     * @param position represents the Island now hosting MotherNature
     * @throws IllegalArgumentException when an Invalid position is passed
     * @throws IllegalIslandException when an Island that does not take part into the archipelago is passed
     */
    public void setCurrPosition(Island position) throws IllegalIslandException, IllegalArgumentException {
        boolean existsIsland = false;
        List<Island> islands = islandManager.getIslands();
        ListIterator<Island> iterator = islandManager.getIterator();

        for (int i=0; i< islands.size(); i++){
            if (iterator.next() == position)
                existsIsland = true;
        }

        if (!existsIsland)
            throw new IllegalIslandException("Trying to put MotherNature on an Island that does not take part into the archipelago");

        if (position == null){
            throw new IllegalArgumentException();}

        position.setPresenceOfMotherNature(true);
        this.currPosition = position;
    }

    /**
     * Updates the current MovementStrategy
     * @param movementStrategy represents a MovementStrategy
     * @throws IllegalArgumentException when passing an invalid MovementStrategy
     */
    public void setCurrMovementStrategy(MovementStrategy movementStrategy) throws IllegalArgumentException{
        if (movementStrategy == null)
            throw new IllegalArgumentException();
        this.currMovementStrategy = movementStrategy;
    }

    /**
     * Makes MotherNature move on Islands
     * @param numOfSteps represents the number of steps that MotherNature has to do to reach a final destination
     * @throws IllegalNumOfStepsException when passing an illegal number of steps
     */
    public void move(int numOfSteps) throws IllegalNumOfStepsException {
        if (numOfSteps == 0)
            throw new IllegalNumOfStepsException("Trying to make MotherNature move an illegal number of steps. Number of steps passed:" + numOfSteps, numOfSteps);

        if (numOfSteps < 0)
            throw new IllegalNumOfStepsException("Trying to make MotherNature nature move an illegal number of steps. Number of steps passed:" + numOfSteps, numOfSteps);

        ListIterator<Island> islandsIterator = islandManager.getIterator();
        Island finalPosition = currMovementStrategy.move(numOfSteps, this.currPosition, islandsIterator);
        this.currPosition = finalPosition;
        islandManager.calculateInfluence(finalPosition);
    }

    /**
     * Returns MotherNature current position
     * @return MotherNature current position
     */
    public Island getPosition() {
        return this.currPosition;
    }

    /**
     * Returns the default MovementStrategy
     * @return the default MovementStrategy
     */
    public MovementStrategy getDefaultMovement() {
        return defaultMovement;
    }

    /**
     * Returns the current MovementStrategy
     * @return the current MovementStrategy
     */
    public MovementStrategy getCurrMovementStrategy() {
        return currMovementStrategy;
    }
}


