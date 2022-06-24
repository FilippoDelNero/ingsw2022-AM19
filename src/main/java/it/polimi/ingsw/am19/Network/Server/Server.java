package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Network.Message.EndMatchMessage;
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
public class Server implements Runnable {
    /** the socket the server opens*/
    private final ServerSocket serverSocket;

    /** a thread pool, each new connection is assigned to a different thread */
    private final ExecutorService pool;

    /** an object used to handle the "login" phase of each player*/
    private LoginManager loginManager;

    /** keeps a reference to the MatchController class */
    private MatchController matchController;

    /** a list of all clientManagers created by the server*/
    private List<ClientManager> managers;

    /** object used to lock on the managers list to avoid conflicts */
    private final Object managersLock;

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
        managersLock = new Object();
        managers = new ArrayList<>();
        pool = Executors.newCachedThreadPool();
        System.out.println("Server on");
    }

    public void run() {
        while(!Thread.interrupted()) {
            connect();
        }
    }

    /**
     * method used to accept an incoming connection, it creates a new ClientManager and passes it to the LoginManager
     */
    public void connect() {
        try {
            ClientManager clientManager = new ClientManager(managers.size(),this, serverSocket, this.matchController);
            pool.execute(clientManager);
            if(loginManager.login(clientManager))
                managers.add(clientManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to pass messages from the clientManager to the LoginManager
     * This way we handles all client replies in the login phase
     * @param msg the message coming from the client
     */
    public void MessageToLoginManager(Message msg) {
        loginManager.setAnswerFromClient(msg);
    }

    /**
     * method to disconnect all clients and prepare the server to create a new match
     */
    public void removeAllClients() {
        while(!managers.isEmpty()) {
            ClientManager cm = managers.get(0);
            cm.sendMessage(new EndMatchMessage(null));
            cm.close(false);
        }
    }

    public void removeFromList(ClientManager cm) {
        synchronized (managersLock) {
            managers.remove(cm);
            if(managers.size() == 0)
                prepareForANewMatch();
        }
    }

    /**
     * method to prepare the server to instantiate a new match
     */
    public void prepareForANewMatch() {
        synchronized (managersLock) {
            managers = new ArrayList<>();
        }
        matchController = new MatchController();
        loginManager = new LoginManager(matchController);
    }
}
