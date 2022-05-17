package it.polimi.ingsw.am19.Persistence;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;

import java.io.Serializable;

/**
 * This class contains all the object must be stored for saving a match
 */
public class SavedData implements Serializable {
    private final MatchDecorator model;
    private final int roundNumber;


    public SavedData(MatchDecorator model, int roundNumber) {
        this.model = model;
        this.roundNumber = roundNumber;
    }

    public MatchDecorator getModel() {
        return model;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
