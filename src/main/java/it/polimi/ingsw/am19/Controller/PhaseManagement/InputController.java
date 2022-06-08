package it.polimi.ingsw.am19.Controller.PhaseManagement;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Network.Message.ErrorMessage;
import it.polimi.ingsw.am19.Network.Message.Message;

/**
 * Helper Class for making some checks server side
 */
public class InputController {
    private final MatchDecorator model;
    MatchController matchController;

    public InputController(MatchController mc) {
        this.model = mc.getModel();
        this.matchController = mc;
    }

    /**
     * Checks if the incoming message is from the current player
     * @param msg is the incoming message
     * @return true if the incoming message is from the current player, false otherwise
     */
    boolean checkSender(Message msg) {
        return matchController.getCurrPlayer().equals(msg.getNickname());
    }

    /**
     * Given an island index, it checks whether the corresponding Island is part of the archipelago
     * @param islandIndex it is the island index that needs to be checked
     * @return true if the island index is that of an Island that takes part into the archipelago, false otherwise
     */
    boolean checkIsInArchipelago(int islandIndex){
        if (islandIndex < 0 || islandIndex > model.getIslandManager().getIslands().size()-1){
            matchController.sendMessage(matchController.getCurrPlayer(), new ErrorMessage("server","The island index you chose is not valid. Please retry\n"));
            return false;
        }
        return true;
    }

    /**
     * Given a Cloud index, returns true if its index corresponds to an Island currently in the archipelago
     * @param cloudIndex the CLoud index to check
     * @return true if its index corresponds to an Island currently in the archipelago
     */
    boolean checkCloudIndex(int cloudIndex){
        if (!model.getNonEmptyClouds().contains(cloudIndex)){
            matchController.sendMessage(matchController.getCurrPlayer(), new ErrorMessage("server","Invalid cloud number. Please retry\n"));
            return false;
        }
        return true;
    }

    /**
     * Given a character card, it returns true if is one of those available, false otherwise
     * @param character is the character card the needs to be checked
     * @return true if is one of those available, false otherwise
     */
    boolean checkIsCharacterAvailable(AbstractCharacterCard character){
        if (!((ExpertMatchDecorator)model).getCharacterCards().contains(character)){
            matchController.sendMessage(matchController.getCurrPlayer(),
                    new ErrorMessage("server",character.getId() + " is not available. Please retry"));
            return false;
        }
        else
            return true;
    }

    /**
     * Given a card, it returns true if the current player can afford it, false otherwise
     * @param card is the character card the needs to be checked
     * @return true if the current player can afford it, false otherwise
     */
    boolean checkAffordability(AbstractCharacterCard card){
        if (card.getPrice() <= model.getCurrPlayer().getCoins()){
            return true;
        }
        else{
            matchController.sendMessage(matchController.getCurrPlayer(),
                    new ErrorMessage("server","You can't afford " + card.getId() + " card. Please retry\n"));
            return false;
        }
    }
}
