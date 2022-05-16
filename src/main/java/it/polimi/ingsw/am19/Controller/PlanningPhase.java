package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalCardOptionException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;
import java.util.ListIterator;

public class PlanningPhase extends AbstractPhase implements Phase{
    private final List<String> playersOrder;
    private final ListIterator<String> iterator;

    public PlanningPhase(List<String> playersOrder,MatchController matchController) {
        super(matchController);
        this.playersOrder = playersOrder;
        this.iterator =  playersOrder.listIterator();
    }

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
                        helperCard.getNextRoundOrder() + ", mother nature steps : " + helperCard.getMaxNumOfSteps() + "\n"));
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

    @Override
    public List<String> getPlayersOrder() {
        return playersOrder;
    }

    @Override
    public void initPhase(){
        matchController.sendBroadcastMessage(new GenericMessage("Round " + matchController.getRoundsManager().getRoundNum() + "\n"));
        matchController.sendBroadcastMessage(new GenericMessage("Planning phase has started. In this phase we will follow this order: " + playersOrder));
        try {
            model.refillClouds();
            matchController.sendBroadcastMessage(new GenericMessage("Clouds have been refilled...\n"));
        } catch (TooManyStudentsException e) {
            matchController.sendBroadcastMessage(new ErrorMessage("server", "Internal error"));
            matchController.disconnectAll();
        }
        performPhase(iterator.next());
    }

    @Override
    public void performPhase(String currPlayer) {
        matchController.setCurrPlayer(currPlayer);
        matchController.sendMessageExcept(currPlayer,new GenericMessage("It's " + currPlayer + "'s turn. Please wait your turn...\n"));
        matchController.sendMessage(currPlayer,new GenericMessage((currPlayer + " it's your turn!\n")));
        matchController.sendMessage(currPlayer, new AskHelperCardMessage(
                model.getCurrPlayer().getHelperDeck()));
    }
}
