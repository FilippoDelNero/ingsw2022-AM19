package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

import java.util.List;

public class AskTowerColorMessage extends Message {
    private final List<TowerColor> towerColorAvailable;

    public AskTowerColorMessage( List<TowerColor> towerColorAvailable) {
        super("server", MessageType.ASK_TOWER_COLOR);
        this.towerColorAvailable = towerColorAvailable;
    }

    public List<TowerColor> getTowerColorAvailable() {
        return towerColorAvailable;
    }

    @Override
    public String toString() {
        return "AskTowerColorMessage{" +
                "towerColorAvailable=" + towerColorAvailable +
                '}';
    }
}
