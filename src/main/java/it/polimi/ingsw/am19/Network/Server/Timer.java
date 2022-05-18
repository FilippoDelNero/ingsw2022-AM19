package it.polimi.ingsw.am19.Network.Server;

/**
 * class that simulates a Timer
 */
public class Timer extends Thread {
    /** the client manager that this timer is associated with */
    ClientManager myClientManager;

    /** the amount of time that will elapse between two checks */
    protected int rate = 500;

    /** the total duration of the timer */
    private final int duration;

    /** the amount of time passed from the last reset */
    private int elapsed;

    /**
     * class constructor
     * @param manager the clientManager that needs this timer
     * @param time the amount of time this timer should last
     */
    public Timer (ClientManager manager, int time) {
        myClientManager = manager;
        duration = time; // Assign to member variable
        elapsed = 0; // Set time elapsed
    }

    /**
     * method to reset the timer
     */
    public synchronized void reset() {
        elapsed = 0;
    }

    /**
     * simulates the timer
     */
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

    /**
     * method called when the timer goes off, it closes the timer and the connection held by the client manager
     */
    public void timeout() {
        if(!Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
            myClientManager.close(true);
        }
    }
}
