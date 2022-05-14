package it.polimi.ingsw.am19.Network.ReducedObjects;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * record representing a gameboard
 * @param playerNickname the nickname of the player owning the gameboard
 * @param entrance the entrance of the player's gameboard
 * @param diningRoom the diningRoom of the player's gameboard
 * @param professors the professors owned by the player
 * @param numOfTowers the number of towers on the player's gameboard
 */
public record ReducedGameBoard(String playerNickname,
                               HashMap<PieceColor, Integer> entrance,
                               HashMap<PieceColor, Integer> diningRoom,
                               ArrayList<PieceColor> professors,
                               int numOfTowers) implements Serializable {

    @Override
    public String toString() {
        return "Gameboard of " + playerNickname.toUpperCase() + ":\n" +
                "entrance: " + entrance + '\n' +
                "diningRoom: " + diningRoom + '\n' +
                "professors: " + professors + '\n' +
                "numOfTowers: " + numOfTowers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedGameBoard that = (ReducedGameBoard) o;
        return numOfTowers == that.numOfTowers &&
                playerNickname.equals(that.playerNickname) &&
                entrance.equals(that.entrance) &&
                diningRoom.equals(that.diningRoom) &&
                professors.equals(that.professors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerNickname, entrance, diningRoom, professors, numOfTowers);
    }
}
