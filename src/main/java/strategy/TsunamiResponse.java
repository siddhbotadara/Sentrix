package main.java.strategy;

public class TsunamiResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Tsunami Detected!");
    }
}