package observer;
import models.SeverityLevel;

public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String type, String location, String instructions, SeverityLevel level);
}