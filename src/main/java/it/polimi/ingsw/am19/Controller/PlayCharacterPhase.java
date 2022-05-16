package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Network.Message.AskHelperCardMessage;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.MessageType;
import it.polimi.ingsw.am19.Network.Message.PlayCharacterCardMessage;

import java.util.List;

public class PlayCharacterPhase extends AbstractPhase implements Phase{
    private final String currPlayer;
    public PlayCharacterPhase(MatchController matchController) {
        super(matchController);
        this.currPlayer = matchController.getCurrPlayer();
    }

    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            if(msg.getMessageType() == MessageType.PLAY_CHARACTER_CARD && model instanceof ExpertMatchDecorator){
                // message = () msg;
                //inputController.checkIsCharacterAvailable(message.get)
                askParameters();
            }
        }
    }

    @Override
    public void performPhase(String currPlayer) {

    }

    @Override
    public void initPhase() {

    }

    private void askParameters(){

    }
}
