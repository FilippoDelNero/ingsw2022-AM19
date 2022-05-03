package it.polimi.ingsw.am19.Network.Server;

public class ExecutableServer {
    public static void main(String[] args) {
        boolean bool = true;
        Server server = new Server(1236); //this will be passed as argument
        while(bool) {
            server.connect();
            bool = false;
        }
    }
}