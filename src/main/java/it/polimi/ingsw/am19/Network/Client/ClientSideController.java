package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.MessageType;
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
            //
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void askLoginInfo(AskLoginInfoMessage msg){
        String nickname;
        TowerColor towercolor;
        WizardFamily wizardFamily;
        try {
            nickname = view.askNickname();
            towercolor = view.askTowerColor(msg.getTowerColorsAvailable());
            wizardFamily = view.askWizardFamily(msg.getWizardFamiliesAvailable());
            myClient.sendMessage(new ReplyLoginInfoMessage(nickname,towercolor,wizardFamily));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void generic(GenericMessage msg) {
        view.genericPrint(msg.toString());
    }

    private void error(ErrorMessage msg) {
        view.error(msg.toString());
        communicate(previousMsg);
    }
}
