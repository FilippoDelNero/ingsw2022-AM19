package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Cloud;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalNumOfStepsException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;
import java.util.ListIterator;

public class ActionPhase extends AbstractPhase implements Phase{
    private final List<String> playersOrder;
    private final ListIterator<String> iterator;
    private int numOfMovedStudents = 0;
    private final int MAX_NUM_STUDENTS;

    public ActionPhase(List<String> playersOrder,MatchController matchController) {
        super(matchController);
        this.playersOrder = playersOrder;
        this.iterator = playersOrder.listIterator();
        this.MAX_NUM_STUDENTS = matchController.getModel().getClouds().get(0).getNumOfHostableStudents();
    }

    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            switch (msg.getMessageType()) {
                case ENTRANCE_TO_DINING_ROOM -> {
                    ReplyEntranceToDiningRoomMessage message = (ReplyEntranceToDiningRoomMessage) msg;
                    entranceToDiningRooom(message);
                }

                case ENTRANCE_TO_ISLAND -> {
                    ReplyEntranceToIslandMessage message = (ReplyEntranceToIslandMessage) msg;
                    entranceToIsland(message);
                }
                case MN_STEP -> {
                    ReplyMotherNatureStepMessage message = (ReplyMotherNatureStepMessage) msg;
                    motherNatureSteps(message);
                }

                case CHOSEN_CLOUD -> {
                    ReplyCloudMessage message = (ReplyCloudMessage) msg;
                    takeCloud(message);
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
        matchController.setCurrPlayer(currPlayer);
        matchController.sendMessageExcept(currPlayer,new GenericMessage("It's " + currPlayer + "'s turn. Please wait your turn...\n"));
        matchController.sendMessage(currPlayer,new GenericMessage((currPlayer + " it's your turn!\n")));
        matchController.sendMessage(currPlayer, new AskEntranceMoveMessage());
    }

    @Override
    public void initPhase() {
        String currPlayer = iterator.next();
        matchController.sendBroadcastMessage(new GenericMessage("Action phase has started. In this phase we will follow this order:" + playersOrder+ "\n"));
        performPhase(currPlayer);
    }

    private void entranceToDiningRooom(ReplyEntranceToDiningRoomMessage message){
        PieceColor color = message.getColorChosen();
        //if (inputController.checkIsInEntrance(color)) {
        try {
            model.moveStudentToDiningRoom(color);
        } catch (NoSuchColorException | TooManyStudentsException e) {
            matchController.sendMessage(matchController.getCurrPlayer(),
                    new ErrorMessage("server","You can't move a " + color + " student to your dining room. Please retry\n"));
            return;
        }
        numOfMovedStudents++;
        if (numOfMovedStudents < MAX_NUM_STUDENTS)
            matchController.sendMessage(matchController.getCurrPlayer(), new AskEntranceMoveMessage());
        else if (numOfMovedStudents == MAX_NUM_STUDENTS) {
            matchController.sendMessage(matchController.getCurrPlayer(), new AskMotherNatureStepMessage());
        }
        //}
    }

    private void entranceToIsland(ReplyEntranceToIslandMessage message){
        PieceColor color = message.getColorChosen();
        int islandIndex = message.getIsland();
        if (//inputController.checkIsInEntrance(color) &&
                inputController.checkIsInArchipelago(islandIndex)){
            try {
                model.moveStudent(color,model.getGameBoards().get(
                        model.getPlayerByNickname(matchController.getCurrPlayer())
                ), model.getIslandManager().getIslands().get(islandIndex));
            } catch (NoSuchColorException | TooManyStudentsException e) {
                matchController.sendMessage(matchController.getCurrPlayer(),
                        new ErrorMessage("server","You can't move a " + color + " student to island "+ islandIndex+1+ ". Please retry\n"));
                return;
            }
            numOfMovedStudents++;
            if (numOfMovedStudents < MAX_NUM_STUDENTS)
                matchController.sendMessage(matchController.getCurrPlayer(), new AskEntranceMoveMessage());
            else if (numOfMovedStudents == MAX_NUM_STUDENTS) {
                matchController.sendMessage(matchController.getCurrPlayer(), new AskMotherNatureStepMessage());
            }
        }
    }

    private void motherNatureSteps(ReplyMotherNatureStepMessage message){
        numOfMovedStudents = 0;
        try {
            model.moveMotherNature(message.getStep());
        } catch (IllegalNumOfStepsException e) {
            matchController.sendMessage(matchController.getCurrPlayer(),
                    new ErrorMessage("server","Yuo can't move mother nature of " + message.getStep() + " steps. Please retry \n"));
            return;
        }
        matchController.sendMessage(matchController.getCurrPlayer(), new AskCloudMessage(model.getNonEmptyClouds()));
    }

    private void takeCloud(ReplyCloudMessage message){
        int cloudIndex = message.getCloudChosen();
        if (inputController.checkCloudIndex(cloudIndex)){
            Cloud cloud = model.getClouds().get(cloudIndex);
            GameBoard gameBoard = model.getGameBoards().get(model.getCurrPlayer());
            for (PieceColor color: PieceColor.values()){
                while (cloud.getStudents().get(color) != 0) {
                    try {
                        model.moveStudent(color,cloud,gameBoard);
                    } catch (NoSuchColorException | TooManyStudentsException e) {
                        matchController.disconnectAll();
                        return;
                    }
                }
            }
        }
        if (iterator.hasNext()) {
            String nextPlayer = iterator.next();
            matchController.setCurrPlayer(nextPlayer);
            performPhase(nextPlayer);
        } else {
            //TODO salvataggio partita
            model.resetAlreadyPlayedCards();
            model.updatePlanningPhaseOrder();
            if (matchController.getRoundsManager().hasNextRound() && !model.isFinalRound()) {
                List<String> planningPhaseOrder = model.getPlanningPhaseOrder().stream()
                        .map(Player::getNickname)
                        .toList();
                matchController.getRoundsManager().changePhase(new PlanningPhase(planningPhaseOrder, matchController));
                matchController.getRoundsManager().getCurrPhase().initPhase();
            }
            else
                matchController.changeState();
        }
    }
}
