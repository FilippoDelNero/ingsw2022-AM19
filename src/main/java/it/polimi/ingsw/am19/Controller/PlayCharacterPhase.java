package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Network.Message.*;

public class PlayCharacterPhase extends AbstractPhase implements Phase{
    private final String currPlayer;
    private Phase prevPhase;
    public PlayCharacterPhase(MatchController matchController) {
        super(matchController);
        this.currPlayer = matchController.getCurrPlayer();

    }

    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            if(msg.getMessageType() == MessageType.PLAY_CHARACTER_CARD && model instanceof ExpertMatchDecorator){
                ReplyPlayCharacterCardMessage message = (ReplyPlayCharacterCardMessage) msg;
                AbstractCharacterCard card = message.getCardToUse();
                if (card == null) //the client doesn't want to play a card. Let's go back to action phase

                inputController.checkIsCharacterAvailable(card);
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
