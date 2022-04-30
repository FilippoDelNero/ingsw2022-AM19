package it.polimi.ingsw.am19.Network.Message;

public class AskNicknameOptionsMessage extends Message {
    private final String nicknameAvailable;

    public AskNicknameOptionsMessage( String nicknameAvailable) {
        super("server", MessageType.LOGIN_PLAYERS_OPTION);
        this.nicknameAvailable = nicknameAvailable;
    }

    public String getNicknameAvailable() {
        return nicknameAvailable;
    }

    @Override
    public String toString() {
        return "AskNicknameOptionsMessage{" +
                "nicknameAvailable='" + nicknameAvailable + '\'' +
                '}';
    }
}
