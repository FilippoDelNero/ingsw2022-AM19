package it.polimi.ingsw.am19.Model.CharacterCards;

import it.polimi.ingsw.am19.Model.BoardManagement.GameBoard;
import it.polimi.ingsw.am19.Model.BoardManagement.Player;
import it.polimi.ingsw.am19.Model.Match.AbstractMatch;
import it.polimi.ingsw.am19.Model.Match.TwoPlayersMatch;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import it.polimi.ingsw.am19.Model.Utilities.TowerColor;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ExtraInfluenceCardTest {
    @Test
    void activateEffectTest() {

        //--MATCH PART--
        AbstractMatch m = new TwoPlayersMatch();
        Player player1 = new Player("Phil", TowerColor.BLACK, WizardFamily.SHAMAN);
        Player player2 = new Player("Dennis", TowerColor.WHITE, WizardFamily.KING);
        m.addPlayer(player1);
        m.addPlayer(player2);
        m.initializeMatch();

        //--GIVE PLAYER1 A PROFESSOR PART--
        //finding the color of the student on island
        int islandIndex = m.getIslandManager().getIslands().lastIndexOf(m.getMotherNature().getCurrPosition()) + 1;
        Map<PieceColor, Integer> map = m.getIslandManager().getIslands().get(islandIndex).getNumOfStudents();
        PieceColor color = null;
        GameBoard gb1 = m.getGameBoards().get(player1);
        for(PieceColor c : map.keySet()) {
            if(map.get(c) != 0)
                color = c;
        }
        //make sure that the player has a student of that color in its gameboard
        gb1.getEntrance().replace(color, 1);
        //give the player the professor
        m.setCurrPlayer(player1);
        PieceColor finalColor = color;
        assertDoesNotThrow(() -> m.moveStudentToDiningRoom(finalColor));


        //--CHARACTER CARD PART--
        AbstractCharacterCard card = new ExtraInfluenceCard(m);

        //--CHECK IF EVERYTHING IS OK PART --
        card.activateEffect(m.getIslandManager().getIslands().get(islandIndex), null);


        assertEquals(TowerColor.BLACK, m.getIslandManager().getIslands().get(islandIndex).getTowerColor());
        assertEquals(7, gb1.getNumOfTowers());
    }
}