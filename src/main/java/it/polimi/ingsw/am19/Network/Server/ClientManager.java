package it.polimi.ingsw.am19.Network.Server;

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
    /** the server that created this clientManager */
    private final Server myServer;

    /** the client's socket*/
    private Socket myClient;

    /** channel used to receive incoming messages*/
    private ObjectInputStream input;

    /** channel used to send messages*/
    private ObjectOutputStream output;

    /** Timer needed to make sure the client is still alive*/
    private final Timer myTimer;

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
     */
    public ClientManager(int id, Server server, ServerSocket socket) {
        this.id = id;
        myServer = server;
        myTimer = new Timer(this, 3000);
        try {
            myClient = socket.accept(); //si mette in attesa di una connessione, quando arriva la accetto
            output = new ObjectOutputStream(myClient.getOutputStream());
            output.flush();
            input = new ObjectInputStream(myClient.getInputStream());
        } catch (IOException e) {
            server.removeClient(this);
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
        try {
            output.writeObject(msg);
        } catch (IOException e) {
            close();
        }
    }

    /**
     * method to asynchronously receive messages from the client
     */
    public void receiveMessage() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = (Message) input.readObject();
                if(msg.getMessageType() == MessageType.PING_MESSAGE)
                    myTimer.reset();
                else if(msg.getMessageType() == MessageType.RESUME_MATCH ||
                        msg.getMessageType() == MessageType.REPLY_CREATE_MATCH ||
                        msg.getMessageType() == MessageType.REPLY_LOGIN_INFO)
                    myServer.MessageToLoginManager(msg);
            } catch (IOException | ClassNotFoundException e) {
                close();
            }
        }
    }

    /**
     * method to stop the timer, close the connection and remove this Manager from the server's list
     */
    public void close() {
        if(!Thread.currentThread().isInterrupted()) {
            if(!myClient.isClosed()) {
                try {
                    myClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myServer.removeClient(this);
            }
            if(!myTimer.isInterrupted())
                myTimer.interrupt();
            Thread.currentThread().interrupt();
            System.out.println("client disconnesso");
        }
    }

}
