package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Model.Match.ThreePlayersMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.ReplyCreateMatchMessage;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.List;
import java.util.Map;

public class MatchController {
    /**
     * Keeps a reference to the game model
     */
    private MatchDecorator model;

    /**
     * Associates every nickname to the corresponding ClientManager
     */
    private Map<String, ClientManager> clientManagerMap;

    /**
     * Saves the current match state
     */
    private StateType currState;

    /**
     * Keeps memory of the previous match state
     */
    private StateType prevState;

    public MatchController(){
        this.currState = StateType.INIT;
    }

    /**
     * Given the number of players and the difficulty level, it creates a new match
     * @param msg is the message containing all the information needed to build a new match
     */
    public void createNewMatch(Message msg){
        int numOfPLayers = ((ReplyCreateMatchMessage) msg).getNumOfPlayer();
        //if (inputController.checkPlayerNum(numOfPLayers)){
            if (numOfPLayers == 2)
                this.model = new MatchDecorator(new TwoPlayersMatch());
            else if (numOfPLayers == 3){
                this.model = new MatchDecorator(new ThreePlayersMatch());
            }
        //}

        if (((ReplyCreateMatchMessage) msg).isExpert())
            this.model = new ExpertMatchDecorator(model.getWrappedMatch());
    }

    private boolean checkOldMatches(){
        //TODO  look for saved matches
        //return this.model != null;
        return false; //TODO to be removed
    }

    /**
     * If there's an old match saved, it resumes it
     * Otherwise it does nothing
     */
    public void resumeMatch(){
        if (checkOldMatches()){
            //return this.model;
            //TODO restore an old match
            }
    }

    /**
     * Adds a new Player to a match, given some info
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
}
