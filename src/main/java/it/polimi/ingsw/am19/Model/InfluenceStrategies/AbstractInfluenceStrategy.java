package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * abstract class to group all the methods used by the various influence strategies
 */
public abstract class AbstractInfluenceStrategy implements InfluenceStrategy, Serializable {
    /**
     * a map containing the amount of influence of each player on the island
     */
    Map<Player, Integer> influenceMap = new HashMap<>();

    /**
     * the old owner of the island
     */
    Player oldOwner = null;

    /**
     * the new owner of the island
     */
    Player newOwner = null;

    /**
     * influence value before updating it
     */
    int oldInfluenceValue;

    /**
     * the player's new amount of influence
     */
    int newInfluenceValue;

    /**
     * method to initialize the influence map with each player having no influence
     * it also find the player currently owning the island, if any
     * @param towerColor the color of the tower currently present on the island
     * @param manager the professor manager of the match
     */
    void initialize(TowerColor towerColor, ProfessorManager manager) {
        for(Player player : manager.getGameboards().keySet()) {
            influenceMap.put(player, 0);
            if(player.getTowerColor() == towerColor)
                oldOwner = player;
        }
    }

    /**
     * method to calculate the influence given to each player by the students present on the island
     * @param numOfStudents the map containing the number of students for each color
     * @param manager the professor manager of the match
     */
    void influenceFromStudent(Map<PieceColor, Integer> numOfStudents, ProfessorManager manager) {
        for(PieceColor color : numOfStudents.keySet()) {
            Player player = manager.getOwner(color);
            if(player != null) {
                oldInfluenceValue = influenceMap.get(player);
                newInfluenceValue = oldInfluenceValue + numOfStudents.get(color);
                influenceMap.put(player, newInfluenceValue);
            }
        }
    }

    /**
     * method to calculate the influence give to the player owning the island by the towers present on it
     * @param numOfIslands the number of island that makes up this group
     */
    void influenceFromTower(int numOfIslands) {
        if (oldOwner != null) {
            oldInfluenceValue = influenceMap.get(oldOwner);
            newInfluenceValue = oldInfluenceValue + numOfIslands;
            influenceMap.put(oldOwner, newInfluenceValue);
        }
    }

    /**
     * method that checks if the owner needs to be changed and changes it
     */
    void changeOwner() {
        //check if owner needs to change
        //if there is no new current owner
        if (oldOwner == null) {
            for(Player player : influenceMap.keySet()) {
                if(influenceMap.get(player) > 0) {
                    if(newOwner == null)
                        newOwner = player;
                    else {
                        if(influenceMap.get(player) > influenceMap.get(newOwner))
                            newOwner = player;
                    }
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
    }

    /**
     * method that returns the owner of the island
     * @param numOfIslands the number of island that makes up this group
     * @param manager the professor manager of the match
     * @return returns the new player owning the island, if nothing changed this method will return null
     */
    Player returnOwner(int numOfIslands, ProfessorManager manager){
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
            if(oldOwner != null){

                // but first give the towers back to the old owner
                GameBoard gameBoard = manager.getGameboards().get(oldOwner);
                for (int i = 0; i < numOfIslands; i++)
                    gameBoard.addTower();
            }
            return newOwner;
        }
    }

}
