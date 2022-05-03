package it.polimi.ingsw.am19.View.Cli;

import it.polimi.ingsw.am19.View.View;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli implements View {
    private final PrintStream printer;
    Thread inputThread;

    public Cli() {
        printer = System.out;
    }

    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new StdInReader());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    @Override
    public void init() {
        printer.println("#######                                           ");
        printer.println("#       #####  #   #   ##   #    # ##### #  ####  ");
        printer.println("#       #    #  # #   #  #  ##   #   #   # #      ");
        printer.println("#####   #    #   #   #    # # #  #   #   #  ####  ");
        printer.println("#       #####    #   ###### #  # #   #   #      # ");
        printer.println("#       #   #    #   #    # #   ##   #   # #    # ");
        printer.println("####### #    #   #   #    # #    #   #   #  ####  ");
        printer.println("\n");
        printer.println("Welcome!");
    }

    @Override
    public void error(String errorMsg) {
        printer.println(errorMsg);
    }
}
