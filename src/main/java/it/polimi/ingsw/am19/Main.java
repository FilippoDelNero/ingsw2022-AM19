package it.polimi.ingsw.am19;

import it.polimi.ingsw.am19.Network.Server.Server;
import it.polimi.ingsw.am19.View.Cli.Cli;
import it.polimi.ingsw.am19.View.GUI.Gui;
import javafx.application.Application;

public class Main {
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
                while(true) {
                    server.connect(); //TODO IL SERVER DEVE IMPLEMENTARE RUNNABLE
                }
            }
            else
                System.out.println("write -server and the port address");
        }
    }
}
