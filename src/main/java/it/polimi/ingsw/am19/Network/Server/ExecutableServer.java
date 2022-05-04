package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Network.Message.AskResumeMatchMessage;

import static java.lang.Thread.sleep;

public class ExecutableServer {
    public static void main(String[] args) {
        Server server = new Server(1237); //this will be passed as argument
        while(true) {
        server.connect();
        }

    }
}
