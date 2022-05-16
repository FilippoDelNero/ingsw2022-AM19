package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;

public class PlayCharacterPhase extends AbstractPhase implements Phase{
    private final String currPlayer;
    private AbstractCharacterCard card;
    public PlayCharacterPhase(MatchController matchController) {
        super(matchController);
        this.currPlayer = matchController.getCurrPlayer();
    }

    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            switch (msg.getMessageType()){
                case PLAY_CHARACTER_CARD -> {
                    ReplyPlayCharacterCardMessage message = (ReplyPlayCharacterCardMessage) msg;
                    this.card = message.getCardToUse();
                    if (card == null){ //the client doesn't want to play a card. Let's go back to action phase
                        Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
                        matchController.getRoundsManager().changePhase(prevPhase);
                    }
                    else{
                        if(inputController.checkIsCharacterAvailable(card) &&
                                inputController.checkAffordability(card))
                            askParameters(card);
                    }
                }

                case REPLY_CHARACTER_PARAMETER -> {
                    ReplyCharacterParameterMessage message = (ReplyCharacterParameterMessage) msg;
                    PieceColor color = message.getColor();
                    int islandIndex = message.getIsland();
                    Island island = model.getIslandManager().getIslands().get(islandIndex);
                    List<PieceColor> colorList = message.getColorList();
                    if (inputController.checkIsInArchipelago(islandIndex) &&
                            inputController.checkValidColor(color) &&
                            inputController.checkValidColor(colorList)) {
                        try {
                            ((ExpertMatchDecorator)model).playCard(card,color,island,colorList);
                            Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
                            matchController.getRoundsManager().changePhase(prevPhase);
                        } catch (InsufficientCoinException e) {
                            Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
                            matchController.getRoundsManager().changePhase(prevPhase);
                        } catch (NoSuchColorException | TooManyStudentsException e) {
                            matchController.sendMessage(currPlayer,new ErrorMessage("server","Invalid parameters"));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void performPhase(String currPlayer) {

    }

    @Override
    public void initPhase() {

    }

    private void askParameters(AbstractCharacterCard card){
        boolean requireColor = false, requireIsland = false, requireListColor = false;
        if (card.isRequiringIsland())
            requireIsland = true;
        if (card.isRequiringPieceColor())
            requireColor = true;
        if (card.isRequiringPieceColorList())
            requireListColor = true;
        matchController.sendMessage(currPlayer,
                new AskCharacterParameterMessage(requireColor,requireIsland, requireListColor));
    }
}
