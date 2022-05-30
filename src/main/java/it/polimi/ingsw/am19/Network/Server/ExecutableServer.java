package it.polimi.ingsw.am19.Network.Server;

/**
 * executable class, it creates a game server
 */
@Deprecated
public class ExecutableServer {
    @Deprecated
    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0])); //this will be passed as argument
        while(true) {
        server.connect();
        }
    }
}
