package strategy;

import gui.ThailandMapPanel;

public interface DisasterResponseStrategy {
    void transmitSafetyProcedures();                // Output the Safety Procedures for the disaster

    void simulate(ThailandMapPanel map);
}