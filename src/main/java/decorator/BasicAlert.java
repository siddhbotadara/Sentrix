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
        dashboard.log("[" + level.getLabel() + "] " + type + " detected in " + location);
    }
}