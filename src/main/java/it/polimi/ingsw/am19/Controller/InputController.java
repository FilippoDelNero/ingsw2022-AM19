package it.polimi.ingsw.am19.Controller;

import it.polimi.ingsw.am19.Model.BoardManagement.Island;
import it.polimi.ingsw.am19.Model.CharacterCards.AbstractCharacterCard;
import it.polimi.ingsw.am19.Model.Match.ExpertMatchDecorator;
import it.polimi.ingsw.am19.Model.Match.MatchDecorator;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Network.Message.ErrorMessage;
import it.polimi.ingsw.am19.Network.Message.Message;

import java.util.List;

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
     * Checks if the given PieceColor is in the current player's entrance.
     * In case of absence, it sends an error message
     * @param color the color to look for in the entrance
     */
    /*
    boolean checkIsInEntrance(PieceColor color){
        GameBoard gameBoard = model.getGameBoards().get(model.getPlayerByNickname(matchController.getCurrPlayer()));
        if (!gameBoard.getEntrance().containsKey(color)){
            matchController.sendMessage(matchController.getCurrPlayer(), new ErrorMessage("server","You don't have" + color.name() + " in your entrance"));
            return false;
        }
        return true;
    }*/

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

    boolean checkCloudIndex(int cloudIndex){
        if (!model.getNonEmptyClouds().contains(cloudIndex)){
            matchController.sendMessage(matchController.getCurrPlayer(), new ErrorMessage("server","Invalid cloud number. Please retry\n"));
            return false;
        }
        return true;
    }

    boolean checkIsCharacterAvailable(AbstractCharacterCard character){
        if (!((ExpertMatchDecorator)model).getCharacterCards().contains(character)){
            matchController.sendMessage(matchController.getCurrPlayer(),
                    new ErrorMessage("server",character.getId() + " is not available. Please retry"));
            return false;
        }
        else
            return true;
    }

    boolean checkValidColor(PieceColor color){
        switch (color){
            case GREEN,RED,YELLOW,PINK,BLUE -> {return true;}
            default -> {return false;}
        }
    }

    boolean checkValidColor(List<PieceColor> colors){
        boolean isValid = false;
        for (PieceColor color : colors){
            switch (color){
                case GREEN,RED,YELLOW,PINK,BLUE -> {isValid = true;}
                default -> {
                    isValid = false;
                    return false;
                }
            }
        }
        return isValid;
    }

    boolean checkAffordability(AbstractCharacterCard card){
        if (card.getPrice() <= ((ExpertMatchDecorator)model).getCurrPlayer().getCoins()){
            return true;
        }
        else{
            matchController.sendMessage(matchController.getCurrPlayer(),
                    new ErrorMessage("server","You can't afford " + card.getId() + " card. Please retry\n"));
            return false;
        }
    }
}
