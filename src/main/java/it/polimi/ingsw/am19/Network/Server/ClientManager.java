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
    private final Socket myClient;

    /** channel used to receive incoming messages*/
    private final ObjectInputStream input;

    /** channel used to send messages*/
    private final ObjectOutputStream output;

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
     * boolean used to let the clientManager do two attempts at reading or writing an object
     */
    private boolean tryAgain;

    /**
     * class constructor, accept the connection and opens up input and output, starts the timer
     * @param id a number given in chronological order to each clientManager
     * @param server the server that created this clientManager
     * @param socket the socket that the server is listening on
     * @param matchController the MatchController whose reference needs to be stored
     * @throws IOException exception thrown if the client manager fail to be instantiated
     */
    public ClientManager(int id, Server server, ServerSocket socket, MatchController  matchController) throws IOException {
        this.tryAgain = true;
        this.id = id;
        this.matchController = matchController;

        myServer = server;
        myTimer = new Timer(this, 3000);

        lockToReceive = new Object();
        lockToSend = new Object();

        myClient = socket.accept(); //accepts the connection
        output = new ObjectOutputStream(myClient.getOutputStream());
        output.flush();
        input = new ObjectInputStream(myClient.getInputStream());

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
     * getter for the server
     * @return the server that created this clientManager
     */
    public Server getServer() {
        return myServer;
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
                System.out.println("----" + msg.getMessageType() + "--->");
                output.reset();
            } catch (IOException e) {
                if(tryAgain) {
                    tryAgain = false;
                    sendMessage(msg);
                }
                else {
                    System.out.println("errore in invio");
                    myServer.removeAllClients(this);
                }
            }
            tryAgain = true;
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
                    tryAgain = false;
                    if(msg.getMessageType()!= MessageType.PING_MESSAGE)
                        System.out.println("<---" + msg.getMessageType() + "----");
                    switch (msg.getMessageType()){
                        case PING_MESSAGE -> myTimer.reset();
                        case RESUME_MATCH,REPLY_CREATE_MATCH,REPLY_LOGIN_INFO -> myServer.MessageToLoginManager(msg);
                        default -> matchController.inspectMessage(msg);
                    }
                } catch (IOException e) {
                   if(tryAgain)
                       tryAgain = false;
                   else {
                       System.out.println("errore in ricezione");
                       myServer.removeAllClients(this);
                   }
                } catch (ClassNotFoundException e) {
                    myServer.removeAllClients(this);
                }
            }
        }
    }

    /**
     * method to stop the timer, close the connection and remove this Manager from the server's list
     */
    public void close() {
        if(!isClosed()) {
            while(myTimer.isOff())
                myTimer.off();
            while(!myClient.isClosed()) {
                try {
                    myClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("client disconnected");
            myServer.removeFromList(this);
        }
    }

    /**
     * method that return the status of the clientManager
     * @return true if the thread has been already interrupted
     */
    public boolean isClosed() {
        return Thread.currentThread().isInterrupted();
    }

}
