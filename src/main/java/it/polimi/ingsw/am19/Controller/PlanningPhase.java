package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Exceptions.IllegalCardOptionException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Exceptions.UnavailableCardException;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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
        if (msg.getMessageType() == MessageType.PLAY_HELPER_CARD){
            ReplyHelperCardMessage message = (ReplyHelperCardMessage) msg;
            try {
                model.useHelperCard(message.getHelperCard());
            } catch (UnavailableCardException | IllegalCardOptionException e) {
                matchController.sendMessage(message.getNickname(),new ErrorMessage(message.getNickname(),"Invalid card choice"));

                /*matchController.sendMessage(message.getNickname(), new AskHelperCardMessage(
                        model.getCurrPlayer().getHelperDeck()));*/
            }
            if (iterator.hasNext()){
                String nextPlayer = iterator.next();
                matchController.setCurrPlayer(nextPlayer);
                performPhase(nextPlayer);
            }

            else{
                List<String> actionPhaseOrder = model.getActionPhaseOrder().stream()
                        .map(Player::getNickname)
                        .toList();
                matchController.getRoundsManager().changePhase(new PlanningPhase(actionPhaseOrder,matchController));
                matchController.getRoundsManager().getCurrPhase().initPhase();
            }
        }
    }

    @Override
    public List<String> getPlayersOrder() {
        return playersOrder;
    }

    @Override
    public void initPhase(){
        matchController.sendBroadcastMessage(new GenericMessage("Planning phase has started. In this phase we will follow this order: " + playersOrder));
        try {
            model.refillClouds();
            matchController.sendBroadcastMessage(new GenericMessage("Clouds have been refilled.."));
        } catch (TooManyStudentsException e) {
            matchController.sendBroadcastMessage(new ErrorMessage("server", "Internal error"));
            matchController.disconnectAll();
        }
        performPhase(iterator.next());
    }

    @Override
    public void performPhase(String currPlayer) {
        matchController.setCurrPlayer(currPlayer);
        matchController.sendMessageExcept(currPlayer,new GenericMessage("It's " + currPlayer + "'s turn. Please wait your turn..."));
        matchController.sendMessage(currPlayer,new GenericMessage((currPlayer + " it's your turn!")));
        matchController.sendMessage(currPlayer, new AskHelperCardMessage(
                model.getCurrPlayer().getHelperDeck()));
    }
}
