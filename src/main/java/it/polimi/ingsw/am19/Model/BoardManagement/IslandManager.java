package it.polimi.ingsw.am19.Model.BoardManagement;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.NoEntryTileInfluence;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import it.polimi.ingsw.am19.Model.Utilities.IslandList;
import it.polimi.ingsw.am19.Observer;
import it.polimi.ingsw.am19.Utilities.Notification;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Observable;

import java.util.*;

/**
 * This class is used by the MatchXPlayer class to manage the islands
 */
public class IslandManager extends Observable {
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
    private final ListIterator<Island> iterator;

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
            islands.add(new Island(currInfluenceStrategy));
        }

        iterator = islands.iterator();
        professorManager = manager;
    }

    /**
     * getter for the islands attribute
     * @return a List<Island> which contains a copy of the IslandList islands attribute
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
            if(changes)
                lookForIslandsToMerge();
        }
        currInfluenceStrategy = stdInfluenceStrategy;
    }

    /**
     * check if there are adjacent islands with same tower's color and merges them
     * after merging together two islands, a check is made to the number of Islands in the archipelago
     * if the number is 3, a notification si sent to the observers to say that an end match conditions is now verified
     */
    private void lookForIslandsToMerge() {
        Island island1;
        Island island2;
        for(int i = 0; i <= islands.size(); i++) {
            island1 = iterator.next();
            island2 = iterator.next();
            if(island1.getTowerColor() != null && island1.getTowerColor() == island2.getTowerColor()) {
                unify(island1, island2);
                if (getIslands().size() == 3)
                    for (Observer observer: observers)
                        observer.notify(Notification.FINAL_ROUND);
            }
        }
    }

    /**
     * it merges two islands into a new one, the old islands will get deleted
     * @param island1 the first island to be merged
     * @param island2 the second island to be merged
     */
    private void unify(Island island1, Island island2) {
        //merge the numOfStudents maps from Island1 & 2 into map
        Map<PieceColor, Integer> map = new HashMap<>(island1.getNumOfStudents());
        island2.getNumOfStudents().forEach((k, v) -> map.merge(k, v, Integer::sum));

        //grab the tower's color from island1 (its the same as island2)
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

        //creates a new island
        Island newIsland = new Island(map, towerColor, true, strategy, numOfIslands);

        //adds the new island and deletes the old ones
        iterator.remove();
        iterator.set(newIsland);
    }
}
