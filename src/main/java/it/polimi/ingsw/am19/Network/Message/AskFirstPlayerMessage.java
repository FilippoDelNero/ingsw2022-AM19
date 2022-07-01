package it.polimi.ingsw.am19.Network.Message;

public class AskFirstPlayerMessage extends Message {
    private boolean matchToResume;

    /**
     * class constructor
     * @param matchToResume is there a match to resume, set to true if yes
     */
    public AskFirstPlayerMessage(boolean matchToResume) {
        super("server", MessageType.ASK_LOGIN_FIRST_PLAYER);
        this.matchToResume = matchToResume;
    }

    /**
     * getter for the matchToResume attribute
     * @return true if there is a match saved to resume
     */
    public boolean isMatchToResume() {
        return matchToResume;
    }

    @Override
    public String toString() {
        return "Match To Resume= '" + matchToResume;
    }
}
