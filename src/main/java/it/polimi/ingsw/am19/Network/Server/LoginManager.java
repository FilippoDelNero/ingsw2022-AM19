package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Controller.MatchController;
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
    private int activePlayers;

    /** total number of player able to connect */
    private int numOfPlayers;

    /** set to true if we are currently resuming a match, to false if we are creating a new one */
    private boolean isResumingMatch;

    /** set to true if the newly created match will be an expert one*/
    private boolean isExpertMatch;

    /** the list of the currently available towerColors */
    private final ArrayList<TowerColor> availableTowerColor;

    /** the list of the currently available wizardFamilies */
    private final ArrayList<WizardFamily> availableWizardFamilies;

    /** the list of taken nicknames */
    private final ArrayList<String> nicknames;

    /** the list of nicknames used in a resuming match */
    private ArrayList<String> lastMatchPlayers;

    /** field containing the reply from the client */
    private Message answerFromClient;

    /**
     * keeps a reference to the MatchController class
     */
    private final MatchController matchController;

    /**
     * class constructor, sets the number of active players to 0 and number of player to 10,
     * this last value will be overwritten by the first player
     * new lists containing all possible wizardFamilies and towerColors are created
     */
    public LoginManager(MatchController matchController) {
        this.matchController = matchController;
        activePlayers = 0;
        numOfPlayers = 10;

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
     * @return true if the client is added to the match, false otherwise
     */
    public boolean login (ClientManager clientToAdd) {
        //if there is already a match in progress refuse all connection
        if(activePlayers == numOfPlayers){
            clientToAdd.sendMessage(new GenericMessage("Match already started. Please try later", MessageType.GENERIC_MESSAGE));
            clientToAdd.close();
            return false;
        }

        //if the player connecting is the first one
        else if (activePlayers == 0){


            clientToAdd.sendMessage(new AskFirstPlayerMessage(matchController.checkOldMatches()));
            waitForReply();

            switch (answerFromClient.getMessageType()) {
                //The player wants to create a new match
                case REPLY_CREATE_MATCH -> {
                    isResumingMatch = false;
                    ReplyCreateMatchMessage msg = (ReplyCreateMatchMessage) answerFromClient;

                    numOfPlayers = msg.getNumOfPlayer();
                    isExpertMatch = msg.isExpert();

                    matchController.createNewMatch(numOfPlayers,isExpertMatch);
                    addPlayerToNewMatch(clientToAdd);
                }
                //The player wants to resume an old match
                case RESUME_MATCH -> {
                    isResumingMatch = true;

                    matchController.resumeMatch();
                    lastMatchPlayers = new ArrayList<>(matchController.getNicknamesFromResumedMatch());
                    numOfPlayers = matchController.getNicknamesFromResumedMatch().size();
                    addPlayerToResumingMatch(clientToAdd);
                    sendMessageOfWait(clientToAdd);
                    activePlayers++;
                }
            }
            return true;
        }

        //if the player is not the first one, but there is not a match en course
        else {
            //if we are adding a player to a match previously interrupted
            if(isResumingMatch) {
                addPlayerToResumingMatch(clientToAdd);
                activePlayers++;
                if (activePlayers < numOfPlayers)
                    sendMessageOfWait(clientToAdd);

            }
            // if we are adding a player to a newly created match
            else if(isExpertMatch){
                clientToAdd.sendMessage(new GenericMessage("You will be added to a match of " + numOfPlayers +
                        " players. The match difficulty will be expert...", MessageType.GENERIC_MESSAGE));
                addPlayerToNewMatch(clientToAdd);
            }else{
                clientToAdd.sendMessage(new GenericMessage("You will be added to a match of " + numOfPlayers +
                        " players. The match difficulty will be easy...", MessageType.GENERIC_MESSAGE));
                addPlayerToNewMatch(clientToAdd);
            }
            return true;
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

    /**
     * private method to add a player to a new match
     * @param clientToAdd the clientManager currently served by the LoginManager
     */
    private void addPlayerToNewMatch(ClientManager clientToAdd) {
        if (numOfPlayers == 2)
            availableTowerColor.removeIf(color -> color == TowerColor.GREY);
        clientToAdd.sendMessage(new AskLoginInfoMessage(availableTowerColor, availableWizardFamilies));
        waitForReply();
        ReplyLoginInfoMessage msg = (ReplyLoginInfoMessage) answerFromClient;

        while (checkLoginParameters(msg.getNickname(), msg.getTowerColor(), msg.getWizardFamily())) {
            clientToAdd.sendMessage(new ErrorMessage("server", "Invalid parameters, retry"));
            waitForReply();
            msg = (ReplyLoginInfoMessage) answerFromClient;
        }
        String nickname = msg.getNickname();
        TowerColor towerColor = msg.getTowerColor();
        WizardFamily wizardFamily = msg.getWizardFamily();

        matchController.addPlayer(nickname,towerColor,wizardFamily);
        matchController.setClientManager(nickname,clientToAdd);
        nicknames.add(nickname);
        availableWizardFamilies.remove(wizardFamily);
        availableTowerColor.remove(towerColor);
        sendMessageOfWait(clientToAdd);
        activePlayers++;

        if (activePlayers == numOfPlayers)
            matchController.changeState();
    }

    /**
     * private method to add a player to a resuming match
     * @param clientToAdd the clientManager currently served by the LoginManager
     */
    private void addPlayerToResumingMatch(ClientManager clientToAdd) {
        clientToAdd.sendMessage(new AskNicknameOptionsMessage(lastMatchPlayers));
        waitForReply();
        ReplyLoginInfoMessage msg = (ReplyLoginInfoMessage) answerFromClient;

        while (!lastMatchPlayers.contains(msg.getNickname())) { //While the username is invalid we keep asking for it
            clientToAdd.sendMessage(new ErrorMessage("server","Invalid username replied"));
            waitForReply();
            msg = (ReplyLoginInfoMessage) answerFromClient;
        }

        String nickname = msg.getNickname();
        matchController.setClientManager(nickname,clientToAdd);
        lastMatchPlayers.remove(nickname);
        if(lastMatchPlayers.size()==0)
            matchController.inProgress();
    }

    /**
     * private method used to check that the login parameters are valid
     * @param nickname the nickname chosen by the player
     * @param towerColor the towerColor chosen by the player
     * @param wizardFamily the wizardFamily chosen by the player
     * @return true if the parameter are WRONG, false otherwise
     */
    private boolean checkLoginParameters(String nickname, TowerColor towerColor, WizardFamily wizardFamily) {
        return (nicknames.contains(nickname) ||
                !availableTowerColor.contains(towerColor) ||
                !availableWizardFamilies.contains(wizardFamily));
    }

    /**
     * method to send a message to the already connected client telling them to wait for other players
     * @param clientToAdd the client currently served by the loginManager
     */
    private void sendMessageOfWait(ClientManager clientToAdd) {
        clientToAdd.sendMessage(new GenericMessage("waiting for others player to join...", MessageType.WAIT_MESSAGE));
    }
}
