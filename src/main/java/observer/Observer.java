package observer;
import models.SeverityLevel;

public interface Observer {
    // Must have 4 parameters to match the broadcaster
    void update(String type, String location, String message, SeverityLevel level);
}