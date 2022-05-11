package it.polimi.ingsw.am19.Controller;

import java.util.List;

public class PlanningPhase implements Phase{
    private final List<String> playersOrder;

    public PlanningPhase(List<String> playersOrder) {
        this.playersOrder = playersOrder;
    }

    @Override
    public void inspectMessage() {

    }

    @Override
    public List<String> getPlayersOrder() {
        return playersOrder;
    }

    @Override
    public void performActions() {
        
    }

    @Override
    public String toString() {
        return "planning phase";
    }
}
