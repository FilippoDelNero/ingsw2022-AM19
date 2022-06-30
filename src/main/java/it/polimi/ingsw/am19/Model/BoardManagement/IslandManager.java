package it.polimi.ingsw.am19.Model.BoardManagement;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoEntryTileInfluence;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.Utilities.IslandList;
import it.polimi.ingsw.am19.Utilities.Observer;
import it.polimi.ingsw.am19.Utilities.Notification;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Utilities.Observable;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used by the MatchXPlayer class to manage the islands
 */
public class IslandManager extends Observable implements Observer, Serializable {
    /**
     * list storing the number of island or group of islands currently present
     */
    private final IslandList<Island> islands;

    /**
     * keeps a reference to the professorManager, it is used to calculate the influence of players
     */
    private final ProfessorManager professorManager;

    /**
     * keeps the strategy that will be used to calculate players' influence
     */
    private InfluenceStrategy currInfluenceStrategy;

    /**
     * default influence strategy, it is used to reset every island strategy after use
     */
    private final InfluenceStrategy stdInfluenceStrategy;

    /**
     * the iterator of the list of island
     */
    private transient ListIterator<Island> iterator;

    /**
     * it is the maximum number of island present in a game
     */
    private static final int MAXNUMOFISLAND = 12;

    /**
     * it creates a new IslandManager with its initial 12 islands
     * @param manager: the match will pass a professorManager to this class
     */
    public IslandManager(ProfessorManager manager) {
        stdInfluenceStrategy = new StandardInfluence();
        islands = new IslandList<>();
        currInfluenceStrategy = stdInfluenceStrategy;

        //fill the ArrayList with the initial 12 islands
        for(int i = 0; i<MAXNUMOFISLAND; i++) {
            Island isle = new Island(currInfluenceStrategy);
            isle.addObserver(this);
            islands.add(isle);
        }

        iterator = islands.iterator();
        professorManager = manager;
    }

    /**
     * getter for the islands attribute
     * @return a List of Islands which contains a copy of the IslandList islands attribute
     */
    public List<Island> getIslands() {
        return islands.copy();
    }

    /**
     * return a reference to iterator for the IslandList islands
     * @return return an iterator
     */
    public ListIterator<Island> getIterator() {
        return iterator;
    }

    /**
     * return a new iterator for the IslandList islands
     * @return a new iterator
     */
    public  ListIterator<Island> getNewIterator() {
        return islands.iterator();
    }

    /**
     * setter for the currInfluenceStrategy attribute
     * @param strategy the influence strategy corresponding to the played card
     */
    public void setInfluenceStrategy(InfluenceStrategy strategy) {
        this.currInfluenceStrategy = strategy;
    }

    /**
     * getter for the currInfluenceStrategy attribute
     * @return the strategy currently set
     */
    public InfluenceStrategy getInfluenceStrategy() {
        return currInfluenceStrategy;
    }

    /**
     * this method is used to set directly the strategy of an given island.
     * this method should be used only by the NoEntryTileCard
     * @param island the island of which i want to change strategy
     * @param strategy the strategy: should be only noEntryTileInfluence
     */
    public void setIslandInfluenceStrategy(Island island, InfluenceStrategy strategy) {
        island.setInfluenceStrategy(strategy);
    }

    /**
     * setter for the Iterator attribute
     */
    public void setIterator() {
        this.iterator = islands.iterator();
    }

    /**
     * calculate the influence of each player on the island according to the strategy set
     * @param island is the island upon which the influence will be calculated
     */
    public void calculateInfluence(Island island) {
        boolean changes;

        if(!(island.getInfluenceStrategy() instanceof StandardInfluence)) {
            island.setInfluenceStrategy(stdInfluenceStrategy);
        }
        else {
            island.setInfluenceStrategy(currInfluenceStrategy);
            changes = island.calculateInfluence(professorManager);
            island.setInfluenceStrategy(stdInfluenceStrategy);
            if(changes)
                searchForIslandToMerge(island);
        }
        currInfluenceStrategy = stdInfluenceStrategy;
    }

    /**
     * check if there are adjacent islands with same tower's color and merges them
     * @param island the island around which i should check
     */
    private void searchForIslandToMerge(Island island) {
        if(island.getTowerColor() != null && moveIteratorToIsland(island)) {
            Island islandBefore = iterator.previous();
            iterator.next();
            Island islandAfter = iterator.next();
            if(island.getTowerColor() == islandBefore.getTowerColor()) {
                iterator.previous();
                unify(islandBefore, island);
            }
            else if(island.getTowerColor() == islandAfter.getTowerColor()) {
                unify(island, islandAfter);
            }
        }
    }

    /**
     * utility method used to move the iterator to a specific island
     * @param island the island to which the iterator should be moved to
     * @return true if the island was successfully found, false otherwise
     */
    private boolean moveIteratorToIsland(Island island) {
        boolean found = false;
        for(int i = 0; i <= islands.size(); i++) {
            if(iterator.next().equals(island)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * utility method used to check if there are less than three island in the archipelago
     * @return true if is end-match, false otherwise
     */
    private boolean isEnd() {
        if (getIslands().size() <= 3) {
            notifyObservers(Notification.END_MATCH);
            return true;
        }
        return false;
    }

    /**
     * it merges two islands into a new one, the old islands will get deleted
     * after merging together two islands, a check is made to the number of Islands in the archipelago
     * if the number is 3, a notification is sent to the observers to say that an end match conditions is now verified
     * @param island1 the first island to be merged
     * @param island2 the second island to be merged
     */
    private void unify(Island island1, Island island2) {
        //merge the numOfStudents maps from Island1 & 2 into map
        Map<PieceColor, Integer> map = new HashMap<>(island1.getNumOfStudents());
        island2.getNumOfStudents().forEach((k, v) -> map.merge(k, v, Integer::sum));

        //grab the tower's color from island1 (it's the same as island2)
        TowerColor towerColor = island1.getTowerColor();

        //sums the number of island present in this group
        int numOfIslands = island1.getNumOfIslands() + island2.getNumOfIslands();

        //check for already implemented strategies
        InfluenceStrategy strategy;
        if(island1.getInfluenceStrategy() instanceof NoEntryTileInfluence) {
            strategy = island1.getInfluenceStrategy();
        }
        else if(island2.getInfluenceStrategy() instanceof NoEntryTileInfluence) {
            strategy = island2.getInfluenceStrategy();
        }
        else strategy = stdInfluenceStrategy;

        //set motherNature presence
        boolean isPresent = island1.isMotherNaturePresent() || island2.isMotherNaturePresent();

        //creates a new island
        Island newIsland = new Island(map, towerColor, isPresent, strategy, numOfIslands);
        newIsland.addObserver(this);

        //adds the new island and deletes the old ones
        iterator.remove();
        iterator.set(newIsland);
        if(!isEnd()) {
            searchForIslandToMerge(newIsland);
        }
    }

    /**
     * method to register this manager as an observer of each island
     */
    public void registerObserverToIslands(){
        for(Island i: getIslands())
            i.addObserver(this);
    }

    /**
     * reacts to notifications from the islands
     */
    @Override
    public void notify(Notification notification) {
        switch (notification) {
            case UPDATE_ISLANDS -> notifyObservers(Notification.UPDATE_ISLANDS);
            case UPDATE_GAMEBOARDS -> notifyObservers(Notification.UPDATE_GAMEBOARDS);
        }
    }

    /**
     * check if there are adjacent islands with same tower's color and merges them
     * after merging together two islands, a check is made to the number of Islands in the archipelago
     * if the number is 3, a notification is sent to the observers to say that an end match conditions is now verified
     */
    @Deprecated
    private void lookForIslandsToMerge() {
        int sizeBefore = getIslands().size();
        Island island1;
        Island island2;
        island1 = iterator.next();
        island2 = iterator.next();
        for(int i = 0; i <= islands.size(); i++) {
            if(island1.getTowerColor() != null && island1.getTowerColor() == island2.getTowerColor()) {
                unify(island1, island2);
                assert getIslands().size() == (sizeBefore - 1);
                if (getIslands().size() == 3)
                    notifyObservers(Notification.END_MATCH);
                else
                    lookForIslandsToMerge();
            }
            island1 = island2;
            island2 = iterator.next();
        }
    }
}
