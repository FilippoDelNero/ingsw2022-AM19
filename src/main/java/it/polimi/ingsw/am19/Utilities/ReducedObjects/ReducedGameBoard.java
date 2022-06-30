package it.polimi.ingsw.am19.Utilities.ReducedObjects;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

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
                               int numOfTowers,
                               TowerColor TowerColor,
                               Integer coins) implements Serializable {

    private String pieceColorToANSI(PieceColor color) {
       return switch (color) {
           case RED -> "\u001B[31m";
           case GREEN -> "\u001B[32m";
           case BLUE -> "\u001B[34m";
           case YELLOW -> "\u001B[33m";
           case PINK -> "\033[38;5;206m ";
       };
    }

    @Override
    public String toString() {
        String returnString;

        String entranceString = "Entrance: ";
        for(PieceColor p : PieceColor.values()) {
            if(entrance.get(p) != 0)
                entranceString = entranceString.concat(pieceColorToANSI(p) + p + "x" + entrance.get(p) + "\u001B[0m ");
        }

        String diningString = "Dining Room: ";

        for(PieceColor p : PieceColor.values()) {
            if(diningRoom.get(p) != 0)
                diningString = diningString.concat(pieceColorToANSI(p) + p + "x" + diningRoom.get(p) + " ");
            if(professors.contains(p))
                diningString = diningString.concat("*prof* ");
            diningString = diningString.concat("\u001B[0m");
        }

        if(diningString.equals("Dining Room: "))
            diningString = diningString.concat("empty");

        returnString = "Gameboard of \033[48;1;7m" + playerNickname.toUpperCase() + "\u001B[0m:  " +
                entranceString + '\n' +
                diningString + '\n' +
                "Towers remaining: [" + TowerColor + "] " + numOfTowers ;

        if(coins != null) {
            return returnString + '\n' +
                    "coins: " + coins;
        }
        else {
            return returnString;
        }
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
