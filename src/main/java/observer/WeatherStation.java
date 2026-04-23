package observer;

import java.util.ArrayList;

public class GUIObserver implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObserver(String action) {
        for (Observer o : this.observers) {
            o.update(action);
        }
    }
}