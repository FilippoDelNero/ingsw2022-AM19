package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.Map;

public class RoundsManager {
    private Phase currPhase;
    private int roundNum;
    private final int MAX_ROUND_NUM = 10;
    private final Map<String, ClientManager> clientManagerMap;

    public RoundsManager(Map<String, ClientManager> clientManagerMap, MatchDecorator model) {
        this.clientManagerMap = clientManagerMap;
        this.roundNum = 0;
    }

    public void changePhase(Phase nextPhase) {
        this.currPhase = nextPhase;
        if (this.currPhase instanceof PlanningPhase)
          roundNum++;
        init();
    }

    public Phase getCurrPhase() {
        return currPhase;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public int getMAX_ROUND_NUM() {
        return MAX_ROUND_NUM;
    }

    public void init(){
        sendBroadcastMessage(new GenericMessage("Round " + roundNum));
        sendBroadcastMessage(new GenericMessage("Planning phase has started. In this phase we will follow this order: " + currPhase.getPlayersOrder()));
        for (String nickname: currPhase.getPlayersOrder()){
            sendMessageExcept(nickname,new GenericMessage("It's " + nickname + "'s turn. Please wait your turn..."));
            sendMessage(nickname,new GenericMessage((nickname + " it's your turn!")));
        }
    }

    public boolean hasNextRound(){
        return roundNum < MAX_ROUND_NUM;
    }
    private void sendMessage(String receiver,Message msg){
        clientManagerMap.get(receiver).sendMessage(msg);
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

    private void sendMessageExcept(String playerToExclude,Message msg){
        clientManagerMap.keySet().stream()
                .filter(nickname -> !nickname.equals(playerToExclude))
                .map(clientManagerMap::get)
                .forEach(client -> client.sendMessage(msg));
    }
}

