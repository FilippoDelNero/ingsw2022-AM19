package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Utilities.WizardFamily;

import java.io.Serializable;

/**
 * Class for manage a Helper Card, with its 2 value: NextRoundOrder e MaxNumOfStep
 */
public class HelperCard implements Serializable {
    private final WizardFamily wizardFamily;
    private final int nextRoundOrder;
    private final int maxNumOfSteps;

    /**
     * Constructor for an HelperCard
     * @param wizardFamily WizardFamily to distinguish the 4 decks of HelperCard
     * @param nextRoundOrder The value for decide the order of the next Round
     * @param maxNumOfSteps How many steps MotherNature can do
     */
    public HelperCard(WizardFamily wizardFamily, int nextRoundOrder, int maxNumOfSteps) {
        this.wizardFamily = wizardFamily;
        this.nextRoundOrder = nextRoundOrder;
        this.maxNumOfSteps = maxNumOfSteps;
    }

    /**
     * getter for the WizardFamily parameter
     * @return the WizardFamily of this card
     */
    public WizardFamily getWizardFamily() {
        return wizardFamily;
    }

    /**
     * getter for the NextRoundOrder parameter
     * @return the NextRoundOrder value of this card
     */
    public int getNextRoundOrder() {
        return nextRoundOrder;
    }

    /**
     * getter for the MaxNumOfSteps parameter
     * @return the MaxNumOfSteps value of this card
     */
    public int getMaxNumOfSteps() {
        return maxNumOfSteps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelperCard that = (HelperCard) o;
        return nextRoundOrder == that.nextRoundOrder && maxNumOfSteps == that.maxNumOfSteps;
    }

    @Override
    public String toString() {
        return ("Card number: " + nextRoundOrder + ", " + "mother nature steps: " + maxNumOfSteps);
    }
}