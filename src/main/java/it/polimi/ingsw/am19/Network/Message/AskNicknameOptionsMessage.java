package it.polimi.ingsw.am19.Network.Message;
import java.util.List;

/**
 * Message for ask the nickname in a resuming match
 * Contains the nickname of the previous match still available
 * The reply must to contains one of the nickname present in nicknameAvailable
 */
public class AskNicknameOptionsMessage extends Message {
    private final List<String> nicknameAvailable;

    public AskNicknameOptionsMessage(List<String> nicknameAvailable) {
        super("server", MessageType.LOGIN_PLAYERS_OPTION);
        this.nicknameAvailable = nicknameAvailable;
    }

    public List<String> getNicknameAvailable() {
        return nicknameAvailable;
    }

    @Override
    public String toString() {
        return "AskNicknameOptionsMessage{" +
                "nicknameAvailable=" + nicknameAvailable +
                '}';
    }
}
