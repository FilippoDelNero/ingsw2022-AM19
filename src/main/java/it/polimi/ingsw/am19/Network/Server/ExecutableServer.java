package it.polimi.ingsw.am19.Network.Server;


public class ExecutableServer {
    public static void main(String[] args) {
        Server server = new Server(1237); //this will be passed as argument
        while(true) {
        server.connect();
        }

    }
}
