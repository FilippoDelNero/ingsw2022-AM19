package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Network.Message.AskHelperCardMessage;
import it.polimi.ingsw.am19.Network.Message.ErrorMessage;
import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Server.ClientManager;

import java.util.Collection;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

public class RoundsManager {
    private Phase currPhase;
    private int roundNum;
    private final int MAX_ROUND_NUM = 10;

    public RoundsManager(MatchController mc) {
    }

    public void changePhase(Phase nextPhase) {
        this.currPhase = nextPhase;
        if (this.currPhase instanceof PlanningPhase){
          roundNum++;
        }
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

    public boolean hasNextRound(){
        return roundNum < MAX_ROUND_NUM;
    }
}
