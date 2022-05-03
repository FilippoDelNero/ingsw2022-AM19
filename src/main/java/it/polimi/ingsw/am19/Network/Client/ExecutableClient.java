package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.Network.Message.GenericMessage;
import it.polimi.ingsw.am19.Network.Message.Message;
import it.polimi.ingsw.am19.View.Cli.Cli;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ExecutableClient {
    public static void main(String[] args) {
        Client client = new Client("0.0.0.0", 1236); //this will be passed as argument. The id should be a random number
        client.startPinging();
        client.receiveMessage();

        /*do {
            try {
                input = cli.readLine();
                Message msg = new GenericMessage(input);
                client.sendMessage(msg);
                client.receiveMessage();
            } catch (ExecutionException e) {
                client.disconnect();
            }
        } while(!Objects.equals(input, "stop"));*/

        //client.disconnect();
    }
}
