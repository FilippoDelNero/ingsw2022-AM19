package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Model.Match.ThreePlayersMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import it.polimi.ingsw.am19.Network.Message.*;
import it.polimi.ingsw.am19.Network.ReducedObjects.Reducer;
import it.polimi.ingsw.am19.Network.Server.ClientManager;
import it.polimi.ingsw.am19.Observer;
import it.polimi.ingsw.am19.Utilities.Notification;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchController implements Observer{
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
     * Is the nickname of the current player
     */
    private String currPlayer;

    /**
     * Is a reference to a RoundManager
     */
    private RoundsManager roundsManager;

    /**
     * Is a reference to an InputController
     */
    private InputController inputController;

    private final Reducer reducer;

    public MatchController(){
        this.clientManagerMap = new ConcurrentHashMap<>();
        this.currState = StateType.LOGIN;
        this.reducer = new Reducer();
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

        model.getWrappedMatch().addObserver(this);
        this.inputController = new InputController(this);
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
     * Updates current match state
     */
    public void changeState(){
        switch (currState){
            case LOGIN -> {
                currState = StateType.IN_PROGRESS;
                init();
                inProgress();
            }
            case IN_PROGRESS -> currState = StateType.END_MATCH;
            case END_MATCH -> endMatch();
        }
    }

    /**
     * At the beginning of the in progress state, it initialises the match
     */
    private void init(){
        if (model instanceof ExpertMatchDecorator)
            ((ExpertMatchDecorator)model).initializeMatch();
        else
            model.initializeMatch();
        sendBroadcastMessage(new UpdateGameBoardsMessage(reducer.reducedGameBoard(model.getGameBoards())));
        sendBroadcastMessage(new UpdateIslandsMessage(reducer.reduceIsland(model.getIslandManager().getIslands())));
        sendBroadcastMessage(new GenericMessage("The match has started\n"));
        this.roundsManager = new RoundsManager(this);
    }

    /**
     * Disconnects all clients
     */
    public void disconnectAll(){
        sendBroadcastMessage(new GenericMessage("You will be disconnected due to internal errors"));
        for (String nickname: clientManagerMap.keySet()) {
            ClientManager cm = clientManagerMap.get(nickname);
                cm.close();
        }
    }

    /**
     * Sends a broadcast message
     * @param msg is the message to send in broadcast
     */
    public void sendBroadcastMessage(Message msg){
        for (String nickname: clientManagerMap.keySet()) {
            ClientManager cm = clientManagerMap.get(nickname);
            cm.sendMessage(msg);
        }
    }

    /**
     * Sends a message to a specific client
     * @param receiver the nickname of the client
     * @param msg the message to send
     */
    public void sendMessage(String receiver,Message msg){
        clientManagerMap.get(receiver).sendMessage(msg);
    }

    /**
     * Sends a message to all clients except one
     * @param playerToExclude is the client to exclude from the communication
     * @param msg is the message to send
     */
    public void sendMessageExcept(String playerToExclude,Message msg){
        clientManagerMap.keySet().stream()
                .filter(nickname -> !nickname.equals(playerToExclude))
                .map(clientManagerMap::get)
                .forEach(client -> client.sendMessage(msg));
    }

    /**
     * It receives a message to inspect
     * @param msg is the message that needs to be inspected
     */
    public void inspectMessage(Message msg){
        roundsManager.getCurrPhase().inspectMessage(msg);
    }

    /**
     * At the beginning of a new round, it begins from the PlanningPhase
     */
    private void inProgress(){
        List<String> planningPhaseOrder = model.getPlanningPhaseOrder().stream()
                .map(Player::getNickname)
                .toList();
        roundsManager.changePhase(new PlanningPhase(planningPhaseOrder,this)); //now it's planning phase
        roundsManager.getCurrPhase().initPhase();
    }

    /**
     * Gets model's current player
     * @return model's current player
     */
    public String getCurrPlayer(){
        return model.getCurrPlayer().getNickname();
    }

    /**
     * Sets model's current player given its nickname
     * @param nickname is the nickname of the new current player
     */
    public void setCurrPlayer(String nickname){
       Player player = model.getPlayerByNickname(nickname);
       model.setCurrPlayer(player);
    }

    /**
     * After an end match condition occurred, it simulates the end of the match
     */
    private void endMatch(){
        List<String> winners = model.getWinner().stream()
                .map(Player::getNickname)
                .toList();
        sendBroadcastMessage(new EndMatchMessage(winners));
    }

    /**
     * Returns the map that binds a nickname to its corresponding ClientManager
     * @return the map that binds a nickname to its corresponding ClientManager
     */
    public Map<String, ClientManager> getClientManagerMap() {
        return clientManagerMap;
    }

    /**
     * Returns a reference to the RoundsManager
     * @return a reference to the RoundsManager
     */
    public RoundsManager getRoundsManager() {
        return roundsManager;
    }

    @Override
    public void notify(Notification notification) {
        switch (notification){
            case UPDATE_CLOUDS -> sendBroadcastMessage(
                    new UpdateCloudsMessage(reducer.reduceClouds(model.getClouds())));

            case UPDATE_ISLANDS -> sendBroadcastMessage(
                    new UpdateIslandsMessage(reducer.reduceIsland(model.getIslandManager().getIslands())));

            case UPDATE_GAMEBOARDS -> sendBroadcastMessage(
                    new UpdateGameBoardsMessage(reducer.reducedGameBoard(model.getGameBoards())));
            case END_MATCH -> changeState();
        }
    }

    /**
     * Returns a reference to the InputController
     * @return a reference to the InputController
     */
    public InputController getInputController() {
        return inputController;
    }
}
