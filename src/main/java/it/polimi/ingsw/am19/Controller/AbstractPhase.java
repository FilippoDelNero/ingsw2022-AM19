package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.Map;

/**
 * Class for putting together common Phases characteristics
 */
public class AbstractPhase {
    protected final Map<String, ClientManager> clientManagerMap;
    protected final MatchDecorator model;
    protected final MatchController matchController;
    protected final InputController inputController;

    public AbstractPhase(MatchController matchController) {
        this.clientManagerMap = matchController.getClientManagerMap();
        this.model = matchController.getModel();
        this.matchController = matchController;
        this.inputController = matchController.getInputController();
    }

}
