package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Network.Message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ServerSocket serverSocket;

    private final ExecutorService pool;

    private final LoginManager loginManager;

    private final List<ClientManager> managers;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port); //apre una socket alla porta passata
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.loginManager = new LoginManager();
        managers = new ArrayList<>();
        pool = Executors.newCachedThreadPool();
    }

    public void connect() {
        ClientManager clientManager;
        pool.execute(clientManager = new ClientManager(managers.size(),this, serverSocket));
        managers.add(clientManager);
        loginManager.login(clientManager);
    }

    public void sendMessageToAll(Message msg) {
        System.out.println("sto inviando un messaggio");
        for(ClientManager clientManager : managers) {
            clientManager.sendMessage(msg);
        }
    }

    public void receiveMessage(Message msg) {
        //TODO CHANGE THIS, MASSAGES WILL GO TO THE CONTROLLER
        System.out.println("ricevuto: " + msg.toString());
        this.sendMessageToAll(msg);
    }


    public void removeClient(ClientManager clientManager) {
        synchronized (managers) {
            managers.remove(clientManager);
        }
        System.out.println("client disconnesso");
    }
}
