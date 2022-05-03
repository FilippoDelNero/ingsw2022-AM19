package it.polimi.ingsw.am19.Network.Message;

import java.util.Arrays;

/**
 * Message for ask the nickname in a resuming match
 * Contains the nickname of the previous match still available
 * The reply must to contains one of the nickname present in nicknameAvailable
 */
public class AskNicknameOptionsMessage extends Message {
    private final String[] nicknameAvailable;

    public AskNicknameOptionsMessage( String[] nicknameAvailable) {
        super("server", MessageType.LOGIN_PLAYERS_OPTION);
        this.nicknameAvailable = nicknameAvailable;
    }

    public String[] getNicknameAvailable() {
        return nicknameAvailable;
    }

    @Override
    public String toString() {
        return "AskNicknameOptionsMessage{" +
                "nicknameAvailable=" + Arrays.toString(nicknameAvailable) +
                '}';
    }
}
