package it.polimi.ingsw.am19.View.Cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * class used to read inputs from the user
 */
public class StdInReader implements Callable<String> {
    /** the reader from which user's inputs are read */
    private final BufferedReader reader;

    /**
     * class constructor
     */
    public StdInReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * checks if the users has written something
     * @return the line written by the user
     */
    @Override
    public String call() throws IOException, InterruptedException {
        String input;

        while (!reader.ready()) {
            Thread.sleep(200);
        }
        input = reader.readLine();
        return input;
    }
}