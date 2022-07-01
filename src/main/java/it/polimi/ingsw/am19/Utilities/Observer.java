package it.polimi.ingsw.am19.Utilities;

public interface Observer {
    /**
     * method called when a notification comes in
     * @param notification the notification sent by an observable class
     */
    void notify(Notification notification);
}
