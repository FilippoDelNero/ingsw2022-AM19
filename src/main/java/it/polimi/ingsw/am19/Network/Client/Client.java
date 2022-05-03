package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.PingMessage;
import it.polimi.ingsw.am19.View.Cli.Cli;
import it.polimi.ingsw.am19.View.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

public class Client {
    private final ClientSideController myController;
    private final View view;
    private final Socket socket; //socket con cui mi connetto al server
    private final ObjectInputStream input; //stream sul quale posso mandare oggetti serializzati
    private final ObjectOutputStream output; //stream sul quale posso ricevere oggetti serializzati
    private final ExecutorService thread;    //ExecutorService usato per creare un pool di threads composto in realtà
    // da un singolo thread, esso verrà usato per leggere i messaggi in maniera asincrona
    private final ScheduledExecutorService scheduledThread;

    //costruttore, serve passargli l'indirizzo del server e il numero di porta
    public Client(String hostName, int portNumber) {
        view = new Cli();
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

    public void sendMessage(Message msg) {
        try {
            output.writeObject(msg);
        } catch (IOException e) {
            disconnect();
        }
    }

    public void receiveMessage() {
        thread.execute( () ->
                {
                    while(!thread.isShutdown()) {
                        Message msg;
                        try {
                            msg = (Message) input.readObject();
                            myController.communicate(msg);
                            //TODO CHANGE THIS, TO WHOM SHOULD I SEND THE RECEIVED MESSAGE
                            //System.out.println("client: " + msg.toString());
                        } catch (IOException | ClassNotFoundException e) {
                            disconnect();
                        }
                    }
                }
        );
    }

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

    public void startPinging() {
        scheduledThread.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), 1, 1, TimeUnit.SECONDS);
    }

    public void stopPinging() {
        scheduledThread.shutdownNow();
    }
}
