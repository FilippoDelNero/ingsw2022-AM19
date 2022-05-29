package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;

/**
 * Class for putting together common Phases characteristics
 */
public class AbstractPhase {
    protected final MatchDecorator model;
    protected final MatchController matchController;
    protected final InputController inputController;

    public AbstractPhase(MatchController matchController) {
        this.model = matchController.getModel();
        this.matchController = matchController;
        this.inputController = new InputController(matchController);
    }

}
