package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Network.Message.AskResumeMatchMessage;

import static java.lang.Thread.sleep;

public class ExecutableServer {
    public static void main(String[] args) {
        boolean bool = true;
        Server server = new Server(1236); //this will be passed as argument
        while(bool) {
        server.connect();
        bool = false;
        }
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.sendMessageToAll(new AskResumeMatchMessage());
    }
}
