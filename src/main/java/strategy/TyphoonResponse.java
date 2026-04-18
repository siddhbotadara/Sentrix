package main.java.strategy;

public class TyphoonResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Typhoon Detected");
    }
}