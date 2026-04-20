package strategy;

public class EarthquakeResponse implements DisasterResponseStrategy {


    @Override
    public void transmitSafetyProcedures() {
        System.out.println("Earthquake Detected!");
    }
}