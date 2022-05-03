package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskMotherNatureStep. Contains the num of step choose by the player
 */
public class ReplyMotherNatureStepMessage extends Message {
    private final int step;

    public ReplyMotherNatureStepMessage(String nickname, int step) {
        super(nickname, MessageType.MN_STEP);
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    @Override
    public String toString() {
        return "ReplyMotherNatureStepMessage{" +
                "nickname="+ getNickname() +
                "step=" + step +
                '}';
    }
}
