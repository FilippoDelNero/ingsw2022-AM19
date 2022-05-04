package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * class used to log in a newly connected player
 */
public class LoginManager {
    /** the number of active players */
    private int activePlayer;
    /** total number of player able to connect */
    private int numOfPlayer;
    /** the list of the currently available towerColors */
    private final ArrayList<TowerColor> availableTowerColor;
    /** the list of the currently available wizardFamilies */
    private final ArrayList<WizardFamily> availableWizardFamilies;
    /** the list of taken nicknames */
    private final ArrayList<String> nicknames;
    /** the list of nicknames used in a resuming match */
    ArrayList<String> lastMatchPlayers;
    /** field containing the reply from the client */
    private Message answerFromClient = null;
    /** set to true if we are currently resuming a match, to false if we are creating a new one */
    private boolean isResumingMatch = false;



    /**
     * class constructor, sets the number of active players to 0 and number of player to 10,
     * this last value will be overwritten by the first player
     * new lists containing all possible wizardFamilies and towerColors are created
     */
    public LoginManager() {
        activePlayer = 0;
        numOfPlayer = 10;
        availableTowerColor = new ArrayList<>();
        availableWizardFamilies = new ArrayList<>();
        lastMatchPlayers = new ArrayList<>();
        nicknames = new ArrayList<>();
        availableWizardFamilies.addAll(Arrays.asList(WizardFamily.values()));
        availableTowerColor.addAll(Arrays.asList(TowerColor.values()));

    }

    /**
     * method used to log in a new player
     * @param clientToAdd the clientManager of the newly accepted player
     */
    public void login (ClientManager clientToAdd) {
        //if there is already a match in progress refuse all connection
        if(activePlayer == numOfPlayer){
            clientToAdd.sendMessage(new GenericMessage("Match already started. Please try later"));
            clientToAdd.close();
        }
        //if the player connecting is the first one
        else if (activePlayer==0){
            //TODO controllare se ci sono match già salavti e passarlo nel messaggio, altrimenti null
            clientToAdd.sendMessage(new AskFirstPlayerMessage(null));
            waitForReply();
            switch (answerFromClient.getMessageType()) {
                case REPLY_CREATE_MATCH -> {
                    ReplyCreateMatchMessage msg = (ReplyCreateMatchMessage) answerFromClient;
                    //TODO qua creiamo gameController e possiamo dirgli da quanti player fare il match e la difficoltà
                    numOfPlayer = msg.getNumOfPlayer();
                    clientToAdd.sendMessage(new AskLoginInfoMessage(availableTowerColor, availableWizardFamilies));
                    waitForReply();
                    ReplyLoginInfoMessage secondMsg = (ReplyLoginInfoMessage) answerFromClient;
                    System.out.println(secondMsg.toString());
                    while (!availableTowerColor.contains(secondMsg.getTowerColor()) &&
                            !availableWizardFamilies.contains(secondMsg.getWizardFamily())) {
                        clientToAdd.sendMessage(new ErrorMessage("server", "Invalid parameters, retry"));
                        waitForReply();
                        secondMsg = (ReplyLoginInfoMessage) answerFromClient;
                    }
                    System.out.println(secondMsg.getNickname());
                    nicknames.add(secondMsg.getNickname());
                    availableTowerColor.remove(secondMsg.getTowerColor());
                    availableWizardFamilies.remove(secondMsg.getWizardFamily());
                    //TODO qua posso passare il giocatore o i tre valori al gameController.
                    // aggiungo alla mappa del gameController la entry nickname-ClientManager
                    activePlayer++;
                    System.out.println(activePlayer + "ciao" + nicknames);
                }
                case RESUME_MATCH -> {
                    //TODO recupero nomi giocatori da file + model + controller
                    isResumingMatch = true;
                    lastMatchPlayers.add("Dennis");
                    lastMatchPlayers.add("Phil");
                    lastMatchPlayers.add("Laura");
                    clientToAdd.sendMessage(new AskNicknameOptionsMessage(lastMatchPlayers));
                    waitForReply();
                    ReplyLoginInfoMessage msg = (ReplyLoginInfoMessage) answerFromClient;
                    //TODO associo nickname arrivato al client manager su GameController
                    while (!lastMatchPlayers.contains(msg.getNickname())){
                        clientToAdd.sendMessage(new ErrorMessage("server","Invalid username replied"));
                        waitForReply();
                        msg = (ReplyLoginInfoMessage) answerFromClient;
                    }
                    lastMatchPlayers.remove(msg.getNickname());
                    activePlayer++;
                    //TODO assegno quel client handler a quel nickname
                }
            }
        }
        //if the player is not the first one, but there is not a match en course
        else {
            //if we are adding a player to a match previously interrupted
            if(isResumingMatch){
                clientToAdd.sendMessage(new AskNicknameOptionsMessage(lastMatchPlayers));
                waitForReply();
                ReplyLoginInfoMessage msg = (ReplyLoginInfoMessage) answerFromClient;
                //TODO associo nickname arrivato al client manager su GameController
                while (!lastMatchPlayers.contains(msg.getNickname())){
                    clientToAdd.sendMessage(new ErrorMessage("server","Invalid username replied"));
                    waitForReply();
                    msg = (ReplyLoginInfoMessage) answerFromClient;
                }
                lastMatchPlayers.remove(msg.getNickname());
                activePlayer++;
                //TODO assegno quel client handler a quel nickname
            }
            //if we are adding a player to a newly created match
            else{
            clientToAdd.sendMessage(new AskLoginInfoMessage(availableTowerColor,availableWizardFamilies));
            waitForReply();
            ReplyLoginInfoMessage msg = (ReplyLoginInfoMessage) answerFromClient;
            while (nicknames.contains(msg.getNickname()) ||
                    !availableTowerColor.contains(msg.getTowerColor()) &&
                    !availableWizardFamilies.contains(msg.getWizardFamily())) {
                System.out.println("okokokoko");
                clientToAdd.sendMessage(new ErrorMessage("server", "Invalid parameters, retry"));
                waitForReply();
                msg = (ReplyLoginInfoMessage) answerFromClient;
            }
            nicknames.add(msg.getNickname());
            availableWizardFamilies.remove(msg.getWizardFamily());
            availableTowerColor.remove(msg.getTowerColor());
            //TODO qua posso passare il giocatore o i tre valori al gameController.
            // aggiungo alla mappa del gameController la entry nickname-ClientManager

            activePlayer++;
            }
        }
    }

    /**
     * setter used to set the answerFromClient parameter
     * @param answerFromClient client's answer to a given server's message
     */
    public void setAnswerFromClient(Message answerFromClient) {
        this.answerFromClient = answerFromClient;
    }

    /**
     * waits for an answer from client
     */
    public synchronized void waitForReply() {
        answerFromClient = null;
        while(answerFromClient == null) {
            try {
                wait(200);
            } catch (InterruptedException ignored) { }
        }
    }
}
