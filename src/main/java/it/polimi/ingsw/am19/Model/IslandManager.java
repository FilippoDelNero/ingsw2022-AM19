package it.polimi.ingsw.am19.Model;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.InfluenceStrategy;
import it.polimi.ingsw.am19.Model.InfluenceStrategies.StandardInfluence;
import java.util.*;

/**
 * This class is used by the MatchXPlayer class to manage the islands
 */
public class IslandManager {
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
    private InfluenceStrategy influenceStrategy;

    /**
     * default influence strategy, it is used to reset every island strategy after use
     */
    private final InfluenceStrategy standardInfluence;

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
        standardInfluence = new StandardInfluence();
        islands = new IslandList<>();
        influenceStrategy = standardInfluence;

        //fill the ArrayList with the initial 12 islands
        for(int i = 0; i<MAXNUMOFISLAND; i++) {
            islands.add(new Island(standardInfluence));
        }

        iterator = islands.iterator();
        professorManager = manager;
    }


    public List<Island> getIslands() {
        return islands.copy();
    }

    public void setInfluenceStrategy(InfluenceStrategy strategy) {
        this.influenceStrategy = strategy;
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
        if(!(island.getInfluenceStrategy() instanceof StandardInfluence)) {
            island.setInfluenceStrategy(standardInfluence);
        }
        else {
            island.setInfluenceStrategy(influenceStrategy);
            island.calculateInfluence(professorManager);
            lookForIslandsToMerge();
        }
        influenceStrategy = standardInfluence;
    }

    /**
     * check if there are adjacent islands with same tower's color and merges them
     */
    private void lookForIslandsToMerge() {
        Island island1;
        Island island2;
        for(int i = 0; i <= islands.size(); i++) {
            island1 = iterator.next();
            island2 = iterator.next();
            if(island1.getTowerColor() != null && island1.getTowerColor() == island2.getTowerColor()) {
                unify(island1, island2);
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
        Map<PieceColor, Integer> map = new HashMap<>(island1.getNumOfStudent());
        island2.getNumOfStudent().forEach((k, v) -> map.merge(k, v, Integer::sum));

        //grab the tower's color from island1 (its the same as island2)
        TowerColor towerColor = island1.getTowerColor();

        //sums the number of island present in this group
        int numOfIslands = island1.getNumOfIslands() + island2.getNumOfIslands();

        //check for already implemented strategies
        InfluenceStrategy strategy;
        if(!(island1.getInfluenceStrategy() instanceof StandardInfluence)) {
            strategy = island1.getInfluenceStrategy();
        }
        else if(!(island2.getInfluenceStrategy() instanceof StandardInfluence)) {
            strategy = island2.getInfluenceStrategy();
        }
        else strategy = standardInfluence;

        //creates a new island
        Island newIsland = new Island(map, towerColor, true, strategy, numOfIslands);

        //adds the new island and deletes the old ones
        iterator.remove();
        iterator.set(newIsland);
    }
}
