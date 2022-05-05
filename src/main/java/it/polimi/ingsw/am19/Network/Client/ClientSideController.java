package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.View.Cli.Cli;

import java.util.concurrent.ExecutionException;

public class ClientSideController {
    private String nickname;
    private final Client myClient;
    private final Cli view;
    private Message previousMsg;

    public ClientSideController(Client client, Cli view) {
        myClient = client;
        this.view = view;
        view.init();
    }

    public void communicate(Message msg) {
        MessageType type = msg.getMessageType();
        //save the previously sent message to recover from an error
        if(!(type == MessageType.ERROR_MESSAGE))
            previousMsg = msg;
        switch (type) {
            case ASK_LOGIN_FIRST_PLAYER -> askLoginFirstPlayer( (AskFirstPlayerMessage) msg);
            case ASK_LOGIN_INFO -> askLoginInfo((AskLoginInfoMessage) msg);
            case ERROR_MESSAGE -> error((ErrorMessage) msg);
            case GENERIC_MESSAGE -> generic((GenericMessage) msg);
        }
    }

    private void askLoginFirstPlayer(AskFirstPlayerMessage msg) {
        int numOfPlayers;
        boolean isExpert;
        if(msg.getMatchToResume() == null) {
            try {
                numOfPlayers = view.newMatchNumOfPlayers();
                isExpert = view.newMatchIsExpert();
                myClient.sendMessage(new ReplyCreateMatchMessage(numOfPlayers, isExpert));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
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
