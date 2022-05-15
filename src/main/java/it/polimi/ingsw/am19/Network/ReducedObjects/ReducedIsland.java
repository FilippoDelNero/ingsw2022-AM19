package it.polimi.ingsw.am19.Network.ReducedObjects;

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
        TowerColor towerColor, boolean presenceOfMotherNature, int numOfIslands) implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReducedIsland that = (ReducedIsland) o;
        return presenceOfMotherNature == that.presenceOfMotherNature && numOfIslands == that.numOfIslands && Objects.equals(numOfStudents, that.numOfStudents) && towerColor == that.towerColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numOfStudents, towerColor, presenceOfMotherNature, numOfIslands);
    }

    @Override
    public String toString() { //TODO FAR CAPIRE DA QUANTE ISOLE è COMPOSTO IL GRUPPO (ICONCINA?)
        String string = "";
        for(PieceColor p : PieceColor.values()) {
            if(numOfStudents.get(p) != 0)
                string = string.concat(p + "x" + numOfStudents.get(p) + " ");
        }
        if(towerColor != null)
            string = string.concat("-- " + towerColor);
        if(presenceOfMotherNature)
            string = string.concat(" -- MotherNature");

        return string;
    }
}
