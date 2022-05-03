package it.polimi.ingsw.am19.Network.Server;

public class Timer extends Thread {
    ClientManager myClientManager;
    protected int rate = 500;
    private int duration;
    private int elapsed;

    public Timer (ClientManager manager, int time) {
        myClientManager = manager;
        duration = time; // Assign to member variable
        elapsed = 0; // Set time elapsed
    }

    public synchronized void reset() {
        elapsed = 0;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(rate); // Put the timer to sleep
            } catch (InterruptedException e) {
                continue;
            }

            synchronized (this) { // Use 'synchronized' to prevent conflicts
                elapsed += rate; // Increment time remaining
                if (elapsed > duration) // Check to see if the time has been exceeded
                    timeout(); // Trigger a timeout
            }
        }
    }

    public void timeout() {
        if(!Thread.currentThread().isInterrupted()) {
            myClientManager.close();
            Thread.currentThread().interrupt();
        }
    }
}
