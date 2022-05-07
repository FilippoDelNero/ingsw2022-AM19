package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.View.View;

import java.util.concurrent.ExecutionException;

/**
 * client-side controller used to react to incoming messages
 */
public class ClientSideController {
    /** reference to the client, necessary to be able to send back replies to the server */
    private final Client myClient;

    /** reference to the view, used to communicate with the user*/
    private final View view;

    /** nickname chosen by the user, used to sign outgoing messages*/
    private String nickname;

    /** a references to the second to last message, used to recover from errors*/
    private Message previousMsg;

    /**
     * class constructor, it also perform the view.init() method
     * @param client the client that this controller is associated with
     * @param view the view this controller has to work with
     */
    public ClientSideController(Client client, View view) {
        myClient = client;
        this.view = view;
        view.init();
    }

    /**
     * the method called by the client to pass a message that's to be worked on
     * @param msg the message sent by the server
     */
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

    /**
     * The method called when a AskFirstPlayerMessage comes in
     * if the are match to resume those will be shown otherwise it will be asked the number of player
     * and the difficulty of the new match that will be created
     * @param msg the AskFirstPlayerMessage sent by the server
     */
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
        //TODO manca cosa fare in caso di else
    }

    /**
     * The method is called when a AskLoginInfoMessage comes in
     * it asks and sends nickname, wizardFamily and towerColor
     * @param msg the AskLoginInfoMessage sent by the server
     */
    private void askLoginInfo(AskLoginInfoMessage msg){
        TowerColor towercolor;
        WizardFamily wizardFamily;
        try {
            this.nickname = view.askNickname();
            towercolor = view.askTowerColor(msg.getTowerColorsAvailable());
            wizardFamily = view.askWizardFamily(msg.getWizardFamiliesAvailable());
            myClient.sendMessage(new ReplyLoginInfoMessage(nickname,towercolor,wizardFamily));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to display a genericMessage coming from the server
     * @param msg the GenericMessage sent by the server
     */
    private void generic(GenericMessage msg) {
        view.genericPrint(msg.toString());
    }

    /**
     * Method used to display an errorMessage and to recover to the last message by simulating the arrival
     * of the message of which the answer caused the error
     * @param msg the ErrorMessage sent by the server
     */
    private void error(ErrorMessage msg) {
        view.genericPrint(msg.toString());
        communicate(previousMsg);
    }
}
