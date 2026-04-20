package sensors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.cdimascio.dotenv.Dotenv;

public class SensorFactory {
    
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("WEATHER_API_KEY");

    static {
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("API key not found in .env file!");
        }
    }

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?units=metric&q=";

    public static WeatherData fetchRealTimeData(String city) {
        try {
            // For "Thailand Overall", we use Bangkok as the primary relay station
            String queryCity = city.equalsIgnoreCase("THAILAND (OVERALL)") ? "Bangkok" : city;
            URL url = new URL(BASE_URL + queryCity.replace(" ", "%20") + "&appid=" + API_KEY);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            if (conn.getResponseCode() != 200) throw new Exception("API Error");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) { result.append(line); }
            rd.close();

            String json = result.toString();
            double temp = Double.parseDouble(extract(json, "\"temp\":", ","));
            int humidity = Integer.parseInt(extract(json, "\"humidity\":", ","));
            double wind = Double.parseDouble(extract(json, "\"speed\":", ","));
            String desc = extract(json, "\"description\":\"", "\"");

            return new WeatherData(city, temp, humidity, wind, desc);
        } catch (Exception e) {
            e.printStackTrace(); 

            return new WeatherData(city, 0.0, 0, 0.0, "Node Offline");
        }
    }

    private static String extract(String json, String key, String end) {
        int start = json.indexOf(key) + key.length();
        int stop = json.indexOf(end, start);
        return json.substring(start, stop).trim();
    }
}