package it.polimi.ingsw.am19.Network.Client;

import it.polimi.ingsw.am19.View.Cli.Cli;
import it.polimi.ingsw.am19.View.GUI.Gui;
import javafx.application.Application;
import javafx.application.Platform;

/**
 * executable class, it creates a game client
 */
@Deprecated
public class ExecutableClient {
    @Deprecated
    public static void main(String[] args) {
        if(args.length == 3)
            new Cli();
        else if(args.length == 2)
            Application.launch(Gui.class);
    }
}
