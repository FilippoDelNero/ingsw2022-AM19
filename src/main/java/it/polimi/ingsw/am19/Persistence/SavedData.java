package it.polimi.ingsw.am19.Persistence;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;

import java.io.Serializable;

/**
 * This class contains all the object must be stored for saving a match
 */
public class SavedData implements Serializable {
    private final boolean isExpert;
    private final MatchDecorator model;


    public SavedData(boolean isExpert, MatchDecorator model) {
        this.isExpert = isExpert;
        this.model = model;
    }

    public boolean isExpert() {
        return isExpert;
    }

    public MatchDecorator getModel() {
        return model;
    }

}
