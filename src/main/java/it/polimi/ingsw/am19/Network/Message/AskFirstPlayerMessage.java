package it.polimi.ingsw.am19.Network.Message;

public class AskFirstPlayerMessage extends Message {
    private boolean matchToResume;

    public AskFirstPlayerMessage(boolean matchToResume) {
        super("server", MessageType.ASK_LOGIN_FIRST_PLAYER);
        this.matchToResume = matchToResume;
    }

    public boolean isMatchToResume() {
        return matchToResume;
    }

    @Override
    public String toString() {
        return "Match To Resume= '" + matchToResume;
    }
}
