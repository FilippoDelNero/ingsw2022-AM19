package it.polimi.ingsw.am19.Model.InfluenceStrategies;

import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.BoardManagement.ProfessorManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NoEntryTileInfluenceTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     */
    @Test
    void testWhereNothingHappens() {
        NoEntryTileInfluence noEntryTileInfluence = new NoEntryTileInfluence();
        ProfessorManager manager = new ProfessorManager();
        Map<PieceColor, Integer> map = new HashMap<>();
        map.put(PieceColor.RED, 2);
        map.put(PieceColor.GREEN, 1);
        map.put(PieceColor.BLUE, 10);
        map.put(PieceColor.YELLOW, 1);
        map.put(PieceColor.PINK, 0);
        assertNull(noEntryTileInfluence.calculateInfluence(map, null, 1, manager));
    }
}
