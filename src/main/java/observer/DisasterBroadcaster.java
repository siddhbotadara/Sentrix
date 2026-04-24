package observer;

import java.util.ArrayList;
import java.util.List;

public class DisasterBroadcaster implements Subject {
    private static DisasterBroadcaster instance;
    private final List<Observer> observers = new ArrayList<>();

    private DisasterBroadcaster() {} // Private constructor

    public static DisasterBroadcaster getInstance() {
        if (instance == null) instance = new DisasterBroadcaster();
        return instance;
    }

    @Override
    public void registerObserver(Observer o) { observers.add(o); }

    @Override
    public void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(String disasterType, String location, String instructions) {
        for (Observer o : observers) {
            o.update(disasterType, location, instructions);
        }
    }
}