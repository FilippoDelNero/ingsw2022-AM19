package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Controller.MatchController;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The ClientManager is an object used to handle connection with a specific client
 * it accept the client connection and sets up everything that is needed to communicate with it
 */
public class ClientManager implements Runnable {
    /** keeps a reference to the MatchController class */
    private final MatchController matchController;

    /** Timer needed to make sure the client is still alive*/
    private final Timer myTimer;

    /** the server that created this clientManager */
    private final Server myServer;

    /** the client's socket*/
    private Socket myClient;

    /** channel used to receive incoming messages*/
    private ObjectInputStream input;

    /** channel used to send messages*/
    private ObjectOutputStream output;

    /**
     * lock object used to synchronized on the inputStream
     * done this way because we need to synchronize on a final object
     */
    private final Object lockToReceive;

    /**
     * lock object used to synchronized on the outputStream
     * done this way because we need to synchronize on a final object
     */
    private final Object lockToSend;

    /**
     * the id number used to identify the specific clientManager
     * clientManager #1 is the first one connected
     */
    private final int id;

    /**
     * class constructor, accept the connection and opens up input and output, starts the timer
     * @param id a number given in chronological order to each clientManager
     * @param server the server that created this clientManager
     * @param socket the socket that the server is listening on
     * @param matchController the MatchController whose reference needs to be stored
     */
    public ClientManager(int id, Server server, ServerSocket socket, MatchController  matchController) {
        this.id = id;
        this.matchController = matchController;

        myServer = server;
        myTimer = new Timer(this, 3000);

        lockToReceive = new Object();
        lockToSend = new Object();

        try {
            myClient = socket.accept(); //accepts the connection
            output = new ObjectOutputStream(myClient.getOutputStream());
            output.flush();
            input = new ObjectInputStream(myClient.getInputStream());
        } catch (IOException e) {
            server.removeClient(this, false);
        }

        myTimer.start();
        System.out.println("nuovo client connesso");
    }

    /**
     * getter for the id parameter
     * @return the id of this clientManager
     */
    public int getId() {
        return id;
    }

    /**
     * the clientManager is always listening for incoming messages
     */
    @Override
    public void run() {
        receiveMessage();
    }

    /**
     * method used to send messages to the client this clientManager is associated with
     * @param msg the messages we want to send
     */
    public void sendMessage(Message msg) {
        synchronized (lockToSend) {
            try {
                output.writeObject(msg);
                System.out.println(msg.getMessageType());
                output.reset();
            } catch (IOException e) {
                close(true);
            }
        }
    }

    /**
     * method to asynchronously receive messages from the client
     */
    public void receiveMessage() {
        while(!Thread.currentThread().isInterrupted()) {
            synchronized (lockToReceive) {
                try {
                    Message msg = (Message) input.readObject();
                    if(msg.getMessageType()!= MessageType.PING_MESSAGE)
                        System.out.println(msg.getMessageType());
                    switch (msg.getMessageType()){
                        case PING_MESSAGE -> myTimer.reset();
                        case RESUME_MATCH,REPLY_CREATE_MATCH,REPLY_LOGIN_INFO -> myServer.MessageToLoginManager(msg);
                        default -> matchController.inspectMessage(msg);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    close(true);
                }
            }
        }
    }

    /**
     * method to stop the timer, close the connection and remove this Manager from the server's list
     */
    public void close(boolean fatal) {
        while(!Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();

            while(!myTimer.isInterrupted())
                myTimer.interrupt();

            while(!myClient.isClosed()) {
                try {
                    myClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myServer.removeClient(this, fatal);
            }

            System.out.println("client disconnesso");
        }
    }

    public boolean isClosed() {
        return Thread.currentThread().isInterrupted();
    }

}
