package strategy;

import gui.ThailandMapPanel;
import models.SeverityLevel;

public class TyphoonResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Typhoon Detected! Take nearest shelter.");
    }

    @Override
    public void simulate(ThailandMapPanel map) {
        // Target Northern regions like Chiang Mai
        map.setProvinceStatus("TH86", SeverityLevel.CRITICAL);
        map.triggerDisasterOverlay("Chumphon", 85, SeverityLevel.CRITICAL.getOverlayColor());
    }
}