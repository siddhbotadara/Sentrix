package decorator;
import models.SeverityLevel;

public interface Alert {
    void issue(String type, String location, String message, SeverityLevel level);
}