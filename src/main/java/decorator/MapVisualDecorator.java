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
        // Dynamically use the overlay color from the severity level
        mapPanel.triggerDisasterOverlay(location, 85, level.getOverlayColor()); 
    }
}