package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Network.Message.Message;

import java.util.List;

public interface Phase {
    void inspectMessage(Message msg);

    List<String> getPlayersOrder();

    void performPhase(String currPlayer);

    void initPhase();
}
