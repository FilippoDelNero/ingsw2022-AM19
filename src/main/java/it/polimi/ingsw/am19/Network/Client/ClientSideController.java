package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.MessageType;
import it.polimi.ingsw.am19.Network.Message.ReplyResumeMatch;
import it.polimi.ingsw.am19.View.Cli.Cli;

import java.util.concurrent.ExecutionException;

public class ClientSideController {
    private String nickname;
    private Client myClient;
    private Cli view;

    public ClientSideController(Client client, Cli view) {
        myClient = client;
        this.view = view;
    }

    public void communicate(Message msg) {
        MessageType type = msg.getMessageType();
        switch (type) {
            case ASK_RESUME_MATCH -> askResumeMatch();
        }
    }

    private void askResumeMatch() {
        try {
            boolean bool = view.askResumeMatch();
            Message msg = new ReplyResumeMatch(null, bool);
            myClient.sendMessage(msg);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
