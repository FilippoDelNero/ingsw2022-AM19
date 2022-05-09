package it.polimi.ingsw.am19.Controller;

import java.util.List;

public interface Phase {
    void inspectMessage();

    List<String> getPlayersOrder();

    void performActions();
}
