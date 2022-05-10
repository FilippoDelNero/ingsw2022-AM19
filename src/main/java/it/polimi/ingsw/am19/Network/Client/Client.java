package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.MessageType;
import it.polimi.ingsw.am19.Network.Message.PingMessage;
import it.polimi.ingsw.am19.View.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * client class, handles connection to server
 */
public class Client {
    /** the controller used to manage incoming messages and send correct replies */
    private final ClientSideController myController;

    /** socket used to connect to the server */
    private final Socket socket;

    /** stream used to receive serialized objects */
    private final ObjectInputStream input;

    /** stream used to send serialized objects */
    private final ObjectOutputStream output;

    /**
     * ExecutorService used to create a thread pool, a single thread pool will be used
     * this thread will be used to read incoming messages
     */
    private final ExecutorService thread;

    /**
     * ScheduledExecutorService used to create a thread pool, a single thread pool will be used
     * this scheduledThread will be used to ping asynchronously the server
     */
    private final ScheduledExecutorService scheduledThread;

    /**
     * class constructor
     * @param hostName the ip address of the server
     * @param portNumber the port number at which the client should connect to
     */
    public Client(String hostName, int portNumber, View view) {
        myController = new ClientSideController(this, view);
        thread = Executors.newSingleThreadExecutor();
        scheduledThread = Executors.newSingleThreadScheduledExecutor();

        try {
            this.socket = new Socket(hostName, portNumber);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method used to send messages to the server
     * @param msg the messages that needs to be sent
     */
    public void sendMessage(Message msg) {
        try {
            output.writeObject(msg);
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * method used to receive messages from the server asynchronously
     */
    public void receiveMessage() {
        thread.execute( () ->
                {
                    while(!thread.isShutdown()) {
                        Message msg;
                        try {
                            msg = (Message) input.readObject();
                            myController.communicate(msg);
                        } catch (IOException | ClassNotFoundException e) {
                            disconnect();
                        }
                    }
                }
        );
    }

    /**
     * method to disconnect from the server, should be called before killing the client
     */
    public void disconnect() {
        stopPinging();
        thread.shutdownNow();
        if(socket.isConnected()) {
            if(!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * method to start pinging the server
     */
    public void startPinging() {
        scheduledThread.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), 1, 1, TimeUnit.SECONDS);
    }

    /**
     * method to stop pinging the server
     */
    public void stopPinging() {
        scheduledThread.shutdownNow();
    }
}
