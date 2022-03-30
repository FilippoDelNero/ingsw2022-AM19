package it.polimi.ingsw.am19.Model;

/**
 * Class for manage a Helper Card, with its 2 value: NextRoundOrder e MaxNumOfStep
 */
public class HelperCard {
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

    public int getNextRoundOrder() {
        return nextRoundOrder;
    }

    public int getMaxNumOfSteps() {
        return maxNumOfSteps;
    }

}
