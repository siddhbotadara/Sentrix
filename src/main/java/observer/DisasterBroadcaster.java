package observer;
import java.util.ArrayList;
import java.util.List;
import models.SeverityLevel;

public class DisasterBroadcaster implements Subject {
    private static DisasterBroadcaster instance;
    private final List<Observer> observers = new ArrayList<>();

    private DisasterBroadcaster() {}

    public static DisasterBroadcaster getInstance() {
        if (instance == null) {
            instance = new DisasterBroadcaster();
        }
        return instance;
    }

    @Override
    public void registerObserver(Observer o) { 
        observers.add(o); 
    }

    @Override
    public void removeObserver(Observer o) { 
        observers.remove(o); 
    }

    @Override
    public void notifyObservers(String type, String location, String instructions, SeverityLevel level) {
        for (Observer o : observers) {
            o.update(type, location, instructions, level);
        }
    }
}