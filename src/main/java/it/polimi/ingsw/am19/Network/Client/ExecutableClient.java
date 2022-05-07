package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.View.Cli.Cli;

/**
 * executable class, it creates a game client
 */
public class ExecutableClient {
    public static void main(String[] args) {
        if(args.length == 2) {
            Client client = new Client(args[0], Integer.parseInt(args[1]), new Cli()); //the last parameter must be also be a user's choice

            client.startPinging();
            client.receiveMessage();
        }

    }
}
