package strategy;

import gui.ThailandMapPanel;
import models.SeverityLevel;

public class FloodResponse implements DisasterResponseStrategy {
    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Flood Detected! Moving to upper floors.");
    }

    @Override
    public void simulate(ThailandMapPanel map) {
        // Target Northern regions like Chiang Mai
        map.setProvinceStatus("TH50", SeverityLevel.CRITICAL);
        map.triggerDisasterOverlay("Chiang Mai", 85, SeverityLevel.CRITICAL.getOverlayColor());
    }
}