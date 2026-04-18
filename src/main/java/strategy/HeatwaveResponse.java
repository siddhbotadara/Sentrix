package main.java.strategy;

public class HeatwaveResponse implements DisasterResponseStrategy {

    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Heatwave Detected!");
    }
}