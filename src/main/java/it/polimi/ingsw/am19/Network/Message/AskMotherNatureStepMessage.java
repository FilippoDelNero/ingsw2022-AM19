package it.polimi.ingsw.am19.Network.Message;

/**
 * Message for ask of how many step the current player want to move MotherNature
 */
public class AskMotherNatureStepMessage extends Message {
    public AskMotherNatureStepMessage() {
        super("server", MessageType.HOW_MANY_STEP_MN);
    }

    @Override
    public String toString() {
        return "AskMotherNatureStepMessage{}";
    }
}
