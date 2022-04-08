package it.polimi.ingsw.am19.Model.Utilities;

import it.polimi.ingsw.am19.Controller.Observer;

import java.util.List;


public class Observable {
    private Observer observer;
    
    public void notifyObserver(){
        observer.notify();
    }

    public void setObserver(Observer observer){
        this.observer = observer;
    }
}
