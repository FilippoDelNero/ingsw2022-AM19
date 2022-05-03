package it.polimi.ingsw.am19.View.Cli;

import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli {
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

    public boolean askResumeMatch() throws ExecutionException {
        String input;
        while(true) {
            printer.println("Welcome, do you want to resume an old match? [y/n]");

            input = readLine();

            if(input.equals("y"))
                return true;
            else if(input.equals("n"))
                return false;
        }
    }

    public void genericPrint(String toPrint) {
        printer.println(toPrint);
    }
}
