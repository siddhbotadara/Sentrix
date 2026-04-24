package strategy;

import gui.ThailandMapPanel;
import models.SeverityLevel;

public class HeatwaveResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Heatwaves Detected! Move to cooler place.");
    }

    @Override
    public void simulate(ThailandMapPanel map) {
        // Target Northern regions like Chiang Mai
        map.setProvinceStatus("TH40", SeverityLevel.WARNING);
        map.triggerDisasterOverlay("Khon Kaen", 85, SeverityLevel.CRITICAL.getOverlayColor());
    }
}