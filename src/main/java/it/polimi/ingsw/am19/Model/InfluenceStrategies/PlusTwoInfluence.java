package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
//TODO MAYBE WE SHOULD CONDESATE THE COMMON CODE INTO A ABSTRACT CLASS
public class PlusTwoInfluence implements InfluenceStrategy {
    private Player currentPlayer;

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    @Override
    public Player calculateInfluence(Map<PieceColor, Integer> numOfStudents, TowerColor towerColor, int numOfIslands, ProfessorManager manager) {
        //create a map with each player's influence on the island
        Map<Player, Integer> influenceMap = new HashMap<>();
        Player oldOwner = null;
        Player newOwner = null;
        int oldInfluenceValue;
        int newInfluenceValue;

        //initialize the map to zero and find the current player owning the island
        for(Player player : manager.getGameboards().keySet()) {
            influenceMap.put(player, 0);
            if(player.getTowerColor() == towerColor)
                oldOwner = player;
        }

        //calculate influence from student
        for(PieceColor color : numOfStudents.keySet()) {
            Player player = manager.getOwner(color);
            oldInfluenceValue = influenceMap.get(player);
            newInfluenceValue = oldInfluenceValue + numOfStudents.get(color);
            influenceMap.put(player, newInfluenceValue);
        }

        //calculate the influence from the tower
        if (oldOwner != null) {
            oldInfluenceValue = influenceMap.get(oldOwner);
            newInfluenceValue = oldInfluenceValue + numOfIslands;
            influenceMap.put(oldOwner, newInfluenceValue);
        }

        //add the extra influence to the player that played the card
        oldInfluenceValue = influenceMap.get(currentPlayer);
        newInfluenceValue = oldInfluenceValue + 2;
        influenceMap.put(currentPlayer, newInfluenceValue);

        //check if owner needs to change
        //if there is no new current owner
        if (oldOwner == null) {
            for(Player player : influenceMap.keySet()) {
                //if there is no new owner yet assign one
                if(newOwner == null) {
                    newOwner = player;
                }
                //check if the assigned owner is the one with more influence
                else {
                    if(influenceMap.get(player) > influenceMap.get(newOwner))
                        newOwner = player;
                }
            }
        }
        //if the island is already owned by a player
        else {
            for(Player player : influenceMap.keySet()) {
                if(influenceMap.get(player) > influenceMap.get(oldOwner))
                    newOwner = player;
            }
            if(newOwner == null) //if the owner hasn't change set the new one to the old one
                newOwner = oldOwner;
        }

        //return the owner, if no changes than return null
        //if the island ownership hasn't changed do nothing and return null
        if(newOwner == oldOwner) {
            return null;
        }
        else {
            //if two players not owning the island have the same influence the ownership does not change, return null
            for(Player player : influenceMap.keySet()) {
                if(player != newOwner && Objects.equals(influenceMap.get(player), influenceMap.get(newOwner)))
                    return null;
            }
            //if there is a player with a clear majority the island changes ownership, return the new owner
            if(oldOwner != null)
                manager.getGameboards().get(oldOwner).setNumOfTowers(+numOfIslands); //but first give the towers back to the old owner
            return newOwner;
        }
    }
}
