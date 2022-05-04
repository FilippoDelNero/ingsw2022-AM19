package it.polimi.ingsw.am19.Network.Message;

public class AskFirstPlayerMessage extends Message {
    private final String matchToResume;

    public AskFirstPlayerMessage(String matchToResume) {
        super("server", MessageType.ASK_LOGIN_FIRST_PLAYER);
        this.matchToResume = matchToResume;
    }

    public String getMatchToResume() {
        return matchToResume;
    }

    @Override
    public String toString() {
        return "Match To Resume= '" + matchToResume;
    }
}
