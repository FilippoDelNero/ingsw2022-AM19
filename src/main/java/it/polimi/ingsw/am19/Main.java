package it.polimi.ingsw.am19;

import it.polimi.ingsw.am19.Network.Server.Server;
import it.polimi.ingsw.am19.View.Cli.Cli;
import it.polimi.ingsw.am19.View.GUI.Gui;
import javafx.application.Application;

/**
 * main class
 */
public class Main {
    /**
     * main method, called at the start of the application
     * @param args the arguments passed to the program
     */
    public static void main(String[] args) {
        if(args.length == 0)
            Application.launch(Gui.class);
        else if(args.length == 1) {
            if(args[0].equals("-cli"))
                new Cli();
            else
                System.out.println("write -cli for cli or nothing for gui");
        }
        else if(args.length == 2) {
            if(args[0].equals("-s")) {
                Server server = new Server(Integer.parseInt(args[1]));
                Thread thread = new Thread(server, "server");
                thread.start();
            }
            else
                System.out.println("write -server and the port address");
        }
        else {
            System.out.println("write -s and the port address to start a server");
            System.out.println("write -cli to start a client in CLI mode or nothing to start a client in GUI mode");
        }
    }
}
