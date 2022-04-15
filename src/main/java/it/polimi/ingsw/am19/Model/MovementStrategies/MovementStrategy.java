package it.polimi.ingsw.am19.Model.MovementStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;

import java.util.ListIterator;
//TODO aggiungere javadoc
public interface MovementStrategy {
    Island move(int numOfSteps, Island currPosition, ListIterator<Island> islandsIterator);
}
