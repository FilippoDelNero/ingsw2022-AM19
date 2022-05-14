package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.Map;

public class AbstractPhase {
    protected final Map<String, ClientManager> clientManagerMap;
    //private final Reducer reducer;
    protected final MatchDecorator model;
    protected final MatchController matchController;

    public AbstractPhase(MatchController matchController) {
        this.clientManagerMap = matchController.getClientManagerMap();
        this.model = matchController.getModel();
        this.matchController = matchController;
    }

}
