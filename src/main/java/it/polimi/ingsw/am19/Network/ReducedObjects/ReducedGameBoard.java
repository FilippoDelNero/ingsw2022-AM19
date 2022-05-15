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
        String entranceString = "Entrance: ";
        for(PieceColor p : PieceColor.values()) {
            if(entrance.get(p) != 0)
                entranceString = entranceString.concat(p + "x" + entrance.get(p) + " ");
        }
        String diningString = "Dining Room: ";
        for(PieceColor p : PieceColor.values()) {
            if(diningRoom.get(p) != 0)
                diningString = diningString.concat(p + "x" + diningRoom.get(p) + " ");
            if(professors.contains(p))
                diningString = diningString.concat("*prof* ");
        }
        if(diningString.equals("Dining Room: "))
            diningString = diningString.concat("empty");

        return "Gameboard of \033[48;1;7m" + playerNickname.toUpperCase() + "\u001B[0m:  " +
                entranceString + '\n' +
                diningString + '\n' +
                "Towers remaining: " + numOfTowers;
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
