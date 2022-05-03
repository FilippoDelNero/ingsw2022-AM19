package it.polimi.ingsw.am19.View.Cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class StdInReader implements Callable<String> {
    private final BufferedReader reader;

    public StdInReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

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