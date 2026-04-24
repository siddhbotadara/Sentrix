package strategy;

import gui.ThailandMapPanel;
import models.SeverityLevel;

public class EarthquakeResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Earthquake Detected, evacuate now!");
    }

    @Override
    public void simulate(ThailandMapPanel map) {
        // Target Northern regions like Chiang Mai
        map.setProvinceStatus("TH57", SeverityLevel.CRITICAL);
        map.triggerDisasterOverlay("Chiang Rai", 85, SeverityLevel.CRITICAL.getOverlayColor());
    }
}