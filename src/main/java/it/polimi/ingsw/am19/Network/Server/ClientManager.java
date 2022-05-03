package it.polimi.ingsw.am19.Network.Server;

import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientManager implements Runnable{
    private final Server myServer;

    private Socket myClient;

    private ObjectInputStream input;

    private ObjectOutputStream output;

    private final Timer myTimer;

    private final int id;

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

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        receiveMessage();
    }

    public void sendMessage(Message msg) {
        try {
            output.writeObject(msg);
        } catch (IOException e) {
            close();
        }
    }

    public void receiveMessage() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = (Message) input.readObject();
                if(msg.getMessageType() == MessageType.PING_MESSAGE)
                    myTimer.reset();
                else if(msg.getMessageType() == MessageType.LOGIN_NICKNAME)
                    ;
                else
                    myServer.receiveMessage(msg);
            } catch (IOException | ClassNotFoundException e) {
                close();
            }
        }
    }

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
        }
    }

}
