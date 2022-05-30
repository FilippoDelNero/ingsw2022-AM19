package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalCardOptionException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;
import java.util.ListIterator;

/**
 * A Class for managing planning phase lifecycle
 */
public class PlanningPhase extends AbstractPhase implements Phase{
    /**
     * It contains all players' nicknames, in the order in which they will perform this phase
     */
    private final List<String> playersOrder;

    /**
     * An iterator for iterating players' nicknames
     */
    private final ListIterator<String> iterator;

    public PlanningPhase(List<String> playersOrder,MatchController matchController) {
        super(matchController);
        this.playersOrder = playersOrder;
        this.iterator =  playersOrder.listIterator();
    }

    /**
     * Inspects messages sent from the client and reacts to them only if they come from the current player
     * and only if the message type is within those expected for this phase
     * @param msg  message passed from MatchController class
     */
    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            if (msg.getMessageType() == MessageType.PLAY_HELPER_CARD) {
                ReplyHelperCardMessage message = (ReplyHelperCardMessage) msg;
                HelperCard helperCard = message.getHelperCard();
                try {
                    model.useHelperCard(helperCard);
                } catch (UnavailableCardException | IllegalCardOptionException e) {
                    matchController.sendMessage(message.getNickname(), new ErrorMessage(message.getNickname(), "Invalid card choice. Please retry\n"));
                    return;
                }
                matchController.sendMessageExcept(matchController.getCurrPlayer(), new GenericMessage(matchController.getCurrPlayer() + " played card number: " +
                        helperCard.getNextRoundOrder() + ", mother nature steps : " + helperCard.getMaxNumOfSteps() + "\n", MessageType.GENERIC_MESSAGE));
                if (iterator.hasNext()) {
                    String nextPlayer = iterator.next();
                    matchController.setCurrPlayer(nextPlayer);
                    performPhase(nextPlayer);
                } else {
                    List<String> actionPhaseOrder = model.getActionPhaseOrder().stream()
                            .map(Player::getNickname)
                            .toList();
                    matchController.getRoundsManager().changePhase(new ActionPhase(actionPhaseOrder, matchController));
                    matchController.getRoundsManager().getCurrPhase().initPhase();
                }
            }
        }
    }

    /**
     * Returns the players' order for this phase
     * @return the players' order for this phase
     */
    public List<String> getPlayersOrder() {
        return playersOrder;
    }

    /**
     * Initializes planning phase:
     * notifies clients about the current round number,
     * refills clouds,
     * picks up the first player and make it perform his phase
     */
    @Override
    public void initPhase(){
        matchController.getRoundsManager().incrementPhaseNum();
        matchController.sendBroadcastMessage(new GenericMessage("Round " + matchController.getRoundsManager().getRoundNum() + "\n", MessageType.GENERIC_MESSAGE));
        matchController.sendBroadcastMessage(new GenericMessage("Planning phase has started. In this phase we will follow this order: " + playersOrder, MessageType.GENERIC_MESSAGE));
        try {
            model.refillClouds();
            matchController.sendBroadcastMessage(new GenericMessage("Clouds have been refilled...\n", MessageType.GENERIC_MESSAGE));
        } catch (TooManyStudentsException e) {
            matchController.sendBroadcastMessage(new ErrorMessage("server", "Internal error"));
            matchController.disconnectAll();
        }
        performPhase(iterator.next());
    }

    /**
     * Performs the planning phase of a specific player
     * @param currPlayer is the nickname of the player that needs to perform this phase
     */
    @Override
    public void performPhase(String currPlayer) {
        matchController.setCurrPlayer(currPlayer);
        matchController.sendMessageExcept(currPlayer,new GenericMessage("It's " + currPlayer + "'s turn. Please wait your turn...\n", MessageType.GENERIC_MESSAGE));
        matchController.sendMessage(currPlayer,new GenericMessage((currPlayer + " it's your turn!\n"), MessageType.GENERIC_MESSAGE));
        matchController.sendMessage(currPlayer, new AskHelperCardMessage(
                model.getCurrPlayer().getHelperDeck()));
    }
}
