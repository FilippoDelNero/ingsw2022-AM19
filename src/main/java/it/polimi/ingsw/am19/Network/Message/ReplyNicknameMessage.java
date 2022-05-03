package it.polimi.ingsw.am19.Network.Message;

/**
 * Reply to AskNickname. Contains a new nickname choose by a player
 */
public class ReplyNicknameMessage extends Message {
    private final String nicknameChosen;

    public ReplyNicknameMessage(String nicknameChosen) {
        super(null, MessageType.LOGIN_NICKNAME);
        this.nicknameChosen = nicknameChosen;
    }

    public String getNicknameChosen() {
        return nicknameChosen;
    }

    @Override
    public String toString() {
        return "ReplyNicknameMessage{" +
                "nicknameChosen='" + nicknameChosen + '\'' +
                '}';
    }
}
