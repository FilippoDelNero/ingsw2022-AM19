package it.polimi.ingsw.am19;

public class Observable {
    protected Observer observer;

    public void addObserver(Observer observer){
        this.observer = observer;
    }
}
