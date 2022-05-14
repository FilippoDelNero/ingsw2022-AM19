package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Network.Message.Message;

import java.util.List;

public class ActionPhase extends AbstractPhase implements Phase{
    private final List<String> playersOrder;

    public ActionPhase(List<String> playersOrder,MatchController matchController) {
        super(matchController);
        this.playersOrder = playersOrder;
    }

    @Override
    public void inspectMessage(Message msg) {

    }

    @Override
    public List<String> getPlayersOrder() {
        return this.playersOrder;
    }

    @Override
    public void performPhase(String currPlayer) {

    }

    @Override
    public void initPhase() {
    }

}
