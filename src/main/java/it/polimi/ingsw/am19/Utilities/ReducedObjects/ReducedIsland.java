package it.polimi.ingsw.am19.Utilities.ReducedObjects;

import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * record representing an island
 * @param numOfStudents the students present on the island
 * @param towerColor the towerColor, if present, on the island
 * @param presenceOfMotherNature true is motherNature is on this island
 * @param numOfIslands the number of island making up this group
 */
public record ReducedIsland(
        Map<PieceColor, Integer> numOfStudents,
        TowerColor towerColor,
        boolean presenceOfMotherNature,
        int numOfIslands,
        boolean noEntryTile) implements Serializable {

    private String pieceColorToANSI(PieceColor color) {
        return switch (color) {
            case RED -> "\u001B[31m";
            case GREEN -> "\u001B[32m";
            case BLUE -> "\u001B[34m";
            case YELLOW -> "\u001B[33m";
            case PINK -> "\033[38;5;206m";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedIsland that = (ReducedIsland) o;
        return presenceOfMotherNature == that.presenceOfMotherNature &&
                numOfIslands == that.numOfIslands &&
                Objects.equals(numOfStudents, that.numOfStudents) &&
                towerColor == that.towerColor &&
                noEntryTile == that.noEntryTile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numOfStudents, towerColor, presenceOfMotherNature, numOfIslands);
    }

    @Override
    public String toString() {
        String string = "[";
        for(int i = 0; i < numOfIslands; i++) {
            string = string.concat("*");
        }
        string = string.concat("] ");
        for(PieceColor p : PieceColor.values()) {
            if(numOfStudents.get(p) != 0)
                string = string.concat(pieceColorToANSI(p) + p + "x" + numOfStudents.get(p) + "\u001B[0m ");
        }
        if(towerColor != null)
            string = string.concat("-- " + towerColor);
        if(presenceOfMotherNature)
            string = string.concat(" -- MotherNature");
        if(noEntryTile)
            string = string.concat(" -- No Entry");
        return string;
    }
}
