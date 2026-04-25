package strategy;

import gui.ThailandMapPanel;

public interface DisasterResponseStrategy {
    void transmitSafetyProcedures();               

    void simulate(ThailandMapPanel map);
}