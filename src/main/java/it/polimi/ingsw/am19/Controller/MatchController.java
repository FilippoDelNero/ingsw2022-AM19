package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Model.Match.ThreePlayersMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.ErrorMessage;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchController {
    /**
     * Keeps a reference to the game model
     */
    private MatchDecorator model;

    /**
     * Associates every nickname with the corresponding ClientManager
     */
    private final Map<String, ClientManager> clientManagerMap;

    /**
     * Saves the current match state
     */
    private StateType currState;

    /**
     * Keeps memory of the previous match state
     */
    private StateType prevState;

    /**
     * Is the nickname of the current player
     */
    private String currPlayer;

    public MatchController(){
        this.clientManagerMap = new ConcurrentHashMap<>();
        this.currState = StateType.LOGIN;
        this.prevState = StateType.LOGIN;
    }

    /**
     * Given the number of players and the difficulty level, it creates a new match
     * @param numOfPlayers is the numberOfPlayers that will take part to the new match
     * @param isExpert is the difficulty chosen for the new match
     */
    public void createNewMatch(int numOfPlayers, boolean isExpert){
            if (numOfPlayers == 2)
                this.model = new MatchDecorator(new TwoPlayersMatch());
            else if (numOfPlayers == 3){
                this.model = new MatchDecorator(new ThreePlayersMatch());
            }

        if (isExpert)
            this.model = new ExpertMatchDecorator(model.getWrappedMatch());
    }

    private boolean checkOldMatches(){
        //TODO  look for saved matches
        return this.model != null;
    }

    public MatchDecorator resumeMatch(){
        if (checkOldMatches()){
            //TODO properly restore an old match
            return this.model;
            }
        else
            return null;
    }

    /**
     * Adds a new Player to the match
     * @param nickname is the nickname associated to the new Player
     * @param towerColor is the TowerColor chosen for the new Player
     * @param wizardFamily is the WizardFamily chosen for the new Player
     */
    public void addPlayer(String nickname, TowerColor towerColor, WizardFamily wizardFamily){
        model.addPlayer(new Player(nickname,towerColor,wizardFamily));
    }

    /**
     * Saves an association between a nickname and a ClientManager to make the corresponding client reachable by the server
     * @param nickname is the nickname associated with a certain client
     * @param clientManager is the ClientManager associated with a certain client
     */
    public void setClientManager(String nickname, ClientManager clientManager){
        clientManagerMap.put(nickname,clientManager);
    }

    /**
     * Returns an ArrayList of nicknames, saved in the stored match
     * @return an ArrayList of nicknames, saved in the stored match
     */
    public List<String> getNicknamesFromResumedMatch(){
        return model.getPlanningPhaseOrder().stream()
                .map(Player::getNickname).toList();
    }

    /**
     * Returns a reference to the model
     * @return a reference to the model
     */
    public MatchDecorator getModel() {
        return model;
    }

    /**
     * Updates current and previous match state
     */
    public void changeState(){
        prevState = currState;
        switch (prevState){
            case LOGIN -> {
                currState = StateType.IN_PROGRESS;
                init();
            }
            case IN_PROGRESS -> currState = StateType.END_MATCH;
        }
    }

    /**
     * At the beginning of the "in progress" state, it initialises the match
     */
    public void init(){
        model.initializeMatch();
        sendBroadcastMessage(new GenericMessage("The match has started"));
        try {
            model.refillClouds();
        } catch (TooManyStudentsException e) {
            sendBroadcastMessage(new ErrorMessage("server", "Internal error"));
            disconnectAll();
        }
        //sendBroadcastMessage(new GenericMessage("Clouds have been refilled"));
    }

    /**
     * Disconnects all clients
     */
    private void disconnectAll(){
        for (String nickname: clientManagerMap.keySet()) {
            ClientManager cm = clientManagerMap.get(nickname);
            //TODO when phil will fix everything, delete it
            synchronized (cm){
                cm.close();
            }
        }
    }

    /**
     * Sends a broadcast message
     * @param msg is the message to send in broadcast
     */
    private void sendBroadcastMessage(Message msg){
        for (String nickname: clientManagerMap.keySet()) {
            ClientManager cm = clientManagerMap.get(nickname);
            //TODO synchronization will be deleted, when the problem will be fixed on the server side
            synchronized (cm){
                cm.sendMessage(msg);
            }
        }
    }

    /**
     * It receives a message to inspect
     * @param msg is the message that needs to be inspected
     */
    public void inspectMessage(Message msg){
        //TODO complete
    }
}
