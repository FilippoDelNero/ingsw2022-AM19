package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.Network.Message.PingMessage;
import it.polimi.ingsw.am19.View.Cli.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    private final ClientSideController myController;
    private final Cli cli;
    private final Socket socket; //socket con cui mi connetto al server
    private final ObjectInputStream input; //stream sul quale posso mandare oggetti serializzati
    private final ObjectOutputStream output; //stream sul quale posso ricevere oggetti serializzati
    private final ExecutorService thread;    //ExecutorService usato per creare un pool di threads composto in realtà
    // da un singolo thread, esso verrà usato per leggere i messaggi in maniera asincrona
    private final ScheduledExecutorService scheduledThread;

    //costruttore, serve passargli l'indirizzo del server e il numero di porta
    public Client(String hostName, int portNumber) {
        cli = new Cli();
        myController = new ClientSideController(this, cli);
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
        cli.genericPrint("sono un client e sono connesso ad un server");
        //System.out.println("sono un client e sono connesso ad un server");

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
                            cli.genericPrint(msg.toString());
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