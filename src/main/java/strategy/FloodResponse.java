package main.java.strategy;

public class FloodResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Flood Detected");
    }
}