package it.polimi.ingsw.am19;

import it.polimi.ingsw.am19.Utilities.Notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Observable {
    protected transient List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notifyObservers(Notification notification){
        for(Observer o : observers)
            o.notify(notification);
    }
}
