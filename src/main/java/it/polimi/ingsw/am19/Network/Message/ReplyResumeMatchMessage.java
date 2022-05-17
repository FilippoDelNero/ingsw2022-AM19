package it.polimi.ingsw.am19.Network.Message;

import it.polimi.ingsw.am19.Network.Server.ClientManager;

public class ReplyResumeMatchMessage extends Message {
    public ReplyResumeMatchMessage() {
        super(null, MessageType.RESUME_MATCH);
    }




    @Override
    public String toString() {
        return "ReplyResumeMatchMessage";
    }
}
