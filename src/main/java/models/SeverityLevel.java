package models;

import java.awt.Color;

public enum SeverityLevel {
    SAFE(new Color(0, 229, 255, 100)),      // Translucent Cyan
    WARNING(new Color(255, 193, 7, 180)),   // Translucent Amber
    CRITICAL(new Color(255, 0, 85, 200));   // Translucent Red

    private final Color color;

    SeverityLevel(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}