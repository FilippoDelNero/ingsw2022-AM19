package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.Island;

import java.util.List;
import java.util.ListIterator;

public interface MovementStrategy {
    public Island move(int numOfSteps, Island currPosition, ListIterator<Island> islandsIterator);
}
