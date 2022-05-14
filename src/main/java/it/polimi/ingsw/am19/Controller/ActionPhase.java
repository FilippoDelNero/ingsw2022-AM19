package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;
import java.util.ListIterator;

public class ActionPhase extends AbstractPhase implements Phase{
    private final List<String> playersOrder;
    private final ListIterator<String> iterator;
    private int numOfMovedStudents = 0;

    public ActionPhase(List<String> playersOrder,MatchController matchController) {
        super(matchController);
        this.playersOrder = playersOrder;
        this.iterator = playersOrder.listIterator();
    }

    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            switch (msg.getMessageType()) {
                case ENTRANCE_TO_DINING_ROOM -> {
                    ReplyEntranceToDiningRoomMessage message = (ReplyEntranceToDiningRoomMessage) msg;
                    PieceColor color = message.getColorChosen();
                    if (inputController.checkIsInEntrance(color)) {
                        numOfMovedStudents++;
                        if (numOfMovedStudents < 3)
                            matchController.sendMessage(matchController.getCurrPlayer(), new AskEntranceMoveMessage());
                        else if (numOfMovedStudents == 3) {
                            matchController.sendMessage(matchController.getCurrPlayer(), new AskMotherNatureStepMessage());
                        }
                    }
                }

                case ENTRANCE_TO_ISLAND -> {
                    ReplyEntranceToIslandMessage message = (ReplyEntranceToIslandMessage) msg;
                    PieceColor color = message.getColorChosen();
                    int islandIndex = message.getIsland();
                    if (inputController.checkIsInEntrance(color) && inputController.checkIsInArchipelago(islandIndex)){
                        numOfMovedStudents++;
                        if (numOfMovedStudents < 3)
                            matchController.sendMessage(matchController.getCurrPlayer(), new AskEntranceMoveMessage());
                        else if (numOfMovedStudents == 3) {
                            matchController.sendMessage(matchController.getCurrPlayer(), new AskMotherNatureStepMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> getPlayersOrder() {
        return this.playersOrder;
    }

    @Override
    public void performPhase(String currPlayer) {

    }

    @Override
    public void initPhase() {
        String currPlayer = iterator.next();
        matchController.setCurrPlayer(currPlayer);
        matchController.sendBroadcastMessage(new GenericMessage("Action phase has started. In this phase we will follow this order:" + playersOrder));
        matchController.sendMessageExcept(currPlayer,new GenericMessage("It's " + currPlayer + "'s turn. Please wait your turn..."));
        matchController.sendMessage(currPlayer,new GenericMessage((currPlayer + " it's your turn!")));
        matchController.sendMessage(currPlayer, new AskEntranceMoveMessage());
    }

}
