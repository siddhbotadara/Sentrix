package models;

import java.awt.Color;

public enum SeverityLevel {
    // Add SAFE here for the Reset button
    SAFE("SAFE", new Color(0, 255, 150), new Color(0, 255, 150, 0)),
    LOW("ADVISORY", new Color(0, 255, 150), new Color(0, 255, 150, 90)),
    MEDIUM("WARNING", new Color(255, 200, 0), new Color(255, 200, 0, 110)),
    CRITICAL("EMERGENCY", new Color(255, 0, 85), new Color(255, 0, 85, 130));

    private final String label;
    private final Color coreColor;
    private final Color overlayColor;

    SeverityLevel(String label, Color coreColor, Color overlayColor) {
        this.label = label;
        this.coreColor = coreColor;
        this.overlayColor = overlayColor;
    }

    public String getLabel() { return label; }
    public Color getCoreColor() { return coreColor; }
    public Color getOverlayColor() { return overlayColor; }
}