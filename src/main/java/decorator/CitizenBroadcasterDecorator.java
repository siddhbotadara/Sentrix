package decorator;
import observer.DisasterBroadcaster;
import models.SeverityLevel;

public class CitizenBroadcasterDecorator extends AlertDecorator {
    public CitizenBroadcasterDecorator(Alert alert) {
        super(alert);
    }

    @Override
    public void issue(String type, String location, String message, SeverityLevel level) {
        super.issue(type, location, message, level);
        // FIXED: Passing 'level' to the broadcaster so observers get the color data
        DisasterBroadcaster.getInstance().notifyObservers(type, location, message, level);
    }
}