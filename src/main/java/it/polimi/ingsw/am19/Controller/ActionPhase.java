package it.polimi.ingsw.am19.Controller;

import java.util.List;

public class ActionPhase implements Phase{
    private final List<String> playersOrder;

    public ActionPhase(List<String> playersOrder) {
        this.playersOrder = playersOrder;
    }

    @Override
    public void inspectMessage() {

    }

    @Override
    public List<String> getPlayersOrder() {
        return this.playersOrder;
    }

    @Override
    public void performActions() {

    }

    @Override
    public String toString() {
        return "action phase";
    }
}
