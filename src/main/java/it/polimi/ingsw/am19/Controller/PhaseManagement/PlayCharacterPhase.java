package it.polimi.ingsw.am19.Controller.PhaseManagement;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Controller.PhaseManagement.AbstractPhase;
import it.polimi.ingsw.am19.Controller.PhaseManagement.ActionPhase;
import it.polimi.ingsw.am19.Controller.PhaseManagement.Phase;
import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.CharacterCards.*;
import it.polimi.ingsw.am19.Model.CharacterCards.Character;
import it.polimi.ingsw.am19.Model.Exceptions.InsufficientCoinException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.*;

import java.util.List;

/**
 * A Class that manages playing a character card into an expert match
 */
public class PlayCharacterPhase extends AbstractPhase implements Phase {
    private final String currPlayer;
    private AbstractCharacterCard  card;
    public PlayCharacterPhase(MatchController matchController) {
        super(matchController);
        this.currPlayer = matchController.getCurrPlayer();
    }

    /**
     * Inspects messages passed as argument, but only if they come from the current player and
     * only if their type is between those expected
     * @param msg  message passed from MatchController class
     */
    @Override
    public void inspectMessage(Message msg) {
        if (inputController.checkSender(msg)) {
            switch (msg.getMessageType()){
                case PLAY_CHARACTER_CARD -> playCharacterCard((ReplyPlayCharacterCardMessage) msg);

                case REPLY_CHARACTER_PARAMETER -> activateCardEffect((ReplyCharacterParameterMessage) msg);
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


    private void playCharacterCard(ReplyPlayCharacterCardMessage message){
        if (message.getCardToUse() == null)//the client doesn't want to play a card. Let's go back to action phase
            goBackToPrevPhase();
        else{
            card = getCharacterById(message.getCardToUse());
            askForParameters();
        }
    }

    private void goBackToPrevPhase(){
        Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
        matchController.getRoundsManager().changePhase(prevPhase);

        switch (((ActionPhase)prevPhase).getCurrStep()){
            case MOVE_STUD ->
                matchController.sendMessage(currPlayer,new AskEntranceMoveMessage(((ActionPhase)prevPhase).getMAX_NUM_STUDENTS()));
            case MOVE_MN ->
                matchController.sendMessage(currPlayer,new AskMotherNatureStepMessage());
            case TAKE_STUD ->
                matchController.sendMessage(currPlayer, new AskCloudMessage(model.getNonEmptyClouds()));
        }
    }

    private void askForParameters(){
        if (inputController.checkIsCharacterAvailable(card) &&
                inputController.checkAffordability(card))
            askParameters();
        else
            card = null;
    }

    private AbstractCharacterCard getCharacterById(Character id){
       for (AbstractCharacterCard characterCard : ((ExpertMatchDecorator)model).getCharacterCards()){
           if (characterCard.getId() == id)
               return characterCard;
       }
       return null;
    }

    private void activateCardEffect(ReplyCharacterParameterMessage message){
        PieceColor color = message.getColor();
        Integer islandIndex = message.getIsland();
        Island island = null;
        if (islandIndex != null)
            island = model.getIslandManager().getIslands().get(islandIndex);
        List<PieceColor> colorList = message.getColorList();

            try {
                ((ExpertMatchDecorator)model).playCard(card,color,island,colorList);
                ((ActionPhase)matchController.getRoundsManager().getPrevPhase()).setCardPlayed(true);
                goBackToPrevPhase();
            } catch (InsufficientCoinException e) {
                Phase prevPhase = matchController.getRoundsManager().getPrevPhase();
                matchController.getRoundsManager().changePhase(prevPhase);
            } catch (NoSuchColorException | TooManyStudentsException e) {
                matchController.sendMessage(currPlayer,new ErrorMessage("server","Invalid parameters"));
            }
    }
}
