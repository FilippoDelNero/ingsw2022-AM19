package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskCloud. Contains the num of the Cloud chosen
 */
public class ReplyCloudMessage extends Message {
    private final int cloudChosen;

    public ReplyCloudMessage(String nickname, int cloudChosen) {
        super(nickname, MessageType.CHOSEN_CLOUD);
        this.cloudChosen = cloudChosen;
    }

    public int getCloudChosen() {
        return cloudChosen;
    }

    @Override
    public String toString() {
        return "ReplyCloudMessage{" +
                "nickname="+ getNickname() +
                "cloudChosen=" + cloudChosen +
                '}';
    }
}
