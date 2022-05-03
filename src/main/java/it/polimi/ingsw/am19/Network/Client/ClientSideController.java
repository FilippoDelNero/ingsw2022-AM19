package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.View.View;

import java.util.concurrent.ExecutionException;

public class ClientSideController {
    private String nickname;

    private final Client myClient;

    private final View view;

    private Message previousMsg;

    public ClientSideController(Client client, View view) {
        myClient = client;
        this.view = view;
        view.init();
        login();
    }

    public void communicate(Message msg) {
        MessageType type = msg.getMessageType();
        //save the previously sent message to recover from an error
        if(!(type == MessageType.ERROR_MESSAGE))
            previousMsg = msg;
        switch (type) {
            case ASK_RESUME_MATCH -> login();
            case ASK_NUM_PLAYERS -> askNumOfPlayers();
            case ERROR_MESSAGE -> error(msg.toString());
        }
    }

    private void askNumOfPlayers() {

    }

    private void login() {

    }

    private void error(String error) {
        view.error(error);
        communicate(previousMsg);
    }
}
