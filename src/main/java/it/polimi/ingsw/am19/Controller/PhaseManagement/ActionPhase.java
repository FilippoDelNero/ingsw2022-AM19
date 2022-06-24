package it.polimi.ingsw.am19.Controller.PhaseManagement;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Model.BoardManagement.Cloud;
import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.MoveStudent;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalNumOfStepsException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;
import java.util.ListIterator;

/**
 * A Class for managing action phase lifecycle
 */
public class ActionPhase extends AbstractPhase implements Phase {
    /**
     * Is the list of player's nicknames ordered according the action phase order
     */
    private final List<String> playersOrder;

    /**
     * An playersIterator for going through clients list
     */
    private final ListIterator<String> playersIterator;

    /**
     * It keeps trace of the number of students already moved by a player
     */
    private int numOfMovedStudents = 0;

    /**
     * It's the maximum number of students that a player can move. It changes according to the match type
     */
    private final int MAX_NUM_STUDENTS;

    /**
     * It keeps trace of the current step type
     */
    private ActionPhaseSteps currStep;

    /**
     * Is true if thr player currently performing this phase has already played a character card in this round.
     * It is taken into account only if the match in progress is an expert one
     */
    private boolean hasPlayedCard;

    public ActionPhase(List<String> playersOrder, MatchController matchController) {
        super(matchController);
        this.playersOrder = playersOrder;
        this.playersIterator = playersOrder.listIterator();
        this.currStep = ActionPhaseSteps.MOVE_STUD;
        this.MAX_NUM_STUDENTS = matchController.getModel().getClouds().get(0).getNumOfHostableStudents();
    }

    /**
     * Inspects messages passed as argument, but only if they come from the current player and
     * only if their type is between those expected
     * @param msg  message passed from MatchController class
     */
    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            switch (msg.getMessageType()) {
                case ENTRANCE_TO_DINING_ROOM -> entranceToDiningRoom((ReplyEntranceToDiningRoomMessage) msg);

                case ENTRANCE_TO_ISLAND -> entranceToIsland((ReplyEntranceToIslandMessage) msg);

                case MN_STEP -> motherNatureSteps((ReplyMotherNatureStepMessage) msg);

                case CHOSEN_CLOUD -> takeCloud((ReplyCloudMessage) msg);
            }
        }
    }

    /**
     * Updates the hasPlayedCard attribute according to the boolean passed as parameter
     * @param hasPlayedCard true if the card has been played, false otherwise
     */
    public void setHasPlayedCard(boolean hasPlayedCard) {
        this.hasPlayedCard = hasPlayedCard;
    }

    /**
     * Returns true if a card was already played in the actionPhase of the current player, false otherwise
     * @return true if a card was already played in the actionPhase of the current player, false otherwise
     */
    public boolean getHasPlayedCard() {
        return hasPlayedCard;
    }

    /**
     * Returns the current action phase step
     * @return the current action phase step
     */
    public ActionPhaseSteps getCurrStep() {
        return currStep;
    }

    /**
     * Return the maximum number of students a player can move
     * @return the maximum number of students a player can move
     */
    public int getMAX_NUM_STUDENTS() {
        return MAX_NUM_STUDENTS;
    }

    /**
     * Performs action phase for the player passed as parameter
     * @param currPlayer is the nickname of the player that needs to perform the action phase
     */
    @Override
    public void performPhase(String currPlayer) {
        this.hasPlayedCard = false;
        matchController.setCurrPlayer(currPlayer);
        matchController.sendMessageExcept(currPlayer,new GenericMessage("It's " + currPlayer + "'s turn. Please wait your turn...\n", MessageType.GENERIC_MESSAGE));
        matchController.sendMessage(currPlayer,new GenericMessage((currPlayer + " it's your turn!\n"), MessageType.GENERIC_MESSAGE));

        if (model instanceof ExpertMatchDecorator && !hasPlayedCard)
            sendCharacterRequest();
        else
            matchController.sendMessage(currPlayer,
                    new AskEntranceMoveMessage(MAX_NUM_STUDENTS - numOfMovedStudents));
    }

    /**
     * Initializes action phase and makes the first player in order perform his action phase
     */
    @Override
    public void initPhase() {
        String currPlayer = playersIterator.next();
        String msgContent = "Action phase has started. In this phase we will follow this order:" + playersOrder+ "\n";
        matchController.sendBroadcastMessage(new GenericMessage(msgContent, MessageType.START_ACTION_MESSAGE));
        performPhase(currPlayer);
    }

    private void entranceToDiningRoom(ReplyEntranceToDiningRoomMessage message){
        String currPlayer = matchController.getCurrPlayer();
        PieceColor color = message.getColorChosen();
        try {
            model.moveStudentToDiningRoom(color);
        } catch (NoSuchColorException | TooManyStudentsException e) {
            matchController.sendMessage(currPlayer,
                    new ErrorMessage("server","You can't move a "
                            + color + " student to your dining room. Please retry\n"));
            return;
        }
        numOfMovedStudents++;
        if (!allStudMoved())
            matchController.sendMessage(currPlayer,
                    new AskEntranceMoveMessage(MAX_NUM_STUDENTS - numOfMovedStudents));
        else {
            changeActionStep();
            if (model instanceof ExpertMatchDecorator && !hasPlayedCard){
                sendCharacterRequest();
            } else
                matchController.sendMessage(currPlayer, new AskMotherNatureStepMessage());
        }
    }

    private void entranceToIsland(ReplyEntranceToIslandMessage message){
        String currPlayer = matchController.getCurrPlayer();
        PieceColor color = message.getColorChosen();
        int islandIndex = message.getIsland();

        MoveStudent entrance = model.getGameBoards().get(
                model.getPlayerByNickname(currPlayer));
        MoveStudent island = model.getIslandManager().getIslands().get(islandIndex);

        if (inputController.checkIsInArchipelago(islandIndex)){
            try {
                model.moveStudent(color, entrance, island);
            } catch (NoSuchColorException | TooManyStudentsException e) {
                matchController.sendMessage(currPlayer,
                        new ErrorMessage("server","You can't move a " + color + " student to island "+ (islandIndex+1)+ ". Please retry\n"));
                return;
            }

            numOfMovedStudents++;

            if (!allStudMoved())
                matchController.sendMessage(currPlayer,
                        new AskEntranceMoveMessage(MAX_NUM_STUDENTS - numOfMovedStudents));
            else {
                changeActionStep();
                if (model instanceof ExpertMatchDecorator && !hasPlayedCard){
                    sendCharacterRequest();
                } else
                    matchController.sendMessage(currPlayer, new AskMotherNatureStepMessage());
            }
        }
    }

    private void motherNatureSteps(ReplyMotherNatureStepMessage message){
        String currPlayer = matchController.getCurrPlayer();
        numOfMovedStudents = 0;
        try {
            model.moveMotherNature(message.getStep());
            changeActionStep();
            if (model instanceof ExpertMatchDecorator && !hasPlayedCard)
                sendCharacterRequest();
            else
                matchController.sendMessage(currPlayer, new AskCloudMessage(model.getNonEmptyClouds()));
        } catch (IllegalNumOfStepsException e) {
            matchController.sendMessage(currPlayer,
                    new ErrorMessage("server","You can't move mother nature of "
                            + message.getStep() + " steps. Please retry\n"));
        }
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
        if (playersIterator.hasNext()) //another player needs to perform the same phase
            pickNextPlayer();
        else { //no players left for this phase
            model.resetAlreadyPlayedCards();
            model.updatePlanningPhaseOrder();
            matchController.saveMatch();

            //if there's another round to play and the current round was not the last one, restart from planning phase
            if (matchController.getRoundsManager().hasNextRound() && !model.isFinalRound())
                playAnotherRound();
            else
                matchController.changeState(); // transition to end match state
        }
    }

    private void changeActionStep() {
        switch (currStep){
            case MOVE_STUD -> currStep = ActionPhaseSteps.MOVE_MN;
            case MOVE_MN -> currStep = ActionPhaseSteps.TAKE_STUD;
        }
    }

    private boolean allStudMoved(){
        return numOfMovedStudents >= MAX_NUM_STUDENTS;
    }

    private void sendCharacterRequest(){
        matchController.getRoundsManager().changePhase(new PlayCharacterPhase(matchController));
        List<AbstractCharacterCard> charactersAvailable = ((ExpertMatchDecorator) model).getCharacterCards();
        matchController.sendMessage(matchController.getCurrPlayer(),
                new AskPlayCharacterCardMessage(charactersAvailable));
    }

    private void pickNextPlayer(){
        String nextPlayer = playersIterator.next();
        matchController.setCurrPlayer(nextPlayer);
        hasPlayedCard = false;
        currStep = ActionPhaseSteps.MOVE_STUD;
        performPhase(nextPlayer);
    }

    private void playAnotherRound(){
            List<String> planningPhaseOrder = model.getPlanningPhaseOrder().stream()
                    .map(Player::getNickname)
                    .toList();
            matchController.getRoundsManager().changePhase(new PlanningPhase(planningPhaseOrder, matchController));
            matchController.getRoundsManager().getCurrPhase().initPhase();
    }
}
