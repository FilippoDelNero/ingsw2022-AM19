package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Network.Server.ClientManager;

public class ReplyResumeMatchMessage extends Message {
    private final boolean resume;
    private ClientManager clientManager = null;

    public ReplyResumeMatchMessage(String nickname, boolean resume) {
        super(nickname, MessageType.RESUME_MATCH);
        this.resume = resume;
    }


    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    public boolean isResume() {
        return resume;
    }

    @Override
    public String toString() {
        return "ReplyResumeMatchMessage{" +
                "resume=" + resume +
                ", clientManager=" + clientManager +
                '}';
    }
}
