package sensors;

public class WeatherData {
    public String cityName;
    public double temp;
    public int humidity;
    public double windSpeed;
    public String description;
    public int pressure;
    public int visibility;
    public double rain;

    public WeatherData(String name, double t, int h, double w, String d, int p, int v, double r) {
        this.cityName = name;
        this.temp = t;
        this.humidity = h;
        this.windSpeed = w;
        this.description = d;
        this.pressure = p;
        this.visibility = v;
        this.rain = r;
    }
}