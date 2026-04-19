package main.java.strategy;

public class ResponseEngine {
    private DisasterResponseStrategy disasterResponseStrategy;

    public void setDisasterResponseStrategy(DisasterResponseStrategy strategy) {
        this.disasterResponseStrategy = strategy;
    }

    public void sendWarning() {
        this.disasterResponseStrategy.transmitSafetyProcedures();
    }
}