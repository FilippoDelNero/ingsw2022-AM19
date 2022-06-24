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
        ClientManager clientManager;
        pool.execute(clientManager = new ClientManager(managers.size(),this, serverSocket, this.matchController));
        if(loginManager.login(clientManager))
            managers.add(clientManager);
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
     * method to remove a clientManager from the server's list
     * @param clientManager the clientManager that is no longer in use
     */
    public void removeClient(ClientManager clientManager, boolean fatal) {
        synchronized (managersLock) {
            if(fatal)
                removeAllClients();
            else {
                managers.remove(clientManager);
                if(managers.isEmpty())
                    prepareForANewMatch();
            }
        }
    }

    /**
     * method to disconnect all clients and prepare the server to create a new match
     */
    public void removeAllClients() {
        if(!managers.isEmpty()) {
            managers.get(0).sendMessage(new EndMatchMessage(null));
            managers.get(0).close(false);
            managers.remove(0);
            removeAllClients();
        }
        else
           prepareForANewMatch();
    }

    /**
     * method to prepare the server to instantiate a new match
     */
    public void prepareForANewMatch() {
        managers = new ArrayList<>();
        matchController = new MatchController();
        loginManager = new LoginManager(matchController);
    }
}
