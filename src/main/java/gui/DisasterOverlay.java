package gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class DisasterOverlay {
    private String activeCity = null;
    private int radius = 0;
    private final Map<String, Point> cityCoords = new HashMap<>();
    private Color currentColor = new Color(255, 0, 85, 90);

    public DisasterOverlay() {

        // Approximate coordinates
        cityCoords.put("Bangkok", new Point(449, 460)); // Capital, TH10
        cityCoords.put("Phuket", new Point(311, 814));  // tsunami, TH83
        cityCoords.put("Chiang Rai", new Point(405, 84)); // Earthquake, TH57
        cityCoords.put("Chiang Mai", new Point(332, 153)); // Flood, TH50
        cityCoords.put("Kanchanaburi", new Point(353, 415)); // Flood, TH71
        cityCoords.put("Songkhla", new Point(443, 884)); // Flood, TH90
        cityCoords.put("Khon Kaen", new Point(571, 293)); // HeatWaves, TH40
        cityCoords.put("Chumphon", new Point(360, 647)); // Typhoon, TH86
    }

    public void setEffect(String cityName, int radius, Color color) {
        this.activeCity = cityName;
        this.radius = radius;
        this.currentColor = color;
    }

    public void clear() {
        this.activeCity = null;
    }

    public void draw(Graphics2D g2) {
        if (activeCity == null || !cityCoords.containsKey(activeCity)) return;
        Point p = cityCoords.get(activeCity);

        g2.setColor(currentColor); 
        g2.fill(new Ellipse2D.Double(p.x - radius, p.y - radius, radius * 2, radius * 2));

        g2.setColor(currentColor.getRGB() != 0 ? currentColor.darker() : Color.RED);
        g2.setStroke(new BasicStroke(3));
        g2.draw(new Ellipse2D.Double(p.x - radius, p.y - radius, radius * 2, radius * 2));
    }

    public Point getCityLocation(String cityName) {
        return cityCoords.get(cityName);
    }
}