package gui;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.animation.AnimationElement;
import models.SeverityLevel;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ThailandMapPanel extends JPanel {
    private SVGUniverse svgUniverse = new SVGUniverse();
    private SVGDiagram diagram;
    private DisasterOverlay disasterOverlay;

    public ThailandMapPanel() {
        setBackground(new Color(11, 14, 20)); // Deep Navy Background
        this.disasterOverlay = new DisasterOverlay();
        loadMap();
    }

    private void loadMap() {
        try {
            URL url = getClass().getResource("/images/thailand_map_3.svg");

            if (url == null) {
                System.err.println("SVG not found in resources!");
                return;
            }

            diagram = svgUniverse.getDiagram(svgUniverse.loadSVG(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Bridge methods to control the overlay from the Dashboard
    public void triggerDisasterOverlay(String cityName, int radius) {
        disasterOverlay.setEffect(cityName, radius);
        repaint();
    }

    public void clearOverlay() {
        disasterOverlay.clear();
        repaint();
    }

    public void setProvinceStatus(String id, SeverityLevel level) {
        if (diagram == null) return;
        try {
            SVGElement element = diagram.getElement(id);
            if (element != null) {
                String hex = String.format("#%02x%02x%02x", 
                    level.getColor().getRed(), 
                    level.getColor().getGreen(), 
                    level.getColor().getBlue());
                element.setAttribute("fill", AnimationElement.AT_XML, hex);
                diagram.updateTime(0);
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (diagram == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Auto-scaling logic
        double scaleX = (double) getWidth() / diagram.getWidth();
        double scaleY = (double) getHeight() / diagram.getHeight();
        double scale = Math.min(scaleX, scaleY) * 0.95; // 5% padding

        g2.translate((getWidth() - diagram.getWidth() * scale) / 2, 
                     (getHeight() - diagram.getHeight() * scale) / 2);
        g2.scale(scale, scale);

        try {
            diagram.render(g2);
            disasterOverlay.draw(g2);
        } catch (SVGException e) {
            System.err.println("Rendering error: " + e.getMessage());
        }
    }
}