package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Network.Message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class, handles incoming connections
 */
public class Server {
    /** the socket the server opens*/
    private final ServerSocket serverSocket;

    /** a thread pool, each new connection is assigned to a different thread */
    private final ExecutorService pool;

    /** an object used to handle the "login" phase of each player*/
    private final LoginManager loginManager;

    private final MatchController matchController;

    /** a list of all clientManagers created by the server*/
    private final List<ClientManager> managers;

    /**
     * class constructor, opens up a socket and create a LoginManager
     * @param port the port on which the server should open its socket
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.matchController = new MatchController();
        this.loginManager = new LoginManager(matchController);
        managers = new ArrayList<>();
        pool = Executors.newCachedThreadPool();
    }

    /**
     * method used to accept an incoming connection, it creates a new ClientManager and passes it to the LoginManager
     */
    public void connect() {
        ClientManager clientManager;
        pool.execute(clientManager = new ClientManager(managers.size(),this, serverSocket));
        managers.add(clientManager);
        loginManager.login(clientManager);
    }

    /**
     * Method used to pass messages from the clientManager to the LoginManager
     * This way we handles all client replies in the login phase
     * @param msg the message coming from the client
     */
    public void MessageToLoginManager(Message msg) {
        System.out.println(msg.toString());
        loginManager.setAnswerFromClient(msg);
    }

    /**
     * method to remove a clientManager from the server's list
     * @param clientManager the clientManager that is no longer in use
     */
    public void removeClient(ClientManager clientManager) {
        synchronized (managers) {
            managers.remove(clientManager);
        }
    }
}
