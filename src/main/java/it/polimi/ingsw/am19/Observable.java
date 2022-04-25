package it.polimi.ingsw.am19;

import it.polimi.ingsw.am19.Observer;

public class Observable {
    private Observer observer;
    
    public void notifyObserver(){
        observer.notify();
    }

    public void setObserver(Observer observer){
        this.observer = observer;
    }
}
