package decorator;
import gui.MainDashboard;
import models.SeverityLevel;

public class BasicAlert implements Alert {
    private MainDashboard dashboard;

    public BasicAlert(MainDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void issue(String type, String location, String message, SeverityLevel level) {
        // Core functionality: Log the event to the system console
        dashboard.log("[" + level + "] " + type + " detected: " + location);
    }
}