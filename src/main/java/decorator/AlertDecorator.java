package decorator;
import models.SeverityLevel;

public abstract class AlertDecorator implements Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert alert) {
        this.decoratedAlert = alert;
    }

    public void issue(String type, String location, String message, SeverityLevel level) {
        decoratedAlert.issue(type, location, message, level);
    }
}