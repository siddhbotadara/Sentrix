package decorator;
import gui.ThailandMapPanel;
import models.SeverityLevel;

public class MapVisualDecorator extends AlertDecorator {
    private ThailandMapPanel mapPanel;

    public MapVisualDecorator(Alert alert, ThailandMapPanel mapPanel) {
        super(alert);
        this.mapPanel = mapPanel;
    }

    @Override
    public void issue(String type, String location, String message, SeverityLevel level) {
        super.issue(type, location, message, level);
        // Uses your existing logic: trigger overlay by City Name
        mapPanel.triggerDisasterOverlay(location, 85); 
        // Note: You can still call mapPanel.setProvinceStatus here if you have a way to 
        // resolve the ID, otherwise the circle overlay works on name alone!
    }
}