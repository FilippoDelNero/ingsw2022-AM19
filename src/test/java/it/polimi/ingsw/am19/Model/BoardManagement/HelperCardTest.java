package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.BoardManagement.HelperCard;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the 2 public methods of HelperCard. No specific corner case identified
 */
class HelperCardTest {
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
     * Testing the getter for the NextRound value
     */
    @Test
    @DisplayName("Testing getNextRound")
    void getNextRoundOrder() {
        HelperCard card=new HelperCard(WizardFamily.KING,5,3);
        assertEquals(5, card.getNextRoundOrder());
    }

    /**
     * Testing the getter for the MaxNumOfStep value
     */
    @Test
    @DisplayName("Testing getMaxNumOfStep")
    void getMaxNumOfSteps() {
        HelperCard card=new HelperCard(WizardFamily.KING,5,3);
        assertEquals(3, card.getMaxNumOfSteps());
    }
}
