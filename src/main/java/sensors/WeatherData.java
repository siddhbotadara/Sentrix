package sensors;

public class WeatherData {
    public String cityName;
    public double temp;
    public int humidity;
    public double windSpeed;
    public String description;

    public WeatherData(String name, double t, int h, double w, String d) {
        this.cityName = name;
        this.temp = t;
        this.humidity = h;
        this.windSpeed = w;
        this.description = d;
    }
}