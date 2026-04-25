package observer;
import models.SeverityLevel;

public interface Observer {

    void update(String type, String location, String message, SeverityLevel level);
}