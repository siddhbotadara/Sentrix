package strategy;

import gui.ThailandMapPanel;
import models.SeverityLevel;

public class TsunamiResponse implements DisasterResponseStrategy {
    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Tsunami Detected! Evacuate to higher ground.");
    }

    @Override
    public void simulate(ThailandMapPanel map) {

        map.setProvinceStatus("TH83", SeverityLevel.CRITICAL); 
        map.triggerDisasterOverlay("Phuket", 100, SeverityLevel.CRITICAL.getOverlayColor());
    }
}